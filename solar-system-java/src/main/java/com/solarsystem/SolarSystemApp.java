package com.solarsystem;

import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

public class SolarSystemApp {

    private long window;
    private int width = 800;
    private int height = 600;

    private int planetShaderProgram;
    private int starShaderProgram;
    private SphereGenerator.SphereData sphereData;
    private List<Planet> planets = new ArrayList<>();
    private Skybox skybox;
    private TextRenderer textRenderer;
    private Map<String, PlanetDetails> planetDetailsMap;

    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();

    private float deltaTime = 0.0f;
    private float lastFrameTime = 0.0f;
    private float timeScale = 10.0f;
    private boolean isPaused = false;
    private boolean showImGuiMenu = true;
    private boolean showQuitConfirmation = false;
    private boolean showText = true;
    private int selectedPlanet = 0;

    private Vector3f cameraPos = new Vector3f(-100.0f, 30.0f, 0.0f);
    private Vector3f cameraFront = new Vector3f(1.0f, -0.3f, 0.0f).normalize();
    private Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
    private float cameraYaw = 0.0f;
    private float cameraPitch = -15.0f;
    private float mouseSensitivity = 0.1f;
    private float cameraSpeed = 0.5f;
    private boolean firstMouse = true;
    private double lastX = 400.0;
    private double lastY = 300.0;

    private boolean[] keys = new boolean[GLFW_KEY_LAST];
    private boolean spacePressed = false;
    private boolean f1Pressed = false;
    private boolean f2Pressed = false;
    private boolean f3Pressed = false;
    private boolean escPressed = false;
    private boolean isFullscreen = false;
    private int windowedX = 100;
    private int windowedY = 100;
    private int windowedWidth = 800;
    private int windowedHeight = 600;

    private int nearStarsVAO;
    private int nearStarsVBO;
    private int farStarsVAO;
    private int farStarsVBO;
    private float[] nearStarPositions;
    private float[] farStarPositions;
    private final int nearStarCount = 35000;
    private final int farStarCount = 70000;
    private final float starBoundary = 1000.0f;
    private final float nearStarSpeed = 19.6875f;
    private final float farStarSpeed = 13.125f;
    private float starSpeedMultiplier = 1.0f;

    private static final String VERTEX_SHADER = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "layout (location = 1) in vec3 aNormal;\n" +
            "layout (location = 2) in vec2 aTexCoord;\n" +
            "out vec3 FragPos;\n" +
            "out vec3 Normal;\n" +
            "out vec2 TexCoord;\n" +
            "uniform mat4 model;\n" +
            "uniform mat4 view;\n" +
            "uniform mat4 projection;\n" +
            "void main() {\n" +
            "    FragPos = vec3(model * vec4(aPos, 1.0));\n" +
            "    Normal = mat3(transpose(inverse(model))) * aNormal;\n" +
            "    TexCoord = aTexCoord;\n" +
            "    gl_Position = projection * view * vec4(FragPos, 1.0);\n" +
            "}\n";

    private static final String FRAGMENT_SHADER = "#version 330 core\n" +
            "in vec3 FragPos;\n" +
            "in vec3 Normal;\n" +
            "in vec2 TexCoord;\n" +
            "out vec4 FragColor;\n" +
            "uniform vec4 color;\n" +
            "uniform bool hasTexture;\n" +
            "uniform sampler2D textureSampler;\n" +
            "uniform float sunTextureRotation;\n" +
            "uniform bool isSun;\n" +
            "void main() {\n" +
            "    float distToSun = length(FragPos);\n" +
            "    if (distToSun < 4.0) {\n" +
            "        vec4 baseColor = hasTexture ? texture(textureSampler, TexCoord) : color;\n" +
            "        if (isSun) {\n" +
            "            vec2 distortedTexCoord = TexCoord;\n" +
            "            distortedTexCoord.x += 0.03 * sin(TexCoord.y * 10.0 + sunTextureRotation);\n" +
            "            distortedTexCoord.y += 0.02 * cos(TexCoord.x * 8.0 - sunTextureRotation);\n" +
            "            distortedTexCoord.x += 0.01 * sin(TexCoord.x * 15.0 + sunTextureRotation * 0.5);\n" +
            "            baseColor = hasTexture ? texture(textureSampler, distortedTexCoord) : color;\n" +
            "        }\n" +
            "        FragColor = baseColor;\n" +
            "    } else {\n" +
            "        vec3 lightDir = normalize(vec3(0.0) - FragPos);\n" +
            "        vec3 normal = normalize(Normal);\n" +
            "        float angle = dot(normal, lightDir);\n" +
            "        float smoothAngle = smoothstep(-0.45, 0.85, angle);\n" +
            "        float diff = max(smoothAngle, 0.175);\n" +
            "        vec4 baseColor = hasTexture ? texture(textureSampler, TexCoord) : color;\n" +
            "        vec3 result = diff * baseColor.rgb * 1.5;\n" +
            "        FragColor = vec4(min(result, vec3(1.0)), baseColor.a);\n" +
            "    }\n" +
            "}\n";

