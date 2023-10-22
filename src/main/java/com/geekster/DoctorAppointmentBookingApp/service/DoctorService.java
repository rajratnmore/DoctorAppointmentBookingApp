package com.geekster.DoctorAppointmentBookingApp.service;

import com.geekster.DoctorAppointmentBookingApp.model.Doctor;
import com.geekster.DoctorAppointmentBookingApp.model.Qualification;
import com.geekster.DoctorAppointmentBookingApp.model.Specialization;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IDoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    IDoctorRepo doctorRepo;

    @Autowired
    PTokenService pTokenService;

    public List<Doctor> getAllDoctors(AuthenticationInputDto authenticationInputDto) {
        if(pTokenService.isAuthenticate(authenticationInputDto)){
            return doctorRepo.findAll();
        }else{
            return null;
        }
    }

    public String addDoctor(Doctor newDoctor) {
        Integer doctorId = newDoctor.getDoctorId();
        if(doctorId!= null && doctorRepo.existsById(doctorId)) {
            return "this doctor already exist";
        }
        newDoctor.setAppointments(null);
        doctorRepo.save(newDoctor);
        return "new doctor has been added";

    }

    public Doctor getDoctorById(Integer doctorId) {
        return doctorRepo.findById(doctorId).orElse(null);
    }

    public List<Doctor> getDoctorsByQualificationOrSpec(AuthenticationInputDto authenticationInputDto, Qualification qual, Specialization spec) {

        if(pTokenService.isAuthenticate(authenticationInputDto)){
            List<Doctor> doctors = doctorRepo.findByDoctorQualificationOrDoctorSpecialization(qual, spec);
            return doctors.stream().peek(doctor -> doctor.setAppointments(null)).collect(Collectors.toList()) ;
        }else{
            return null;
        }

    }
}
