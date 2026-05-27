package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.dto.StatusDto;
import com.example.todolist.entity.Status;
import com.example.todolist.repository.StatusRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusService {
	private final StatusRepository repository;
	
	public List<StatusDto> getAllStatus(){
		List<Status> statusList = repository.findAll();
		return statusList.stream()
				.map(StatusDto::from).toList();
	}
	
	public Status findById(String id) {
		return repository.getReferenceById(id);
	}
}
