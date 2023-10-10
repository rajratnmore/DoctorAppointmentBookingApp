package com.geekster.DoctorAppointmentBookingApp.controller;

import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    // Sign Up
    @PostMapping("patient/signup")
    public String patientSignUp(@RequestBody Patient patient){
        return patientService.patientSignUp(patient);
    }


    // Sing In



    // Sing Out



}
