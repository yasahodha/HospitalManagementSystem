package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital.Entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}
