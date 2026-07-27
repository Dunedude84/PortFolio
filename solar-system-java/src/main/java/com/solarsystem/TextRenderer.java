package com.solarsystem;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class TextRenderer {
    
    private static final int BITMAP_W = 512;
    private static final int BITMAP_H = 512;
    private static final int CHAR_HEIGHT = 24;
    
    private int fontTexture;
    private STBTTBakedChar.Buffer cdata;
    private int shaderProgram;
    private int VAO, VBO;
    
    private static final String TEXT_VERTEX_SHADER = 
        "#version 330 core\n" +
        "layout (location = 0) in vec4 vertex;\n" +
        "out vec2 TexCoords;\n" +
        "uniform mat4 projection;\n" +
        "void main() {\n" +
        "    gl_Position = projection * vec4(vertex.xy, 0.0, 1.0);\n" +
        "    TexCoords = vertex.zw;\n" +
        "}\n";
    
    private static final String TEXT_FRAGMENT_SHADER = 
        "#version 330 core\n" +
        "in vec2 TexCoords;\n" +
        "out vec4 color;\n" +
        "uniform sampler2D text;\n" +
        "uniform vec3 textColor;\n" +
        "void main() {\n" +
        "    float alpha = texture(text, TexCoords).r;\n" +
        "    color = vec4(textColor, alpha);\n" +
        "}\n";
    
    public boolean init() {
        try {
            ByteBuffer ttf = loadFontFile();
            if (ttf == null) {
                System.err.println("Failed to load font file");
                return false;
            }
            
            ByteBuffer bitmap = BufferUtils.createByteBuffer(BITMAP_W * BITMAP_H);
            cdata = STBTTBakedChar.malloc(96);
            
            STBTruetype.stbtt_BakeFontBitmap(ttf, CHAR_HEIGHT, bitmap, BITMAP_W, BITMAP_H, 32, cdata);
            
            fontTexture = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, fontTexture);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, BITMAP_W, BITMAP_H, 0, GL_RED, GL_UNSIGNED_BYTE, bitmap);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            
            shaderProgram = ShaderUtils.createShaderProgram(TEXT_VERTEX_SHADER, TEXT_FRAGMENT_SHADER);
            
            VAO = glGenVertexArrays();
            VBO = glGenBuffers();
            glBindVertexArray(VAO);
            glBindBuffer(GL_ARRAY_BUFFER, VBO);
            glBufferData(GL_ARRAY_BUFFER, 6 * 4 * Float.BYTES, GL_DYNAMIC_DRAW);
            glEnableVertexAttribArray(0);
            glVertexAttribPointer(0, 4, GL_FLOAT, false, 4 * Float.BYTES, 0);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
            
            System.out.println("Text rendering initialized successfully");
            return true;
            
        } catch (Exception e) {
            System.err.println("Failed to initialize text rendering: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private ByteBuffer loadFontFile() {
        String[] fontPaths = {
            "C:/Windows/Fonts/arial.ttf",
            "C:/Windows/Fonts/calibri.ttf",
            "C:/Windows/Fonts/verdana.ttf",
            "/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf",
            "/System/Library/Fonts/Helvetica.ttc"
        };
        
        for (String path : fontPaths) {
            try {
                byte[] fontData = Files.readAllBytes(Paths.get(path));
                ByteBuffer buffer = BufferUtils.createByteBuffer(fontData.length);
                buffer.put(fontData);
                buffer.flip();
                System.out.println("Loaded font: " + path);
                return buffer;
            } catch (IOException e) {
                // Try next font
            }
        }
        
        return null;
    }
    
    public void renderText(String text, float x, float y, float scale, Vector3f color, int screenWidth, int screenHeight) {
        boolean depthTestEnabled = glIsEnabled(GL_DEPTH_TEST);
        boolean cullFaceEnabled = glIsEnabled(GL_CULL_FACE);
        
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_CULL_FACE);
        
        glUseProgram(shaderProgram);
        glUniform3f(glGetUniformLocation(shaderProgram, "textColor"), color.x, color.y, color.z);
        
        Matrix4f projection = new Matrix4f().ortho(0.0f, screenWidth, 0.0f, screenHeight, -1.0f, 1.0f);
        float[] projArray = new float[16];
        projection.get(projArray);
        glUniformMatrix4fv(glGetUniformLocation(shaderProgram, "projection"), false, projArray);
        
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, fontTexture);
        glBindVertexArray(VAO);
        
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer xb = stack.floats(0.0f);
            FloatBuffer yb = stack.floats(0.0f);
            
            STBTTAlignedQuad q = STBTTAlignedQuad.malloc(stack);
            
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                if (c < 32 || c >= 128) continue;
                
                STBTruetype.stbtt_GetBakedQuad(cdata, BITMAP_W, BITMAP_H, c - 32, xb, yb, q, true);
                
                float x0 = x + q.x0() * scale;
                float y0 = y - q.y0() * scale;
                float x1 = x + q.x1() * scale;
                float y1 = y - q.y1() * scale;
                
                float[] vertices = {
                    x0, y1, q.s0(), q.t1(),
                    x0, y0, q.s0(), q.t0(),
                    x1, y0, q.s1(), q.t0(),
                    
                    x0, y1, q.s0(), q.t1(),
                    x1, y0, q.s1(), q.t0(),
                    x1, y1, q.s1(), q.t1()
                };
                
                glBindBuffer(GL_ARRAY_BUFFER, VBO);
                glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
                glDrawArrays(GL_TRIANGLES, 0, 6);
            }
        }
        
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
        
        if (depthTestEnabled) {
            glEnable(GL_DEPTH_TEST);
        }
        if (cullFaceEnabled) {
            glEnable(GL_CULL_FACE);
        }
    }
    
    public void cleanup() {
        if (cdata != null) {
            cdata.free();
        }
        glDeleteTextures(fontTexture);
        glDeleteVertexArrays(VAO);
        glDeleteBuffers(VBO);
        glDeleteProgram(shaderProgram);
    }
}
