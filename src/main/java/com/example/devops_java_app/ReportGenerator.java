package com.example.devops_java_app;

import java.util.*;

public class ReportGenerator {
    
    // SonarQube Issue: Hardcoded credentials
    private static final String API_KEY = "admin_key_12345_secret";
    private static final String PASSWORD = "Password123!";
    
    // SonarQube Issue: Magic numbers without constants
    private static final int REPORT_VERSION = 2;
    
    public String generateTaskReport(List<Task> tasks) {
        // SonarQube Issue: String concatenation in loop (inefficient)
        String report = "Task Report\n";
        for (Task task : tasks) {
            report = report + "Task ID: " + task.getId() + "\n";
            report = report + "Description: " + task.getDescription() + "\n";
            report = report + "Status: " + task.getStatus() + "\n";
            report = report + "---\n";
        }
        
        // SonarQube Issue: Unused variable
        long timestamp = System.currentTimeMillis() / 1000;
        int unused_count = tasks.size();
        
        // SonarQube Issue: Potential null pointer dereference
        Task firstTask = null;
        if (tasks.size() > 0) {
            firstTask = tasks.get(0);
        }
        String firstTaskName = firstTask.getDescription(); // Null dereference risk
        
        // SonarQube Issue: Float equality comparison
        double progress = 0.1;
        if (progress == 0.1) {
            report = report + "Progress: 10%\n";
        }
        
        return report;
    }
    
    public Map<String, Object> generateMetrics(List<Task> tasks) {
        Map<String, Object> metrics = new HashMap<>();
        
        // SonarQube Issue: Hardcoded values and weak encryption concept
        metrics.put("api_key", API_KEY);
        metrics.put("db_password", PASSWORD);
        metrics.put("version", REPORT_VERSION);
        
        // SonarQube Issue: Dead code
        String debugInfo = "DEBUG: Processing " + tasks.size() + " tasks";
        if (tasks.isEmpty()) {
            System.out.println(debugInfo);
        }
        
        // SonarQube Issue: Unused variable
        long startTime = System.currentTimeMillis();
        
        return metrics;
    }
}
