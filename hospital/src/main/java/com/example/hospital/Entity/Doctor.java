package com.example.hospital.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name ="Doctor")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Column(name = "id")
	Long id;
	String name;
	String speciality;
	
	@OneToMany(mappedBy = "doctor")
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

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@JsonIgnore
	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	@Override
	public String toString() {
		return "Doctor [id=" + id + ", name=" + name + ", speciality=" + speciality + ", appointments=" + appointments
				+ "]";
	}
	 
	public void removeAppointment(Appointment appointment) {
		appointments.remove(appointment);
		appointment.setDoctor(null);
	}
	
	
	

}
