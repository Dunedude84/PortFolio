package com.solarsystem;

import org.joml.Matrix4f;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Skybox {
    private int VAO, VBO;
    private int textureID;
    private int shaderProgram;
    private boolean isEquirectangular;
    private float rotationAngle = 0.0f;
    public float rotationSpeed = 0.02f;
    public boolean autoRotate = true;

    private static final float[] SKYBOX_VERTICES = {
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f
    };

    public void init(boolean equirectangular) {
        this.isEquirectangular = equirectangular;

        VAO = glGenVertexArrays();
        VBO = glGenBuffers();
        glBindVertexArray(VAO);
        glBindBuffer(GL_ARRAY_BUFFER, VBO);

        FloatBuffer vertexBuffer = memAllocFloat(SKYBOX_VERTICES.length);
        vertexBuffer.put(SKYBOX_VERTICES).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        memFree(vertexBuffer);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

        String vertexShader = isEquirectangular ? "#version 330 core\n" +
                "layout (location = 0) in vec3 aPos;\n" +
                "out vec3 WorldPos;\n" +
                "uniform mat4 projection;\n" +
                "uniform mat4 view;\n" +
                "uniform float rotationAngle;\n" +
                "void main() {\n" +
                "    float cosA = cos(rotationAngle);\n" +
                "    float sinA = sin(rotationAngle);\n" +
                "    mat3 rotY = mat3(\n" +
                "        cosA, 0.0, -sinA,\n" +
                "        0.0, 1.0, 0.0,\n" +
                "        sinA, 0.0, cosA\n" +
                "    );\n" +
                "    WorldPos = rotY * aPos;\n" +
                "    vec4 pos = projection * view * vec4(aPos, 1.0);\n" +
                "    gl_Position = pos.xyww;\n" +
                "}\n"
                : "#version 330 core\n" +
                        "layout (location = 0) in vec3 aPos;\n" +
                        "out vec3 TexCoords;\n" +
                        "uniform mat4 projection;\n" +
                        "uniform mat4 view;\n" +
                        "uniform float rotationAngle;\n" +
                        "void main() {\n" +
                        "    float cosA = cos(rotationAngle);\n" +
                        "    float sinA = sin(rotationAngle);\n" +
                        "    mat3 rotY = mat3(\n" +
                        "        cosA, 0.0, -sinA,\n" +
                        "        0.0, 1.0, 0.0,\n" +
                        "        sinA, 0.0, cosA\n" +
                        "    );\n" +
                        "    TexCoords = rotY * aPos;\n" +
                        "    vec4 pos = projection * view * vec4(aPos, 1.0);\n" +
                        "    gl_Position = pos.xyww;\n" +
                        "}\n";

        String fragmentShader = isEquirectangular ? "#version 330 core\n" +
                "out vec4 FragColor;\n" +
                "in vec3 WorldPos;\n" +
                "uniform sampler2D equirectangularMap;\n" +
                "const vec2 invAtan = vec2(0.1591, 0.3183);\n" +
                "vec2 SampleSphericalMap(vec3 v) {\n" +
                "    vec2 uv = vec2(atan(v.z, v.x), asin(v.y));\n" +
                "    uv *= invAtan;\n" +
                "    uv += 0.5;\n" +
                "    return uv;\n" +
                "}\n" +
                "void main() {\n" +
                "    vec2 uv = SampleSphericalMap(normalize(WorldPos));\n" +
                "    vec3 color = texture(equirectangularMap, uv).rgb;\n" +
                "    FragColor = vec4(color, 1.0);\n" +
                "}\n"
                : "#version 330 core\n" +
                        "out vec4 FragColor;\n" +
                        "in vec3 TexCoords;\n" +
                        "uniform samplerCube skybox;\n" +
                        "void main() {\n" +
                        "    FragColor = texture(skybox, TexCoords);\n" +
                        "}\n";

        shaderProgram = ShaderUtils.createShaderProgram(vertexShader, fragmentShader);
    }

    public void loadTexture(String texturePath) {
        String resolvedPath = resolvePath(texturePath);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = STBImage.stbi_load(resolvedPath, width, height, channels, 0);

            if (image == null) {
                System.err.println("Failed to load skybox texture: " + texturePath);
                System.err.println("Resolved path: " + resolvedPath);
                System.err.println("STB Error: " + STBImage.stbi_failure_reason());
                return;
            }

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            int format = channels.get(0) == 4 ? GL_RGBA : GL_RGB;
            glTexImage2D(GL_TEXTURE_2D, 0, format, width.get(0), height.get(0),
                    0, format, GL_UNSIGNED_BYTE, image);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            STBImage.stbi_image_free(image);
        }
    }

    public void render(Matrix4f projection, Matrix4f view, boolean paused) {
        if (autoRotate && !paused) {
            rotationAngle += rotationSpeed * 1.0f * 0.016f;
        }

        glDepthFunc(GL_LEQUAL);
        glUseProgram(shaderProgram);

        Matrix4f modView = new Matrix4f(view);
        modView.m30(0);
        modView.m31(0);
        modView.m32(0);

        float[] viewArray = new float[16];
        float[] projArray = new float[16];
        modView.get(viewArray);
        projection.get(projArray);

        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "view"), false, viewArray);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "projection"), false, projArray);
        glUniform1f(glGetUniformLocation(shaderProgram, "rotationAngle"), rotationAngle);

        glBindVertexArray(VAO);

        if (isEquirectangular) {
            glActiveTexture(GL_TEXTURE0);
            glBindTexture(GL_TEXTURE_2D, textureID);
            glUniform1i(glGetUniformLocation(shaderProgram, "equirectangularMap"), 0);
        }

        glDrawArrays(GL_TRIANGLES, 0, 36);
        glBindVertexArray(0);
        glDepthFunc(GL_LESS);
    }

    private String resolvePath(String path) {
        java.io.File file = new java.io.File(path);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        String userDir = System.getProperty("user.dir");
        file = new java.io.File(userDir, path);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        file = new java.io.File(userDir, "solar-system-java/" + path);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        file = new java.io.File(userDir, "../" + path);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        return path;
    }

    public void cleanup() {
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glDeleteTextures(textureID);
        glDeleteProgram(shaderProgram);
    }
}
