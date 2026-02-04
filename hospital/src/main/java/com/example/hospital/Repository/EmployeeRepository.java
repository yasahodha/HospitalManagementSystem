package com.example.hospital.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.hospital.Entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
