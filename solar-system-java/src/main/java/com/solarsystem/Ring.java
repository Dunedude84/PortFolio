package com.solarsystem;

import org.joml.Matrix4f;
import org.joml.Vector4f;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Ring {
    public float innerRadius;
    public float outerRadius;
    public Vector4f color;
    public int VAO, VBO;

    public Ring(float innerRadius, float outerRadius, Vector4f color) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.color = color;
        createRingMesh();
    }

    private void createRingMesh() {
        List<Float> vertices = new ArrayList<>();
        int segments = 180;

        for (int i = 0; i <= segments; i++) {
            float angle = (float)i / segments * 2.0f * (float)Math.PI;
            float c = (float)Math.cos(angle);
            float s = (float)Math.sin(angle);

            vertices.add(innerRadius * c);
            vertices.add(0.0f);
            vertices.add(innerRadius * s);
            vertices.add(0.0f);
            vertices.add(1.0f);
            vertices.add(0.0f);

            vertices.add(outerRadius * c);
            vertices.add(0.0f);
            vertices.add(outerRadius * s);
            vertices.add(0.0f);
            vertices.add(1.0f);
            vertices.add(0.0f);
        }

        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexArray[i] = vertices.get(i);
        }

        VAO = glGenVertexArrays();
        VBO = glGenBuffers();

        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);

        FloatBuffer vertexBuffer = memAllocFloat(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        memFree(vertexBuffer);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindVertexArray(0);
    }

    public void render(int shaderProgram, Matrix4f parentModel) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_CULL_FACE);

        Matrix4f model = new Matrix4f(parentModel);

        glUseProgram(shaderProgram);
        
        float[] modelArray = new float[16];
        model.get(modelArray);
        
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "model"), false, modelArray);
        glUniform4f(glGetUniformLocation(shaderProgram, "color"), color.x, color.y, color.z, color.w);
        glUniform1i(glGetUniformLocation(shaderProgram, "hasTexture"), 0);

        glBindVertexArray(VAO);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, (180 + 1) * 2);

        glEnable(GL_CULL_FACE);
        glDisable(GL_BLEND);
    }

    public void cleanup() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
    }
}
