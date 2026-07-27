package com.solarsystem;

import static org.lwjgl.opengl.GL20.*;

public class ShaderUtils {
    
    public static int createShaderProgram(String vertexSource, String fragmentSource) {
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentSource);
        
        if (vertexShader == 0 || fragmentShader == 0) {
            return 0;
        }
        
        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        
        int success = glGetProgrami(program, GL_LINK_STATUS);
        if (success == 0) {
            String infoLog = glGetProgramInfoLog(program);
            System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + infoLog);
            return 0;
        }
        
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        
        return program;
    }
    
    private static int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        
        int success = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (success == 0) {
            String infoLog = glGetShaderInfoLog(shader);
            String shaderType = (type == GL_VERTEX_SHADER) ? "VERTEX" : "FRAGMENT";
            System.err.println("ERROR::SHADER::" + shaderType + "::COMPILATION_FAILED\n" + infoLog);
            return 0;
        }
        
        return shader;
    }
}
