package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<com.example.model.UserDTO> saveUser(@RequestBody User user){
		
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}
	@GetMapping("/getAllUsers")
	public List<com.example.model.UserDTO> getAllUsers()
	{
		return userService.getAllUsers();
	}
	@PutMapping("/updateUser{id}")
	public ResponseEntity<com.example.model.UserDTO> updateUser(
			@PathVariable("id") int id, @RequestBody com.example.entity.User user){
		    return new ResponseEntity<>(userService.updateUser(id, user),HttpStatus.OK);
	}
	@DeleteMapping("/deleteUser?{id}")
	public String deactivateUser(@PathVariable("id") int userId) {
		return userService.deactivateUser(userId);
	}
	
	

}
