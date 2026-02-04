package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital.Entity.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

}
