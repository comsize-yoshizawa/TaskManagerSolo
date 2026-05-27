package com.example.todolist.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.todolist.entity.Category;
import com.example.todolist.entity.Comment;
import com.example.todolist.entity.Status;
import com.example.todolist.entity.User;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskDto {

	private Integer taskId;
	private String taskName;
	private Category category;
	private LocalDate limitDate;
	private User user;
	private Status status;
	private String memo;
	private LocalDateTime createDatetime;
	private LocalDateTime updateDatetime;
	private List<Comment> comment;
	
	
}
