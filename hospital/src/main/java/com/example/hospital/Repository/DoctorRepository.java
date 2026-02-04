package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital.Entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
