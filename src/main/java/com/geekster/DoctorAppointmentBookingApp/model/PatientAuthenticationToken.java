package com.geekster.DoctorAppointmentBookingApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAuthentication")
public class PatientAuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;
    private String tokenValue;
    private LocalDateTime tokenCreationTime;


    // Each token should be linked with a patient
    @OneToOne
    @JoinColumn(name = "fk_patient_id")
    Patient patient;

}
