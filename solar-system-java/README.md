# Solar System Visualization - Java Edition

A 3D solar system visualization application translated from C++ to Java using LWJGL (Lightweight Java Game Library).

## Features

- **3D Rendering**: Realistic planetary orbits and rotations using OpenGL
- **Textured Planets**: All planets rendered with high-quality textures
- **Saturn's Rings**: Beautiful ring system with transparency
- **Moon Orbit**: Earth's moon with proper orbital mechanics
- **Dynamic Skybox**: Equirectangular space background with rotation
- **Camera Controls**: Free-flying camera with mouse look
- **Time Control**: Speed up, slow down, or pause the simulation
- **Planetary Details**: Accurate axial tilts and rotation speeds
- **ImGui UI**: Interactive menu system with planet information panels
- **Text Rendering**: Planet labels and HUD elements using STB TrueType
- **Quit Confirmation**: Modal dialog to prevent accidental exits

## Requirements

- **Java 11 or higher**
- **Maven 3.6+**
- **OpenGL 3.3+ compatible graphics card**
- **Windows OS** (configured for Windows natives, can be adapted for Linux/Mac)

## Project Structure

```
solar-system-java/
├── pom.xml                          # Maven configuration
├── src/main/java/com/solarsystem/
│   ├── SolarSystemApp.java          # Main application class
│   ├── Planet.java                  # Planet rendering and physics
│   ├── Ring.java                    # Saturn's ring system
│   ├── Skybox.java                  # Space background
│   ├── TextureLoader.java           # Texture loading utility
│   ├── ShaderUtils.java             # Shader compilation
│   ├── SphereGenerator.java         # Sphere mesh generation
│   ├── TextRenderer.java            # Text rendering with STB TrueType
│   └── PlanetDetails.java           # Planet information data
└── README.md
```

## Dependencies

The project uses:
- **LWJGL 3.3.3**: OpenGL bindings for Java
  - lwjgl-core: Core functionality
  - lwjgl-glfw: Window and input management
  - lwjgl-opengl: OpenGL bindings
  - lwjgl-stb: Image loading and TrueType font rendering
- **JOML 1.10.5**: Java OpenGL Math Library for vectors and matrices
- **ImGui-Java 1.86.11**: Immediate mode GUI library for interactive menus

## Setup Instructions

### 1. Install Prerequisites

Ensure you have Java 11+ and Maven installed:

```bash
java -version
mvn -version
```

### 2. Prepare Texture Files

The application expects texture files in the parent directory structure:
```
solar_system_V_Alpha/
├── textures/
│   ├── sun.jpg
│   ├── mercury.jpg
│   ├── venus.jpg
│   ├── earth.jpg
│   ├── moon.jpg
│   ├── mars.jpg
│   ├── jupiter.jpg
│   ├── saturn.jpg
│   ├── uranus.jpg
│   ├── neptune.jpg
│   └── skybox/
│       └── equirectangular.jpg
└── solar-system-java/
    └── (this project)
```

**Note**: The texture paths in `SolarSystemApp.java` are set to `../solar_system_V_Alpha/textures/`. Adjust these paths if your directory structure is different.

### 3. Build the Project

Navigate to the project directory and build with Maven:

```bash
cd solar-system-java
mvn clean package
```

This will:
- Download all dependencies
- Compile the Java source files
- Create an executable JAR file

### 4. Run the Application

#### Option A: Using Maven
```bash
mvn exec:java -Dexec.mainClass="com.solarsystem.SolarSystemApp"
```

#### Option B: Using Java directly
```bash
java -cp target/solar-system-java-1.0-SNAPSHOT.jar com.solarsystem.SolarSystemApp
```

#### Option C: From your IDE
- Import the project as a Maven project
- Run the `SolarSystemApp.main()` method

## Controls

| Key/Input | Action |
|-----------|--------|
| **W/A/S/D** | Move camera forward/left/backward/right |
| **Q/E** | Move camera down/up |
| **Mouse** | Look around (camera rotation) |
| **Space** | Pause/Resume simulation |
| **+/-** | Increase/Decrease time scale |
| **F2** | Toggle text rendering (planet labels) |
| **F3** | Toggle ImGui menu |
| **C/V** | Increase/Decrease skybox rotation speed |
| **Shift** | Hold to move camera faster |
| **ESC** | Show quit confirmation dialog |

