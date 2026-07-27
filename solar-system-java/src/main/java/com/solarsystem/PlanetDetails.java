package com.solarsystem;

import java.util.HashMap;
import java.util.Map;

public class PlanetDetails {
    public float surfaceTemperatureDay;
    public float surfaceTemperatureNight;
    public float distanceFromSun;
    public float diameter;
    public float rotationPeriod;
    public String atmosphere;
    
    public PlanetDetails(float tempDay, float tempNight, float distance, float diameter, 
                        float rotation, String atmosphere) {
        this.surfaceTemperatureDay = tempDay;
        this.surfaceTemperatureNight = tempNight;
        this.distanceFromSun = distance;
        this.diameter = diameter;
        this.rotationPeriod = rotation;
        this.atmosphere = atmosphere;
    }
    
    public static Map<String, PlanetDetails> createPlanetDetailsMap() {
        Map<String, PlanetDetails> map = new HashMap<>();
        
        map.put("Mercury", new PlanetDetails(430.0f, -180.0f, 57.9f, 4879.0f, 58.6f, 
            "Minimal - Traces of Oxygen, Sodium, Hydrogen"));
        
        map.put("Venus", new PlanetDetails(462.0f, 462.0f, 108.2f, 12104.0f, 243.0f, 
            "Carbon Dioxide, Nitrogen, Sulfuric Acid"));
        
        map.put("Earth", new PlanetDetails(30.0f, -50.0f, 149.6f, 12742.0f, 1.0f, 
            "Nitrogen, Oxygen, Argon"));
        
        map.put("Mars", new PlanetDetails(20.0f, -140.0f, 227.9f, 6779.0f, 1.03f, 
            "Carbon Dioxide, Nitrogen, Argon"));
        
        map.put("Jupiter", new PlanetDetails(-145.0f, -145.0f, 778.5f, 139820.0f, 0.41f, 
            "Hydrogen, Helium, Methane"));
        
        map.put("Saturn", new PlanetDetails(-178.0f, -178.0f, 1433.5f, 116460.0f, 0.43f, 
            "Hydrogen, Helium, Methane"));
        
        map.put("Uranus", new PlanetDetails(-224.0f, -224.0f, 2872.5f, 50724.0f, 0.72f, 
            "Hydrogen, Helium, Methane"));
        
        map.put("Neptune", new PlanetDetails(-214.0f, -214.0f, 4495.1f, 49244.0f, 0.67f, 
            "Hydrogen, Helium, Methane"));
        
        map.put("Sun", new PlanetDetails(5505.0f, 5505.0f, 0.0f, 1392700.0f, 25.4f, 
            "Hydrogen, Helium"));
        
        map.put("Moon", new PlanetDetails(127.0f, -173.0f, 149.6f, 3474.0f, 27.3f, 
            "Virtually None - Traces of Helium, Argon"));
        
        return map;
    }
}
