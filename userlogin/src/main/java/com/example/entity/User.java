package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	@Column(length = 20)
	private String userName;
	@Column(length = 20)
	private String password;
	@Column(length = 50)
	private String email;
	
	private boolean status = Boolean.FALSE;

}
