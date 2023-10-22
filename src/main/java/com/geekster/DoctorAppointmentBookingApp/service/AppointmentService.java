package com.geekster.DoctorAppointmentBookingApp.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.geekster.DoctorAppointmentBookingApp.model.Appointment;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.ScheduleAppointmentDto;
import com.geekster.DoctorAppointmentBookingApp.repo.IAppointmentRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IDoctorRepo;
import com.geekster.DoctorAppointmentBookingApp.repo.IPatientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    @Autowired
    IAppointmentRepo appointmentRepo;

    @Autowired
    PTokenService pTokenService;

    @Autowired
    IPatientRepo patientRepo;

    @Autowired
    IDoctorRepo doctorRepo;


    public String scheduleAppointment(AuthenticationInputDto authenticationInputDto, Appointment appointment) {
        if(pTokenService.isAuthenticate(authenticationInputDto)){
            String email = authenticationInputDto.getEmail();
            Patient patient = patientRepo.findFirstByPatientEmail(email);
            appointment.setPatient(patient);
            // find the doctor

            Integer doctorId = appointment.getDoctor().getDoctorId();
            boolean isValidDoctor = doctorRepo.existsById(doctorId);

            if(isValidDoctor){
                appointment.setAppointmentCreationTime(LocalDateTime.now());
                appointmentRepo.save(appointment);
                return "Your appointment has been booked";
            }else{
                return "Doctor doesn't exist, Could not book an appointment";
            }

        }else{
            return "Unauthenticated access !";
        }

    }

    public String cancelAppointment(AuthenticationInputDto authenticationInputDto, Integer appointmentId) {
        if(pTokenService.isAuthenticate(authenticationInputDto)){
            String email = authenticationInputDto.getEmail();
            Patient exsitedPatient = patientRepo.findFirstByPatientEmail(email);
            Appointment existedAppointment = appointmentRepo.findById(appointmentId).orElseThrow();
            if(existedAppointment.getPatient().equals(exsitedPatient)){
                appointmentRepo.deleteById(appointmentId);
                return "appointment with "+existedAppointment.getDoctor().getDoctorName() +" has been canceled";
            }else{
                return "unauthorised access !";
            }

        }else{
            return "Unauthenticated access !";
        }
    }
}
