package com.example.service;

import java.util.List;

import com.example.entity.User;
import com.example.model.UserDTO;

public interface UserService {
	UserDTO saveUser(User user);
	
	List<UserDTO> getAllUsers();
	
	UserDTO updateUser(int id, User user);
	
	String deactivateUser(int userId);
	

}