    private static final String STAR_VERTEX_SHADER = "#version 330 core\n" +
            "layout (location = 0) in vec3 aPos;\n" +
            "uniform mat4 view;\n" +
            "uniform mat4 projection;\n" +
            "uniform float pointSize;\n" +
            "void main() {\n" +
            "    gl_Position = projection * view * vec4(aPos, 1.0);\n" +
            "    gl_PointSize = pointSize;\n" +
            "}\n";

    private static final String STAR_FRAGMENT_SHADER = "#version 330 core\n" +
            "out vec4 FragColor;\n" +
            "uniform vec3 starColor;\n" +
            "void main() {\n" +
            "    FragColor = vec4(starColor, 1.0);\n" +
            "}\n";

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, "Solar System - Java", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key >= 0 && key < GLFW_KEY_LAST) {
                if (action == GLFW_PRESS) {
                    keys[key] = true;
                } else if (action == GLFW_RELEASE) {
                    keys[key] = false;
                }
            }
        });

        glfwSetCursorPosCallback(window, this::mouseCallback);
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            width = w;
            height = h;
            glViewport(0, 0, w, h);
        });

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        System.out.println("OpenGL Version: " + glGetString(GL_VERSION));
        System.out.println("OpenGL Vendor: " + glGetString(GL_VENDOR));
        System.out.println("OpenGL Renderer: " + glGetString(GL_RENDERER));

        printControls();

        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glFrontFace(GL_CCW);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        planetShaderProgram = ShaderUtils.createShaderProgram(VERTEX_SHADER, FRAGMENT_SHADER);
        starShaderProgram = ShaderUtils.createShaderProgram(STAR_VERTEX_SHADER, STAR_FRAGMENT_SHADER);
        sphereData = SphereGenerator.createSphere(30, 30);
        initializeStarLayers();

        initializePlanets();

        skybox = new Skybox();
        skybox.init(true);
        skybox.loadTexture("textures/skybox/equirectangular.jpg");

        textRenderer = new TextRenderer();
        if (!textRenderer.init()) {
            System.err.println("Warning: Text rendering initialization failed");
        }

        planetDetailsMap = PlanetDetails.createPlanetDetailsMap();

        ImGui.createContext();
        ImGuiIO io = ImGui.getIO();
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.setFontGlobalScale(2.0f);

        imGuiGlfw.init(window, true);
        imGuiGl3.init("#version 330");

        System.out.println("Initialization complete!");
    }

    private void initializePlanets() {
        planets.add(new Planet(0.0f, 3.0f, new Vector4f(1.25f, 1.1875f, 1.0f, 1.0f),
                0.075f, 0.5f * 0.15f / 24 * 10, "textures/sun.jpg",
                true, 0.0f, "Sun"));
        planets.get(planets.size() - 1).composition = "Hydrogen (73.46%), Helium (24.85%)";

        planets.add(new Planet(10.4f, 0.4f, new Vector4f(0.7f, 0.7f, 0.7f, 1.0f),
                -0.5f * 0.075f, 0.5f * 0.1f, "textures/mercury.jpg",
                true, 0.0f, "Mercury"));
        planets.get(planets.size() - 1).composition = "Core: Iron-Nickel (70%)";

        planets.add(new Planet(14.3f, 0.9f, new Vector4f(0.9f, 0.7f, 0.5f, 1.0f),
                -0.4f * 0.075f, 0.5f * 0.02f, "textures/venus.jpg",
                true, 0.0f, "Venus"));
        planets.get(planets.size() - 1).composition = "Core: Iron-Nickel, Atmosphere: CO2";

        planets.add(new Planet(18.2f, 1.0f, new Vector4f(0.2f, 0.5f, 1.0f, 1.0f),
                -0.3f * 0.075f, 0.5f * 0.15f, "textures/earth.jpg",
                true, 0.0f, "Earth"));
        planets.get(planets.size() - 1).composition = "Core: Iron-Nickel, Crust: Oxygen, Silicon";

        Planet moon = new Planet(1.5f, 0.05f, new Vector4f(0.8f, 0.8f, 0.8f, 1.0f),
                -1.25f * 0.075f, 0.5f * 0.15f, "textures/moon.jpg",
                true, 0.0f, "Moon");
        moon.mass = 7.349e22f;
        moon.composition = "Crust: Anorthosite, Mantle: Olivine";
        moon.surfaceTemp = 127.0f;
        moon.gravity = 1.62f;
        moon.orbitalPeriod = 27.3f;
        planets.get(planets.size() - 1).addSatellite(moon);

        planets.add(new Planet(23.4f, 0.5f, new Vector4f(1.0f, 0.5f, 0.2f, 1.0f),
                -0.2f * 0.075f, 0.5f * 0.15f, "textures/mars.jpg",
                true, 0.0f, "Mars"));
        planets.get(planets.size() - 1).composition = "Core: Iron-Nickel-Sulfur";

        planets.add(new Planet(31.2f, 1.8f, new Vector4f(0.8f, 0.7f, 0.6f, 1.0f),
                -0.1f * 0.075f, 0.5f * 0.4f, "textures/jupiter.jpg",
                true, 0.0f, "Jupiter"));
        planets.get(planets.size() - 1).composition = "Hydrogen (90%), Helium (10%)";

        planets.add(new Planet(38.7f, 1.5f, new Vector4f(0.9f, 0.8f, 0.6f, 1.0f),
                -0.08f * 0.075f, 0.5f * 0.45f, "textures/saturn.jpg",
                true, 0.0f, "Saturn"));
        planets.get(planets.size() - 1).composition = "Hydrogen (96.3%), Helium (3.25%)";
        planets.get(planets.size() - 1).addRing(1.35f, 1.9f, new Vector4f(0.9f, 0.8f, 0.7f, 0.6f));
        planets.get(planets.size() - 1).addRing(2.1f, 2.8f, new Vector4f(0.85f, 0.75f, 0.65f, 0.6f));

        planets.add(new Planet(45.6f, 1.2f, new Vector4f(0.6f, 0.8f, 1.0f, 1.0f),
                -0.06f * 0.075f, 0.5f * 0.5f, "textures/uranus.jpg",
                true, 0.0f, "Uranus"));
        planets.get(planets.size() - 1).composition = "Hydrogen (83%), Helium (15%), Methane (2%)";
        planets.get(planets.size() - 1).addRing(1.2f, 1.4f, new Vector4f(0.6f, 0.6f, 0.6f, 0.6f));

        planets.add(new Planet(53.1f, 1.2f, new Vector4f(0.4f, 0.5f, 1.0f, 1.0f),
                -0.05f * 0.075f, 0.5f * 0.55f, "textures/neptune.jpg",
                true, 0.0f, "Neptune"));
        planets.get(planets.size() - 1).composition = "Hydrogen (80%), Helium (19%), Methane (1%)";
    }

    private void mouseCallback(long window, double xpos, double ypos) {
        if (showImGuiMenu || glfwGetInputMode(window, GLFW_CURSOR) != GLFW_CURSOR_DISABLED) {
            return;
        }

        if (firstMouse) {
            lastX = xpos;
            lastY = ypos;
            firstMouse = false;
            return;
        }

        float xoffset = (float) (xpos - lastX) * mouseSensitivity;
        float yoffset = (float) (lastY - ypos) * mouseSensitivity;
        lastX = xpos;
        lastY = ypos;

        cameraYaw += xoffset;
        cameraPitch += yoffset;

        if (cameraPitch > 89.0f)
            cameraPitch = 89.0f;
        if (cameraPitch < -89.0f)
            cameraPitch = -89.0f;

        Vector3f front = new Vector3f();
        front.x = (float) (Math.cos(Math.toRadians(cameraYaw)) * Math.cos(Math.toRadians(cameraPitch)));
        front.y = (float) Math.sin(Math.toRadians(cameraPitch));
        front.z = (float) (Math.sin(Math.toRadians(cameraYaw)) * Math.cos(Math.toRadians(cameraPitch)));
        cameraFront = front.normalize();
    }

    private void processInput() {
        if (keys[GLFW_KEY_ESCAPE] && !escPressed) {
            if (showImGuiMenu) {
                if (showQuitConfirmation) {
                    showQuitConfirmation = false;
                    showImGuiMenu = false;
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                    firstMouse = true;
                } else {
                    showImGuiMenu = false;
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                    firstMouse = true;
                }
            } else {
                showQuitConfirmation = true;
                showImGuiMenu = true;
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
            escPressed = true;
        } else if (!keys[GLFW_KEY_ESCAPE]) {
            escPressed = false;
        }

        if (keys[GLFW_KEY_SPACE] && !spacePressed) {
            isPaused = !isPaused;
            spacePressed = true;
        } else if (!keys[GLFW_KEY_SPACE]) {
            spacePressed = false;
        }

        if (keys[GLFW_KEY_F1] && !f1Pressed) {
            toggleFullscreen();
            f1Pressed = true;
        } else if (!keys[GLFW_KEY_F1]) {
            f1Pressed = false;
        }

        if (keys[GLFW_KEY_F2] && !f2Pressed) {
            showText = !showText;
            System.out.println("F2 pressed - showText is now: " + showText);
            f2Pressed = true;
        } else if (!keys[GLFW_KEY_F2]) {
            f2Pressed = false;
        }

        if (keys[GLFW_KEY_F3] && !f3Pressed) {
            showImGuiMenu = !showImGuiMenu;
            if (showImGuiMenu) {
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            } else {
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                firstMouse = true;
            }
            f3Pressed = true;
        } else if (!keys[GLFW_KEY_F3]) {
            f3Pressed = false;
        }

        if (keys[GLFW_KEY_EQUAL] || keys[GLFW_KEY_KP_ADD]) {
            timeScale = Math.min(20.0f, timeScale + 0.05f);
        }
        if (keys[GLFW_KEY_MINUS] || keys[GLFW_KEY_KP_SUBTRACT]) {
            timeScale = Math.max(-20.0f, timeScale - 0.05f);
        }

        float currentSpeed = cameraSpeed;
        if (keys[GLFW_KEY_LEFT_SHIFT]) {
            currentSpeed *= 2.0f;
        }

        if (keys[GLFW_KEY_W]) {
            cameraPos.add(new Vector3f(cameraFront).mul(currentSpeed));
        }
        if (keys[GLFW_KEY_S]) {
            cameraPos.sub(new Vector3f(cameraFront).mul(currentSpeed));
        }
        if (keys[GLFW_KEY_A]) {
            cameraPos.sub(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(currentSpeed));
        }
        if (keys[GLFW_KEY_D]) {
            cameraPos.add(new Vector3f(cameraFront).cross(cameraUp).normalize().mul(currentSpeed));
        }
        if (keys[GLFW_KEY_E]) {
            cameraPos.add(new Vector3f(cameraUp).mul(currentSpeed));
        }
        if (keys[GLFW_KEY_Q]) {
            cameraPos.sub(new Vector3f(cameraUp).mul(currentSpeed));
        }

        if (keys[GLFW_KEY_C]) {
            skybox.rotationSpeed += 0.0001f;
        }
        if (keys[GLFW_KEY_V]) {
            skybox.rotationSpeed = Math.max(0.0001f, skybox.rotationSpeed - 0.0001f);
        }

        if (keys[GLFW_KEY_Z]) {
            starSpeedMultiplier += 0.01f;
        }
        if (keys[GLFW_KEY_X]) {
            starSpeedMultiplier = Math.max(0.0f, starSpeedMultiplier - 0.01f);
        }
    }

    private void toggleFullscreen() {
        GLFWVidMode mode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        if (mode == null) {
            return;
        }

        if (!isFullscreen) {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                IntBuffer pX = stack.mallocInt(1);
                IntBuffer pY = stack.mallocInt(1);
                IntBuffer pW = stack.mallocInt(1);
                IntBuffer pH = stack.mallocInt(1);
                glfwGetWindowPos(window, pX, pY);
                glfwGetWindowSize(window, pW, pH);
                windowedX = pX.get(0);
                windowedY = pY.get(0);
                windowedWidth = pW.get(0);
                windowedHeight = pH.get(0);
            }

            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, mode.width(), mode.height(),
                    mode.refreshRate());
            isFullscreen = true;
        } else {
            glfwSetWindowMonitor(window, NULL, windowedX, windowedY, windowedWidth, windowedHeight, 0);
            isFullscreen = false;
        }
    }

    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            float currentFrame = (float) glfwGetTime();
            deltaTime = currentFrame - lastFrameTime;
            lastFrameTime = currentFrame;

            processInput();
            render();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void render() {
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            width = pWidth.get(0);
            height = pHeight.get(0);
        }

        glViewport(0, 0, width, height);

        Matrix4f view = new Matrix4f().lookAt(cameraPos,
                new Vector3f(cameraPos).add(cameraFront), cameraUp);
        Matrix4f projection = new Matrix4f().perspective(
                (float) Math.toRadians(45.0f), (float) width / height, 0.1f, 2000.0f);

        skybox.render(projection, view, isPaused);
        updateStars();
        renderStars(view, projection);

        for (Planet planet : planets) {
            planet.render(planetShaderProgram, view, projection,
                    sphereData.VAO, sphereData.numIndices, isPaused, timeScale);
        }

        if (showText && textRenderer != null) {
            renderTextLabels(view, projection);
        }

        if (showImGuiMenu) {
            renderImGui();
        }
    }

    private void renderTextLabels(Matrix4f view, Matrix4f projection) {
        String pauseText = isPaused ? "PAUSED" : "";
        if (!pauseText.isEmpty()) {
            textRenderer.renderText(pauseText, width / 2.0f - 50.0f, 25.0f, 1.0f,
                    new Vector3f(1.0f, 1.0f, 1.0f), width, height);
        }

        for (Planet planet : planets) {
            Vector3f planetPos = new Vector3f(planet.position)
                    .add(0.0f, planet.size * 1.2f, 0.0f);

            Vector3f screenPos = worldToScreen(planetPos, view, projection);
            if (screenPos != null) {
                textRenderer.renderText(planet.name, screenPos.x - 30.0f, screenPos.y, 0.8f,
                        new Vector3f(1.0f, 1.0f, 1.0f), width, height);
            }
        }
    }

    private Vector3f worldToScreen(Vector3f worldPos, Matrix4f view, Matrix4f projection) {
        org.joml.Vector4f clipPos = new org.joml.Vector4f(worldPos, 1.0f);
        projection.transform(view.transform(clipPos));

        if (clipPos.w <= 0)
            return null;

        clipPos.x /= clipPos.w;
        clipPos.y /= clipPos.w;
        clipPos.z /= clipPos.w;

        if (Math.abs(clipPos.x) > 1.0f || Math.abs(clipPos.y) > 1.0f || Math.abs(clipPos.z) > 1.0f) {
            return null;
        }

        float screenX = (clipPos.x + 1.0f) * width / 2.0f;
        float screenY = (clipPos.y + 1.0f) * height / 2.0f;

        return new Vector3f(screenX, screenY, 0);
    }

    private void initializeStarLayers() {
        nearStarPositions = generateStars(nearStarCount);
        farStarPositions = generateStars(farStarCount);

        nearStarsVAO = glGenVertexArrays();
        nearStarsVBO = glGenBuffers();
        glBindVertexArray(nearStarsVAO);
        glBindBuffer(GL_ARRAY_BUFFER, nearStarsVBO);
        glBufferData(GL_ARRAY_BUFFER, nearStarPositions, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

        farStarsVAO = glGenVertexArrays();
        farStarsVBO = glGenBuffers();
        glBindVertexArray(farStarsVAO);
        glBindBuffer(GL_ARRAY_BUFFER, farStarsVBO);
        glBufferData(GL_ARRAY_BUFFER, farStarPositions, GL_DYNAMIC_DRAW);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private float[] generateStars(int count) {
        float[] positions = new float[count * 3];
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int idx = i * 3;
            positions[idx] = (random.nextFloat() * 2.0f - 1.0f) * starBoundary;
            positions[idx + 1] = (random.nextFloat() * 2.0f - 1.0f) * starBoundary;
            positions[idx + 2] = (random.nextFloat() * 2.0f - 1.0f) * starBoundary;
        }

        return positions;
    }

    private void updateStars() {
        if (isPaused) {
            return;
        }

        updateStarLayer(nearStarPositions, nearStarSpeed * starSpeedMultiplier, nearStarsVBO);
        updateStarLayer(farStarPositions, farStarSpeed * starSpeedMultiplier, farStarsVBO);
    }

    private void updateStarLayer(float[] starPositions, float speed, int vbo) {
        float movement = speed * deltaTime;
        for (int i = 2; i < starPositions.length; i += 3) {
            starPositions[i] -= movement;
            if (starPositions[i] < -starBoundary) {
                starPositions[i] += starBoundary * 2.0f;
            }
        }

        FloatBuffer buffer = BufferUtils.createFloatBuffer(starPositions.length);
        buffer.put(starPositions).flip();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void renderStars(Matrix4f view, Matrix4f projection) {
        glUseProgram(starShaderProgram);

        float[] viewArray = new float[16];
        float[] projArray = new float[16];
        view.get(viewArray);
        projection.get(projArray);

        glUniformMatrix4fv(glGetUniformLocation(starShaderProgram, "view"), false, viewArray);
        glUniformMatrix4fv(glGetUniformLocation(starShaderProgram, "projection"), false, projArray);

        glUniform1f(glGetUniformLocation(starShaderProgram, "pointSize"), 2.0f);
        glUniform3f(glGetUniformLocation(starShaderProgram, "starColor"), 1.0f, 1.0f, 1.0f);
        glBindVertexArray(nearStarsVAO);
        glDrawArrays(GL_POINTS, 0, nearStarCount);

        glUniform1f(glGetUniformLocation(starShaderProgram, "pointSize"), 1.0f);
        glUniform3f(glGetUniformLocation(starShaderProgram, "starColor"), 0.72f, 0.72f, 0.82f);
        glBindVertexArray(farStarsVAO);
        glDrawArrays(GL_POINTS, 0, farStarCount);

        glBindVertexArray(0);
    }

    private void renderImGui() {
        imGuiGlfw.newFrame();
        ImGui.newFrame();

        if (showQuitConfirmation) {
            ImGui.openPopup("Quit?");
            ImGui.setNextWindowPos(width / 2.0f, height / 2.0f, ImGuiCond.Appearing, 0.5f, 0.5f);

            if (ImGui.beginPopupModal("Quit?", ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoSavedSettings)) {
                ImGui.text("Are you sure you want to quit?");
                ImGui.separator();

                if (ImGui.button("Yes", 120, 0)) {
                    glfwSetWindowShouldClose(window, true);
                    showQuitConfirmation = false;
                    ImGui.closeCurrentPopup();
                }
                ImGui.sameLine();
                if (ImGui.button("No", 120, 0)) {
                    showQuitConfirmation = false;
                    showImGuiMenu = false;
                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                    firstMouse = true;
                    ImGui.closeCurrentPopup();
                }
                ImGui.endPopup();
            }
        }

        if (showImGuiMenu && !showQuitConfirmation) {
            ImGui.setNextWindowSize(600, 800, ImGuiCond.FirstUseEver);
            ImGui.begin("Solar System Information", ImGuiWindowFlags.AlwaysAutoResize);

            ImGui.textColored(1.0f, 1.0f, 0.0f, 1.0f, "Solar System Simulation");
            ImGui.separator();

            if (ImGui.beginTabBar("InfoTabs")) {
                if (ImGui.beginTabItem("General")) {
                    ImGui.text(String.format("Time Scale: %.2f", timeScale));
                    ImGui.sameLine();
                    if (ImGui.smallButton("+"))
                        timeScale = Math.min(20.0f, timeScale + 0.1f);
                    ImGui.sameLine();
                    if (ImGui.smallButton("-"))
                        timeScale = Math.max(-20.0f, timeScale - 0.1f);

                    ImGui.text(String.format("FPS: %.1f", 1.0f / deltaTime));
                    ImGui.text(String.format("Camera Position: (%.1f, %.1f, %.1f)", cameraPos.x, cameraPos.y,
                            cameraPos.z));

                    ImGui.separator();
                    ImGui.textColored(0.4f, 0.8f, 1.0f, 1.0f, "Simulation Controls");
                    if (ImGui.button(isPaused ? "Resume" : "Pause", 100, 0)) {
                        isPaused = !isPaused;
                    }
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Planets")) {
                    String[] planetNames = new String[planets.size()];
                    for (int i = 0; i < planets.size(); i++) {
                        planetNames[i] = planets.get(i).name;
                    }

                    ImGui.text("Select Planet:");
                    imgui.type.ImInt selectedPlanetImInt = new imgui.type.ImInt(selectedPlanet);
                    if (ImGui.combo("##PlanetSelect", selectedPlanetImInt, planetNames)) {
                        selectedPlanet = selectedPlanetImInt.get();
                    }

                    if (selectedPlanet >= 0 && selectedPlanet < planets.size()) {
                        Planet planet = planets.get(selectedPlanet);
                        ImGui.separator();
                        ImGui.textColored(0.4f, 0.8f, 1.0f, 1.0f, planet.name + " Details:");

                        if (ImGui.beginTable("PlanetProperties", 2)) {
                            ImGui.tableNextRow();
                            ImGui.tableNextColumn();
                            ImGui.text("Axial Tilt:");
                            ImGui.tableNextColumn();
                            ImGui.text(String.format("%.2f degrees", planet.axialTilt));

                            PlanetDetails details = planetDetailsMap.get(planet.name);
                            if (details != null) {
                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Surface Temp (Day):");
                                ImGui.tableNextColumn();
                                ImGui.text(String.format("%.2f C", details.surfaceTemperatureDay));

                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Surface Temp (Night):");
                                ImGui.tableNextColumn();
                                ImGui.text(String.format("%.2f C", details.surfaceTemperatureNight));

                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Distance from Sun:");
                                ImGui.tableNextColumn();
                                ImGui.text(String.format("%.2f million km", details.distanceFromSun));

                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Diameter:");
                                ImGui.tableNextColumn();
                                ImGui.text(String.format("%.2f km", details.diameter));

                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Rotation Period:");
                                ImGui.tableNextColumn();
                                ImGui.text(String.format("%.2f Earth days", details.rotationPeriod));

                                ImGui.tableNextRow();
                                ImGui.tableNextColumn();
                                ImGui.text("Atmosphere:");
                                ImGui.tableNextColumn();
                                ImGui.text(details.atmosphere);
                            }

                            ImGui.tableNextRow();
                            ImGui.tableNextColumn();
                            ImGui.text("Composition:");
                            ImGui.tableNextColumn();
                            ImGui.text(planet.composition);

                            ImGui.endTable();
                        }

                        if (!planet.satellites.isEmpty()) {
                            ImGui.separator();
                            ImGui.textColored(0.4f, 0.8f, 1.0f, 1.0f, "Satellites: " + planet.satellites.size());
                            if (ImGui.treeNode("Satellite Details")) {
                                for (Planet satellite : planet.satellites) {
                                    if (ImGui.treeNode(satellite.name)) {
                                        if (ImGui.beginTable("SatelliteProperties", 2)) {
                                            ImGui.tableNextRow();
                                            ImGui.tableNextColumn();
                                            ImGui.text("Surface Temp:");
                                            ImGui.tableNextColumn();
                                            ImGui.text(String.format("%.2f C", satellite.surfaceTemp));

                                            ImGui.tableNextRow();
                                            ImGui.tableNextColumn();
                                            ImGui.text("Gravity:");
                                            ImGui.tableNextColumn();
                                            ImGui.text(String.format("%.2f m/s²", satellite.gravity));

                                            ImGui.tableNextRow();
                                            ImGui.tableNextColumn();
                                            ImGui.text("Orbital Period:");
                                            ImGui.tableNextColumn();
                                            ImGui.text(String.format("%.2f Earth days", satellite.orbitalPeriod));

                                            ImGui.tableNextRow();
                                            ImGui.tableNextColumn();
                                            ImGui.text("Composition:");
                                            ImGui.tableNextColumn();
                                            ImGui.text(satellite.composition);

                                            ImGui.endTable();
                                        }
                                        ImGui.treePop();
                                    }
                                }
                                ImGui.treePop();
                            }
                        }

                        if (!planet.rings.isEmpty()) {
                            ImGui.separator();
                            ImGui.textColored(0.4f, 0.8f, 1.0f, 1.0f, "Rings: " + planet.rings.size());
                        }
                    }
                    ImGui.endTabItem();
                }

                if (ImGui.beginTabItem("Help")) {
                    ImGui.textColored(1.0f, 1.0f, 0.0f, 1.0f, "Controls:");
                    if (ImGui.beginTable("Controls", 2)) {
                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("WASD:");
                        ImGui.tableNextColumn();
                        ImGui.text("Move camera");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("Q/E:");
                        ImGui.tableNextColumn();
                        ImGui.text("Move camera Up/Down");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("C/V:");
                        ImGui.tableNextColumn();
                        ImGui.text("Adjust skybox rotation");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("Z/X:");
                        ImGui.tableNextColumn();
                        ImGui.text("Adjust star speed");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("Mouse:");
                        ImGui.tableNextColumn();
                        ImGui.text("Look around");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("Space:");
                        ImGui.tableNextColumn();
                        ImGui.text("Pause/Resume");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("+/-:");
                        ImGui.tableNextColumn();
                        ImGui.text("Adjust time scale");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("F1:");
                        ImGui.tableNextColumn();
                        ImGui.text("Toggle fullscreen");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("F2:");
                        ImGui.tableNextColumn();
                        ImGui.text("Toggle text display");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("F3:");
                        ImGui.tableNextColumn();
                        ImGui.text("Toggle this menu");

                        ImGui.tableNextRow();
                        ImGui.tableNextColumn();
                        ImGui.text("ESC:");
                        ImGui.tableNextColumn();
                        ImGui.text("Quit confirmation");

                        ImGui.endTable();
                    }
                    ImGui.endTabItem();
                }

                ImGui.endTabBar();
            }

            ImGui.end();
        }

        ImGui.render();
        imGuiGl3.renderDrawData(ImGui.getDrawData());
    }

    private void cleanup() {
        glDeleteVertexArrays(sphereData.VAO);
        glDeleteBuffers(sphereData.VBO);
        glDeleteBuffers(sphereData.EBO);
        glDeleteProgram(planetShaderProgram);
        glDeleteProgram(starShaderProgram);

        glDeleteVertexArrays(nearStarsVAO);
        glDeleteBuffers(nearStarsVBO);
        glDeleteVertexArrays(farStarsVAO);
        glDeleteBuffers(farStarsVBO);

        skybox.cleanup();

        if (textRenderer != null) {
            textRenderer.cleanup();
        }

        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();

        for (Planet planet : planets) {
            for (Ring ring : planet.rings) {
                ring.cleanup();
            }
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    private void printControls() {
        System.out.println("\nControls:");
        System.out.println("WASD: Move camera");
        System.out.println("Q/E: Move camera Up/Down");
        System.out.println("Mouse: Look around");
        System.out.println("Space: Pause/Resume");
        System.out.println("+/-: Adjust time scale");
        System.out.println("F1: Toggle fullscreen");
        System.out.println("F2: Toggle text display");
        System.out.println("F3: Toggle ImGui menu");
        System.out.println("C/V: Adjust skybox rotation speed");
        System.out.println("ESC: Quit confirmation");
    }

    public static void main(String[] args) {
        new SolarSystemApp().run();
    }
}
