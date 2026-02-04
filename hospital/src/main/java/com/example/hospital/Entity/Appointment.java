package com.example.hospital.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Appointment")
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id")
	Long id;
	String appointmentDate;
	String appointmentTime;
	String details;
	
	@ManyToOne
	@JoinColumn(name = "patient_id")
	@JsonIgnoreProperties({"appointments"})
	Patient patient;
	
	@ManyToOne
	@JoinColumn(name = "doctore_id")
	@JsonIgnoreProperties({"appointments"})
	Doctor doctor;

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", appointmentDate=" + appointmentDate + ", appointmentTime=" + appointmentTime
				+ ", details=" + details + ", patient=" + patient + ", doctor=" + doctor + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	

	

}
