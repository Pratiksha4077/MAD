package com.example.rolex;

public class NotificationModel {
    public String message;
    public String timestamp;
    public String serviceName;

    public NotificationModel() {}

    public NotificationModel(String message, String timestamp, String serviceName) {
        this.message = message;
        this.timestamp = timestamp;
        this.serviceName = serviceName;
    }
}