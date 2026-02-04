package com.example.hospital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.hospital.Entity.Doctor;
import com.example.hospital.Repository.DoctorRepository;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorRepository docRep;

    @GetMapping
    public List<Doctor> getAll() {
        return docRep.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return docRep.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Doctor createDoctor(@RequestBody Doctor d) {
        return docRep.save(d);
    }

    @PostMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor d1) {
        Doctor d = docRep.findById(id).orElse(null);
        if (d != null) {
            d.setName(d1.getName());
            d.setSpeciality(d1.getSpeciality());
            return docRep.save(d);
        }
        return null;
    }

    @PostMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        docRep.deleteById(id);
        return "Doctor deleted with ID: " + id;
    }
}
