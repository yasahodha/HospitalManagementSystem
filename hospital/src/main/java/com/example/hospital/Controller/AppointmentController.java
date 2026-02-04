package com.example.hospital.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hospital.Entity.Appointment;
import com.example.hospital.Entity.Doctor;
import com.example.hospital.Entity.Patient;
import com.example.hospital.Repository.AppointmentRepository;
import com.example.hospital.Repository.DoctorRepository;
import com.example.hospital.Repository.PatientRepository;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    AppointmentRepository appRep;

    @Autowired
    DoctorRepository docRep;

    @Autowired
    PatientRepository patRep;

    @GetMapping
    public List<AppointmentSummary> getAll() {
        return appRep.findAll().stream()
                .map(AppointmentSummary::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentSummary> getById(@PathVariable Long id) {
        return appRep.findById(id)
                .map(appointment -> ResponseEntity.ok(AppointmentSummary.from(appointment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAppointment(@RequestBody AppointmentRequest request) {
        if (request.patientName == null || request.gender == null || request.dob == null
                || request.doctorId == null || request.appointmentDate == null || request.appointmentTime == null) {
            return new ResponseEntity<>("Missing fields", HttpStatus.BAD_REQUEST);
        }

        Doctor doctor = docRep.findById(request.doctorId).orElse(null);
        if (doctor == null) {
            return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
        }

        Patient patient = new Patient();
        patient.setName(request.patientName);
        patient.setGender(request.gender);
        patient.setDob(request.dob);
        patient = patRep.save(patient);

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.appointmentDate);
        appointment.setAppointmentTime(request.appointmentTime);
        appointment.setDetails(request.details);

        Appointment saved = appRep.save(appointment);
        return new ResponseEntity<>(AppointmentSummary.from(saved), HttpStatus.CREATED);
    }

    // Update an appointment
    @PostMapping("/update/{id}")
    public ResponseEntity<AppointmentSummary> updateAppointment(@PathVariable Long id, @RequestBody Appointment aDetails) {
        Appointment a = appRep.findById(id).orElse(null);
        if (a == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        a.setPatient(aDetails.getPatient());
        a.setDoctor(aDetails.getDoctor());
        a.setAppointmentDate(aDetails.getAppointmentDate());
        a.setAppointmentTime(aDetails.getAppointmentTime());
        a.setDetails(aDetails.getDetails());

        final Appointment updatedAppointment = appRep.save(a);
        return new ResponseEntity<>(AppointmentSummary.from(updatedAppointment), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        return appRep.findById(id).map(appointment -> {
            appointment.getPatient().removeAppointment(appointment);
            appointment.getDoctor().removeAppointment(appointment);
            appRep.delete(appointment);
            return ResponseEntity.ok().build();
        }).orElseThrow();
    }

    static class AppointmentRequest {
        public String patientName;
        public String gender;
        public String dob;
        public Long doctorId;
        public String appointmentDate;
        public String appointmentTime;
        public String details;
    }

    static class AppointmentSummary {
        public Long id;
        public String patientName;
        public String doctorName;
        public String appointmentDate;
        public String appointmentTime;
        public String details;

        static AppointmentSummary from(Appointment appointment) {
            AppointmentSummary summary = new AppointmentSummary();
            summary.id = appointment.getId();
            summary.patientName = appointment.getPatient() != null ? appointment.getPatient().getName() : null;
            summary.doctorName = appointment.getDoctor() != null ? appointment.getDoctor().getName() : null;
            summary.appointmentDate = appointment.getAppointmentDate();
            summary.appointmentTime = appointment.getAppointmentTime();
            summary.details = appointment.getDetails();
            return summary;
        }
    }
}
