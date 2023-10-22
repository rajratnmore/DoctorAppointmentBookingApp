package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.BloodGroup;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.ScheduleAppointmentDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    PTokenService pTokenService;

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
            patient.setAppointments(null);
            patientRepo.save(patient);
            return "patient registered";
        } catch (NoSuchAlgorithmException e) {
            return "Internal Server issue while saving password, try again later !";
        }

    }

    public String patientSignIn(SignInInputDto signInInputDto) {
        // check if user only be there
        String email = signInInputDto.getEmail();
        Patient existedPatient = patientRepo.findFirstByPatientEmail(email);
        if(existedPatient == null){
            return "Such patient is not existed !";
        }

        try {
            String password = PasswordEncryptor.getEncryptedPassword(signInInputDto.getPassword());
            if(existedPatient.getPatientPassword().equals(password)){
                // Issue token
                PatientAuthenticationToken token = new PatientAuthenticationToken(existedPatient);
                if(EmailService.sendMail(email, "OTP after body", token.getTokenValue())){
                    pTokenService.createToken(token);
                    return "check email for OTP / Token";
                }else{
                    return "Please enter a valid email id";
                }

            }else{
                return "Invalid credentials !";
            }

        } catch (NoSuchAlgorithmException e) {
            return "Ops Something has been wrong please try again later";
        }

    }

    public String patientSignOut(AuthenticationInputDto authenticationInputDto) {


        if (pTokenService.isAuthenticate(authenticationInputDto)){
            String tokenValue = authenticationInputDto.getTokenValue();
            pTokenService.deleteToken(tokenValue);
            return "Successfully signed out";
        }else{
            return "Unauthenticated access !";
        }


    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public List<Patient> getAllPatientsByBloodGroup(BloodGroup bloodGroup) {
        List<Patient> patients = patientRepo.findByPatientBloodGroup(bloodGroup);

        for(Patient patient : patients){
            patient.setAppointments(null);
        }
        return patients;

    }
}
