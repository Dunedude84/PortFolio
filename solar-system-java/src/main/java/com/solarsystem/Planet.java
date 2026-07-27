package com.solarsystem;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.joml.Matrix4f;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Planet {
    public float orbitRadius;
    public float size;
    public Vector4f color;
    public float orbitSpeed;
    public float rotationSpeed;
    public float angle;
    public float rotationAngle;
    public Vector3f position;
    public int texture;
    public boolean hasTexture;
    public String name;
    public List<Planet> satellites;
    public List<Ring> rings;
    public Vector3f rotationAxis;
    public float axialTilt;
    public float mass;
    public String composition;
    public String discoveryDate;
    public float surfaceTemp;
    public float gravity;
    public float orbitalPeriod;
    public String surfaceFeatures;
    public String explorationHistory;
    public String atmosphere;

    public Planet(float orbitRadius, float size, Vector4f color, float orbitSpeed, 
                  float rotationSpeed, String texturePath, boolean hasTexture, 
                  float startAngle, String name) {
        this.orbitRadius = orbitRadius;
        this.size = size;
        this.color = color;
        this.orbitSpeed = orbitSpeed * 0.2f * 10;
        this.rotationSpeed = rotationSpeed;
        this.angle = startAngle;
        this.rotationAngle = 0.0f;
        this.position = new Vector3f(0.0f, 0.0f, 0.0f);
        this.texture = 0;
        this.hasTexture = hasTexture;
        this.name = name;
        this.satellites = new ArrayList<>();
        this.rings = new ArrayList<>();
        this.rotationAxis = new Vector3f(0.0f, 1.0f, 0.0f);
        this.axialTilt = 0.0f;
        this.mass = 0.0f;
        this.composition = "";
        this.discoveryDate = "";
        this.surfaceTemp = 0.0f;
        this.gravity = 0.0f;
        this.orbitalPeriod = 0.0f;
        this.surfaceFeatures = "";
        this.explorationHistory = "";
        this.atmosphere = "";

        setupRotationAxis();

        if (hasTexture && texturePath != null) {
            System.out.println("Loading texture for " + name + " from path: " + texturePath);
            this.texture = TextureLoader.loadTexture(texturePath);
            if (this.texture == 0) {
                System.err.println("Failed to load texture for " + name);
                this.hasTexture = false;
            } else {
                System.out.println("Successfully loaded texture for " + name + " with ID: " + this.texture);
                this.hasTexture = true;
            }
        }
    }

    private void setupRotationAxis() {
        switch (name) {
            case "Mercury":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.1f);
                axialTilt = 0.03f;
                break;
            case "Venus":
                rotationAxis = new Vector3f(0.0f, 1.0f, -0.5f);
                axialTilt = 177.4f;
                break;
            case "Earth":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.4f);
                axialTilt = 23.5f;
                break;
            case "Mars":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.3f);
                axialTilt = 25.2f;
                break;
            case "Jupiter":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.05f);
                axialTilt = 3.1f;
                break;
            case "Saturn":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.2f);
                axialTilt = 26.7f;
                break;
            case "Uranus":
                rotationAxis = new Vector3f(0.0f, 1.0f, -0.8f);
                axialTilt = 97.8f;
                break;
            case "Neptune":
                rotationAxis = new Vector3f(0.0f, 1.0f, 0.3f);
                axialTilt = 28.3f;
                break;
        }

        if (rotationAxis.lengthSquared() > 0.0f) {
            rotationAxis.normalize();
        }
    }

    public void addRing(float innerRadius, float outerRadius, Vector4f color) {
        rings.add(new Ring(innerRadius, outerRadius, color));
    }

    public void addSatellite(Planet satellite) {
        satellites.add(satellite);
    }

    public void render(int shaderProgram, Matrix4f view, Matrix4f projection, 
                      int sphereVAO, int numSphereIndices, boolean isPaused, float timeScale) {
        if (!isPaused) {
            angle += orbitSpeed * timeScale * 0.016f;
            rotationAngle += rotationSpeed * timeScale * 0.016f;
        }
        
        position.x = orbitRadius * (float)Math.cos(angle);
        position.z = orbitRadius * (float)Math.sin(angle);
        position.y = 0.0f;

        Matrix4f model = new Matrix4f();
        model.translate(position);
        model.rotate((float)Math.toRadians(axialTilt), 1.0f, 0.0f, 0.0f);
        model.rotate(rotationAngle, rotationAxis.x, rotationAxis.y, rotationAxis.z);
        model.scale(size);

        glUseProgram(shaderProgram);
        
        int modelLoc = glGetUniformLocation(shaderProgram, "model");
        int viewLoc = glGetUniformLocation(shaderProgram, "view");
        int projLoc = glGetUniformLocation(shaderProgram, "projection");
        
        float[] modelArray = new float[16];
        float[] viewArray = new float[16];
        float[] projArray = new float[16];
        
        model.get(modelArray);
        view.get(viewArray);
        projection.get(projArray);
        
        glUniformMatrix4fv(modelLoc, false, modelArray);
        glUniformMatrix4fv(viewLoc, false, viewArray);
        glUniformMatrix4fv(projLoc, false, projArray);

        if (name.equals("Sun")) {
            float time = (float)org.lwjgl.glfw.GLFW.glfwGetTime();
            float rotationSpeed = 0.1f;
            glUniform1f(glGetUniformLocation(shaderProgram, "sunTextureRotation"), time * rotationSpeed);
            glUniform1i(glGetUniformLocation(shaderProgram, "isSun"), 1);
        } else {
            glUniform1i(glGetUniformLocation(shaderProgram, "isSun"), 0);
        }

        if (hasTexture) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, texture);
            glUniform1i(glGetUniformLocation(shaderProgram, "textureSampler"), 0);
            glUniform1i(glGetUniformLocation(shaderProgram, "hasTexture"), 1);
        } else {
            glUniform4f(glGetUniformLocation(shaderProgram, "color"), 
                       color.x, color.y, color.z, color.w);
            glUniform1i(glGetUniformLocation(shaderProgram, "hasTexture"), 0);
        }

        glBindVertexArray(sphereVAO);
        glDrawElements(GL_TRIANGLES, numSphereIndices, GL_UNSIGNED_INT, 0);

        Matrix4f ringModel = new Matrix4f().translate(position);
        for (Ring ring : rings) {
            ring.render(shaderProgram, ringModel);
        }

        for (Planet satellite : satellites) {
            if (!isPaused) {
                satellite.angle += satellite.orbitSpeed * timeScale * 0.016f;
            }
            
            satellite.position.x = position.x + satellite.orbitRadius * (float)Math.cos(satellite.angle);
            satellite.position.z = position.z + satellite.orbitRadius * (float)Math.sin(satellite.angle);
            satellite.position.y = position.y;

            Matrix4f satModel = new Matrix4f();
            satModel.translate(satellite.position);
            satModel.rotate((float)Math.toRadians(satellite.axialTilt), 1.0f, 0.0f, 0.0f);
            satModel.rotate(satellite.rotationAngle, satellite.rotationAxis.x, 
                          satellite.rotationAxis.y, satellite.rotationAxis.z);
            satModel.scale(satellite.size);

            satModel.get(modelArray);
            glUniformMatrix4fv(modelLoc, false, modelArray);

            if (satellite.hasTexture) {
                glActiveTexture(GL_TEXTURE0);
                glBindTexture(GL_TEXTURE_2D, satellite.texture);
                glUniform1i(glGetUniformLocation(shaderProgram, "textureSampler"), 0);
                glUniform1i(glGetUniformLocation(shaderProgram, "hasTexture"), 1);
            } else {
                glUniform4f(glGetUniformLocation(shaderProgram, "color"),
                           satellite.color.x, satellite.color.y, satellite.color.z, satellite.color.w);
                glUniform1i(glGetUniformLocation(shaderProgram, "hasTexture"), 0);
            }

            glDrawElements(GL_TRIANGLES, numSphereIndices, GL_UNSIGNED_INT, 0);
        }
    }
}
