package com.example.devops_java_app;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class TaskController {

    // Simple in-memory list to act as our database
    private List<Task> tasks = new ArrayList<>(Arrays.asList(
        new Task(1, "Install Docker", "Completed"),
        new Task(2, "Build Spring Boot JAR", "In Progress"),
        new Task(3, "Configure SonarQube", "Pending")
    ));

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return tasks;
    }

    @GetMapping("/system-info")
    public Map<String, String> getSystemInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("os", System.getProperty("os.name"));
        info.put("java_version", System.getProperty("java.version"));
        info.put("owner", "Ikramuddin");
        return info;
    }

    @GetMapping("/report")
    public String getTaskReport() {
        ReportGenerator generator = new ReportGenerator();
        return generator.generateTaskReport(tasks);
    }

    @GetMapping("/metrics")
    public Map<String, Object> getMetrics() {
        ReportGenerator generator = new ReportGenerator();
        return generator.generateMetrics(tasks);
    }
}