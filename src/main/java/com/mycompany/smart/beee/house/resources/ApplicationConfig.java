package com.mycompany.smart.beee.house.resources;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Configuration class for the Smart Bee House web services
 * This class defines the base URI path and registers the CORS filter
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {

    /**
     * Register the CORS filter for cross-origin requests
     * @return A set containing only the CORS filter
     */
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        
        // Add CORS filter for Angular frontend communication
        resources.add(CorsFilter.class);
        
        return resources;
    }
}