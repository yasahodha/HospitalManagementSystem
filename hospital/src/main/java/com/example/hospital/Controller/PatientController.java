package com.example.hospital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Entity.Patient;
import com.example.hospital.Repository.PatientRepository;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    PatientRepository patRep;

    @GetMapping
    public List<Patient> getAll() {
        return patRep.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patRep.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> createPatient(@RequestBody Patient p) {
        if (p.getName() == null || p.getGender() == null || p.getDob() == null) {
            return new ResponseEntity<>("Missing fields", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(patRep.save(p), HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient pDetails) {
        Patient p = patRep.findById(id).orElse(null);
        if (p != null) {
            p.setName(pDetails.getName());
            p.setGender(pDetails.getGender());
            p.setDob(pDetails.getDob());
            patRep.save(p);
        }
        return p;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patRep.deleteById(id);
        return "Patient deleted with ID: " + id;
    }
}
