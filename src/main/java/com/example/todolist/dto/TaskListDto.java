package com.example.todolist.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskListDto {

	private Integer taskId;
	private String taskName;
	
	private String categoryName;
	
	private LocalDate limitDate;
	
	private String userName;
	
	private String statusName;
	
	private String memo;
}
