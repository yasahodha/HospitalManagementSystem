package com.example.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.model.UserDTO;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import com.example.util.Converter;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Converter converter;
	
	
	public UserDTO saveUser(User user)
	{
		return converter.convertUserToDTO(userRepository.save(user));
	}
	
	public List<UserDTO> getAllUsers()
	{
	  List<User> users = userRepository.findAll();
	  List<UserDTO> userDto = new ArrayList<>();
	  
	  for(User user : users)
	  {
		  userDto.add(converter.convertUserToDTO(user));
	  }
	  return userDto;
	}
	
	public UserDTO updateUser(int id, User user) {
		User existingUser = userRepository.findById(id).get();
		existingUser.setUserName(user.getUserName());
		existingUser.setEmail(user.getEmail());
		existingUser.setPassword(user.getPassword());
		return
				converter.convertUserToDTO(userRepository.save(existingUser));
		
	}
	@Override
	public String deactivateUser(int userId ) {
		userRepository.deleteById(userId);
		return "The user's account has been deactivated";
	}

}