package com.example.todolist.form;


import java.time.LocalDate;

import com.example.todolist.entity.Category;
import com.example.todolist.entity.Status;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskData {
	private Integer taskId;
	
	@NotBlank(message = "件名を入力してください")
	private String taskName;
	
	@NotNull(message = "カテゴリーを選択してください")
	private Integer categoryId;
	
	@FutureOrPresent(message = "本日以降にしてください")
	@NotNull(message  = "日付を入力してください")
	private LocalDate limitDate;
	
	@NotNull(message = "担当者を選択してください")
	private String userId;
	
	@NotNull(message = "ステータスを選択してください")
	private String statusCode;
	
	private String memo;
	
	public Task toEntity(Category category, User user, Status status) {
		Task task = new Task();
		
		task.setTaskId(taskId);
		task.setTaskName(taskName);
		task.setCategory(category);
		task.setLimitDate(limitDate);
		task.setUser(user);
		task.setStatus(status);
		task.setMemo(memo);
		
		return task;
	}
	
	public static TaskData toForm(Task task) {
		TaskData data = new TaskData();
		
		data.setTaskId(task.getTaskId());
		data.setTaskName(task.getTaskName());
		data.setCategoryId(task.getCategory().getCategoryId());
		data.setLimitDate(task.getLimitDate());
		data.setUserId(task.getUser().getUserId());
		data.setStatusCode(task.getStatus().getStatusCode());
		data.setMemo(task.getMemo());
		return data;
	}
}
