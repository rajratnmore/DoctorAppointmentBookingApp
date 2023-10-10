package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    public String patientSignUp(Patient patient){
        // check if already exist ->   Not allowed : try logging in
        String newEmail = patient.getPatientEmail();
        Patient existedPatient = patientRepo.findFirstByPatientEmail(newEmail);
        if(existedPatient != null){
            return "email already exit";
        }

        // passwords are encrypted before we store it in the table
        String signUpPassword = patient.getPatientPassword();
        try {
            String encryptedPassword = PasswordEncryptor.getEncryptedPassword(signUpPassword);
            patient.setPatientPassword(encryptedPassword);
            patientRepo.save(patient);
            return "patient registered";
        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issue while saving password, try again later !";
        }

    }
}
