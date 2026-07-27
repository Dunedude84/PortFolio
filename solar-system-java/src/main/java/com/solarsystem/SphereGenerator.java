package com.solarsystem;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SphereGenerator {
    
    public static class SphereData {
        public int VAO;
        public int VBO;
        public int EBO;
        public int numIndices;
    }
    
    public static SphereData createSphere(int sectors, int stacks) {
        List<Float> vertices = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        
        float R = 1.0f;
        float sectorStep = (float)(2.0 * Math.PI / sectors);
        float stackStep = (float)(Math.PI / stacks);
        
        for (int i = 0; i <= stacks; i++) {
            float phi = (float)(Math.PI / 2 - i * stackStep);
            float xy = R * (float)Math.cos(phi);
            float z = R * (float)Math.sin(phi);
            
            for (int j = 0; j <= sectors; j++) {
                float theta = j * sectorStep;
                
                float x = xy * (float)Math.cos(theta);
                float y = xy * (float)Math.sin(theta);
                
                float nx = x;
                float ny = y;
                float nz = z;
                float len = (float)Math.sqrt(nx*nx + ny*ny + nz*nz);
                nx /= len;
                ny /= len;
                nz /= len;
                
                float s = (float)j / sectors;
                float t = 1.0f - (float)i / stacks;
                
                vertices.add(x);
                vertices.add(y);
                vertices.add(z);
                vertices.add(nx);
                vertices.add(ny);
                vertices.add(nz);
                vertices.add(s);
                vertices.add(t);
            }
        }
        
        for (int i = 0; i < stacks; i++) {
            int k1 = i * (sectors + 1);
            int k2 = k1 + sectors + 1;
            
            for (int j = 0; j < sectors; j++, k1++, k2++) {
                if (i != 0) {
                    indices.add(k1);
                    indices.add(k2);
                    indices.add(k1 + 1);
                }
                
                if (i != (stacks - 1)) {
                    indices.add(k1 + 1);
                    indices.add(k2);
                    indices.add(k2 + 1);
                }
            }
        }
        
        float[] vertexArray = new float[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            vertexArray[i] = vertices.get(i);
        }
        
        int[] indexArray = new int[indices.size()];
        for (int i = 0; i < indices.size(); i++) {
            indexArray[i] = indices.get(i);
        }
        
        SphereData data = new SphereData();
        data.numIndices = indexArray.length;
        
        data.VAO = glGenVertexArrays();
        data.VBO = glGenBuffers();
        data.EBO = glGenBuffers();
        
        glBindVertexArray(data.VAO);
        
        glBindBuffer(GL_ARRAY_BUFFER, data.VBO);
        FloatBuffer vertexBuffer = memAllocFloat(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        memFree(vertexBuffer);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, data.EBO);
        IntBuffer indexBuffer = memAllocInt(indexArray.length);
        indexBuffer.put(indexArray).flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        memFree(indexBuffer);
        
        int stride = 8 * Float.BYTES;
        
        glVertexAttribPointer(0, 3, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        
        glVertexAttribPointer(1, 3, GL_FLOAT, false, stride, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);
        
        glVertexAttribPointer(2, 2, GL_FLOAT, false, stride, 6 * Float.BYTES);
        glEnableVertexAttribArray(2);
        
        System.out.println("Sphere mesh created with:");
        System.out.println("  Vertices: " + (vertexArray.length / 8));
        System.out.println("  Indices: " + indexArray.length);
        System.out.println("  Stride: " + stride + " bytes");
        
        glBindVertexArray(0);
        
        return data;
    }
}
