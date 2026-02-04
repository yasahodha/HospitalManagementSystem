package com.example.hospital.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Patient")

public class Patient {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name ="ID")
	Long id;
	String name;
	String gender;
	String dob;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	List<Appointment> appointments;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@JsonIgnore
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
		appointment.setPatient(null);
	}
}
