package com.geekster.DoctorAppointmentBookingApp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientId;
    private String patientName;
    private String patientEmail;
    private String patientPassword;
    private Gender patientGender;
    private BloodGroup patientBloodGroup;
    private String patientContact;
    private LocalDate patientDateOfBirth;


}
