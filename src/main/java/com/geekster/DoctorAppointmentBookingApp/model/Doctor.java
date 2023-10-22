package com.geekster.DoctorAppointmentBookingApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Doctor.class, property = "doctorId")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer doctorId;
    private String doctorName;
    private String doctorContact;
    private double doctorFee;
    @Enumerated(value = EnumType.STRING)
    private Qualification doctorQualification;
    @Enumerated(value = EnumType.STRING)
    private Specialization doctorSpecialization;

    @OneToMany(mappedBy = "doctor")
    List<Appointment> appointments;

}
