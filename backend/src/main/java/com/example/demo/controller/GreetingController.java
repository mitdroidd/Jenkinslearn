package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
// EDIT ME: tighten this before any real deployment - wide open for local dev only
@CrossOrigin(origins = "*")
public class GreetingController {

    // These values come from application.properties - see the EDIT ME
    // markers there. Change them, rebuild, redeploy, and refresh the
    // frontend to see your change flow end-to-end through Jenkins.
    @Value("${app.message}")
    private String message;

    @Value("${app.version}")
    private String version;

    @GetMapping("/api/greeting")
    public Map<String, String> greeting() {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", message);
        response.put("version", version);
        return response;
    }
}
