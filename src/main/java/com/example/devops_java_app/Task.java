package com.example.devops_java_app;

public class Task {
    private int id;
    private String description;
    private String status;

    public Task(int id, String description, String status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    // Getters are required for Spring to convert this to JSON
    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
}