package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.dto.UserDto;
import com.example.todolist.entity.User;
import com.example.todolist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository repository;
	
	public List<UserDto> getAllUsers(){
		List<User> userList = repository.findAll();
		return userList.stream()
				.map(UserDto::from).toList();
	}
	
	public User findById(String id) {
		return repository.getReferenceById(id);
	}
}
