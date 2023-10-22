package com.geekster.DoctorAppointmentBookingApp.repo;

import com.geekster.DoctorAppointmentBookingApp.model.PatientAuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPTokenRepo extends JpaRepository<PatientAuthenticationToken, Integer> {
    PatientAuthenticationToken findFirstByTokenValue(String tokenValue);
}
