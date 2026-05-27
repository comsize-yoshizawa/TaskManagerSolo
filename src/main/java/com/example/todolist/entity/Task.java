package com.example.todolist.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "t_task")
public class Task {
	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer taskId;
	
	@Column(name = "task_name")
	private String taskName;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Column(name = "limit_date")
	private LocalDate limitDate;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "status_code")
	private Status status;
	
	@Column(name = "memo")
	private String memo;
	
	@Column(name = "create_datetime",insertable = false, updatable = false)
	private LocalDateTime createDatetime;
	
	@Column(name = "update_datetime",insertable = false, updatable = false)
	private LocalDateTime updateDatetime;
	
	@OneToMany(
			mappedBy = "task",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<Comment> comment;
}
