package com.geekster.DoctorAppointmentBookingApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAuthentication")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = PatientAuthenticationToken.class, property = "tokenId")
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

    public PatientAuthenticationToken(Patient existedPatient) {
        this.patient = existedPatient;
        this.tokenCreationTime = LocalDateTime.now();
        this.tokenValue = UUID.randomUUID().toString();
    }
}
