package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IPTokenRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PTokenService {

    @Autowired
    IPTokenRepo pTokenRepo;

    public void createToken(PatientAuthenticationToken token) {
        pTokenRepo.save(token);
    }

    public void deleteToken(String tokenValue) {
        PatientAuthenticationToken token = pTokenRepo.findFirstByTokenValue(tokenValue);
        pTokenRepo.delete((token));
    }

    public boolean isAuthenticate(AuthenticationInputDto authenticationInputDto) {
        String email = authenticationInputDto.getEmail();
        String tokenValue = authenticationInputDto.getTokenValue();

        /* find the actual token and then get connected patient with it and then verify by passed email with it. */

        PatientAuthenticationToken patientAuthenticationToken = pTokenRepo.findFirstByTokenValue(tokenValue);
        if(patientAuthenticationToken != null){
            return patientAuthenticationToken.getPatient().getPatientEmail().equals(email);
        }else {
            return false;
        }
    }
}
