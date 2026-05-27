package com.example.todolist.dto;

import com.example.todolist.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
	private String userId;
	private String userName;
	
	public static UserDto from(User user) {
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setUserName(user.getUserName());
		
		return dto;
	}
}
