package com.geekster.DoctorAppointmentBookingApp.controller;

import com.geekster.DoctorAppointmentBookingApp.model.Doctor;
import com.geekster.DoctorAppointmentBookingApp.model.Patient;
import com.geekster.DoctorAppointmentBookingApp.model.Specialization;
import com.geekster.DoctorAppointmentBookingApp.model.Qualification;
import com.geekster.DoctorAppointmentBookingApp.model.dto.AuthenticationInputDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.ScheduleAppointmentDto;
import com.geekster.DoctorAppointmentBookingApp.model.dto.SignInInputDto;
import com.geekster.DoctorAppointmentBookingApp.service.AppointmentService;
import com.geekster.DoctorAppointmentBookingApp.service.DoctorService;
import com.geekster.DoctorAppointmentBookingApp.service.PatientService;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class PatientController {

    @Autowired
    PatientService patientService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    // Sign Up
    @PostMapping("patient/signUp")
    public String patientSignUp(@Valid @RequestBody Patient patient){
        return patientService.patientSignUp(patient);
    }


    // Sing
    @PostMapping("patient/signIn")
    public String patientSignIn(@Valid @RequestBody SignInInputDto signInInputDto){
        return patientService.patientSignIn(signInInputDto);
    }



    // Sing Out
    @PostMapping("patient/singOut")
    public String patientSignOut(@Valid @RequestBody AuthenticationInputDto authenticationInputDto){
        return patientService.patientSignOut(authenticationInputDto);
    }


    // schedule an appointment

    @PostMapping("patient/appointment/schedule")
    public String scheduleAppointment(@RequestBody ScheduleAppointmentDto scheduleAppointmentDto){
        return appointmentService.scheduleAppointment(scheduleAppointmentDto.getAuthenticationInputDto(), scheduleAppointmentDto.getAppointment());
    }

    @DeleteMapping("patient/appointment/cancel/{appointmentId}")
    public String cancelAppointment(@Valid @RequestBody AuthenticationInputDto authenticationInputDto, @PathVariable Integer appointmentId){
        return appointmentService.cancelAppointment(authenticationInputDto, appointmentId);
    }

    @GetMapping("doctors/qualification/{qual}/or/specialization/{spec}")
    public List<Doctor> getDoctorsByQualificationOrSpec(@Valid @RequestBody AuthenticationInputDto authenticationInputDto, @PathVariable Qualification qual, @PathVariable Specialization spec){
        return doctorService.getDoctorsByQualificationOrSpec(authenticationInputDto, qual, spec);
    }

}