## Customization

### Adjusting Texture Paths

Edit `SolarSystemApp.java` in the `initializePlanets()` method to change texture paths:

```java
planets.add(new Planet(0.0f, 3.0f, new Vector4f(1.25f, 1.1875f, 1.0f, 1.0f), 
    0.075f, 0.5f * 0.15f / 24 * 10, "path/to/your/sun.jpg", 
    true, 0.0f, "Sun"));
```

### Platform-Specific Natives

The `pom.xml` is configured for Windows. To run on other platforms:

**For Linux:**
Replace `natives-windows` with `natives-linux` in the dependencies.

**For macOS:**
Replace `natives-windows` with `natives-macos` in the dependencies.

**For all platforms (larger download):**
Add all three native classifiers to each LWJGL dependency.

## Differences from C++ Version

### Feature Parity
The Java version now includes all major features from the C++ version:
- ✅ 3D planet rendering with textures
- ✅ Orbital mechanics and rotation
- ✅ Camera controls
- ✅ Skybox rendering
- ✅ Saturn's rings
- ✅ ImGui UI with planet information panels
- ✅ Text rendering for planet labels and HUD
- ✅ Quit confirmation dialog
- ❌ Star field background (can be added if desired)

### Technical Changes
- **Memory Management**: Java's garbage collection vs C++ manual memory management
- **Libraries**: LWJGL instead of GLFW/GLEW/GLM
- **Math**: JOML library instead of GLM
- **Shaders**: Embedded as strings instead of separate files

## Troubleshooting

### "Failed to load texture" errors
- Verify texture files exist at the specified paths
- Check that image files are valid JPG format
- Ensure paths use forward slashes `/` or properly escaped backslashes

### Black screen or no rendering
- Verify your graphics card supports OpenGL 3.3+
- Check console output for OpenGL errors
- Ensure LWJGL natives match your operating system

### Application crashes on startup
- Verify Java 11+ is installed
- Check that all Maven dependencies downloaded correctly
- Try running `mvn clean install` to rebuild

### Performance issues
- Reduce sphere detail in `SphereGenerator.createSphere(30, 30)` - use lower numbers
- Disable vsync by changing `glfwSwapInterval(1)` to `glfwSwapInterval(0)`

## ImGui UI Features

The Java version includes a comprehensive ImGui interface:

### General Tab
- Real-time FPS counter
- Time scale adjustment with +/- buttons
- Camera position display
- Pause/Resume button

### Planets Tab
- Dropdown selector for all planets
- Detailed information for each planet:
  - Axial tilt
  - Surface temperatures (day/night)
  - Distance from Sun
  - Diameter
  - Rotation period
  - Atmospheric composition
  - Planet composition
- Satellite information (for planets with moons)
- Ring information (for Saturn and Uranus)

### Help Tab
- Complete keyboard controls reference
- Quick access to all commands

## Future Enhancements

Potential additions:
- Add star field with parallax effect
- Include fullscreen toggle (F1 key)
- Implement more moons for Jupiter, Saturn, Uranus, Neptune
- Add orbital path visualization
- Include planet focus/tracking camera mode

## License

This is a translation of the original C++ solar system application. Please refer to the original project for licensing information.

## Credits

- **Original C++ Version**: Solar System V Alpha
- **Java Translation**: Converted to use LWJGL, JOML, and ImGui-Java
- **Libraries**: LWJGL, JOML, ImGui-Java, STB Image, STB TrueType

## Building from Source

```bash
# Clone or navigate to the project
cd solar-system-java

# Clean and compile
mvn clean compile

# Run tests (if any)
mvn test

# Package into JAR
mvn package

# Run the application
mvn exec:java -Dexec.mainClass="com.solarsystem.SolarSystemApp"
```

## Support

For issues or questions:
1. Check the console output for error messages
2. Verify all texture files are in the correct location
3. Ensure your system meets the requirements
4. Check that Maven dependencies are properly downloaded

---

**Enjoy exploring the solar system in Java!** 🌍🪐🌟
