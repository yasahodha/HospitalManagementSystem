package com.example.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.model.UserDTO;
@Component
public class Converter {
	
	public User convertUserDTOToUser(UserDTO userDto)
	{
		User user = new User();
		
		if(userDto!=null)
		{
			BeanUtils.copyProperties(userDto, user);
		}
		return user;

}
	
	//convert from entity to dto
	public UserDTO convertUserToDTO(User user)
	{
		UserDTO userDto = new UserDTO();
		
		if(user!=null)
		{
			BeanUtils.copyProperties(user, userDto);
		}
		return userDto;
	}
}