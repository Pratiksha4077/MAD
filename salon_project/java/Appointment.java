package com.example.rolex;

public class Appointment {
    public String appointmentId;
    public String serviceName;
    public String userName; // Added field
    public String date;
    public String time;
    public String status;
    public String userId;

    public Appointment() {}

    public Appointment(String appointmentId, String userName, String serviceName, String date, String time, String status, String userId) {
        this.appointmentId = appointmentId;
        this.userName = userName; // Added to constructor
        this.serviceName = serviceName;
        this.date = date;
        this.time = time;
        this.status = status;
        this.userId = userId;
    }
}