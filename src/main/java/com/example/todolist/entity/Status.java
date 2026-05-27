package com.example.todolist.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "m_status")
@Data
public class Status {
	@Id
	@Column(name = "status_code")
	private String statusCode;
	
	@Column(name = "status_name")
	private String statusName;
}
