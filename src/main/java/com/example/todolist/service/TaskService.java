package com.example.todolist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.todolist.dto.TaskDetailDto;
import com.example.todolist.dto.TaskListDto;
import com.example.todolist.entity.Category;
import com.example.todolist.entity.Status;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.form.TaskData;
import com.example.todolist.repository.CategoryRepository;
import com.example.todolist.repository.StatusRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {
	private final TaskRepository taskRepository;
	private final CategoryRepository categoryRepository;
	private final StatusRepository statusRepository;
	private final UserRepository userRepository;
	
	public Page<TaskListDto> getAllTasks(int page){
		Page<Task> taskList = taskRepository.findAll(PageRequest.of(page, 10));
		
		return taskList.map(task ->{
			TaskListDto dto = new TaskListDto();
			
			dto.setTaskId(task.getTaskId());
			dto.setTaskName(task.getTaskName());
			dto.setCategoryName(task.getCategory().getCategoryName());
			dto.setUserName(task.getUser().getUserName());
			dto.setLimitDate(task.getLimitDate());
			dto.setStatusName(task.getStatus().getStatusName());
			dto.setMemo(task.getMemo());
			
			return dto;
		});
	}
	
	public TaskDetailDto getTaskDetail(int taskId) {
		Task task = taskRepository.findById(taskId).orElseThrow();
		TaskDetailDto dto = new TaskDetailDto();
		
		dto.setTaskId(task.getTaskId());
		dto.setTaskName(task.getTaskName());
		dto.setCategoryName(task.getCategory().getCategoryName());
		dto.setUserName(task.getUser().getUserName());
		dto.setUserId(task.getUser().getUserId());
		dto.setLimitDate(task.getLimitDate());
		dto.setStatusName(task.getStatus().getStatusName());
		dto.setMemo(task.getMemo());
		
		return dto;
	}
	
	public void deleteTask(int taskId) {
		taskRepository.deleteById(taskId);
	}
	
	public void saveTask(TaskData taskData) {
		Category category = categoryRepository.findById(taskData.getCategoryId()).orElseThrow();
		User user = userRepository.findById(taskData.getUserId()).orElseThrow();
		Status status = statusRepository.findById(taskData.getStatusCode()).orElseThrow();
		Task task = taskData.toEntity(category, user, status);
		taskRepository.saveAndFlush(task);
	}
	
	public TaskData toForm(int taskId) {
		TaskData taskForm = new TaskData();
		Task task = taskRepository.findById(taskId).orElseThrow();
		taskForm.setTaskId(task.getTaskId());
		taskForm.setTaskName(task.getTaskName());
		taskForm.setCategoryId(task.getCategory().getCategoryId());
		taskForm.setLimitDate(task.getLimitDate());
		taskForm.setUserId(task.getUser().getUserId());
		taskForm.setStatusCode(task.getStatus().getStatusCode());
		taskForm.setMemo(task.getMemo());
		return taskForm;
		
	}
}
