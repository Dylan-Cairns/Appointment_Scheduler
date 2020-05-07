package model;

import java.time.LocalDateTime;

public class Appointment {
    private int appointmentId;
    private final Customer customer;
    private final int userId;
    private String apptType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Appointment(int appointmentId, Customer customer, int userId,
                       String apptType, LocalDateTime startTime, LocalDateTime endTime) {
        this.appointmentId = appointmentId;
        this.customer = customer;
        this.userId = userId;
        this.apptType = apptType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Appointment(Customer customer, int userId, String apptType,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.customer = customer;
        this.userId = userId;
        this.apptType = apptType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Appointment(Customer customer) {
        this.customer = customer;
        //set userId to -1; this will be allow us to test whether
        // the appointment has been properly initialized in the add edit appt controller
        this.userId = -1;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getUserId() {
        return userId;
    }

    public String getApptType() {
        return apptType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}

