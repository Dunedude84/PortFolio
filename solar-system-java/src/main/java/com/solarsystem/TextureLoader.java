package com.solarsystem;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class TextureLoader {
    
    public static int loadTexture(String path) {
        int textureID = glGenTextures();
        
        String resolvedPath = resolvePath(path);
        
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            ByteBuffer image = STBImage.stbi_load(resolvedPath, width, height, channels, 0);
            
            if (image == null) {
                System.err.println("Failed to load texture: " + path);
                System.err.println("Resolved path: " + resolvedPath);
                System.err.println("STB Error: " + STBImage.stbi_failure_reason());
                return 0;
            }
            
            int format = GL_RGB;
            if (channels.get(0) == 1) {
                format = GL_RED;
            } else if (channels.get(0) == 3) {
                format = GL_RGB;
            } else if (channels.get(0) == 4) {
                format = GL_RGBA;
            }
            
            glBindTexture(GL_TEXTURE_2D, textureID);
            glTexImage2D(GL_TEXTURE_2D, 0, format, width.get(0), height.get(0), 
                        0, format, GL_UNSIGNED_BYTE, image);
            
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glGenerateMipmap(GL_TEXTURE_2D);
            
            STBImage.stbi_image_free(image);
        }
        
        return textureID;
    }
    
    private static String resolvePath(String path) {
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
}
