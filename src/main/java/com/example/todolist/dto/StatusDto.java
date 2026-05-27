package com.example.todolist.dto;

import com.example.todolist.entity.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDto {
	private String statusCode;
	private String statusName;
	
	public static StatusDto from(Status status) {
		StatusDto dto = new StatusDto();
		dto.setStatusCode(status.getStatusCode());
		dto.setStatusName(status.getStatusName());
		
		return dto;
	}
}


