package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.dto.CommentDto;
import com.example.todolist.entity.Comment;
import com.example.todolist.entity.Task;
import com.example.todolist.entity.User;
import com.example.todolist.repository.CommentRepository;
import com.example.todolist.repository.TaskRepository;
import com.example.todolist.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final TaskRepository taskRepository;
	
	public List<CommentDto> getCommentList(Integer taskId){
		List<Comment> commentList = commentRepository.findByTask_TaskId(taskId);
		
		return commentList.stream().map(comment ->{
			CommentDto dto = new CommentDto();
			dto.setCommentId(comment.getCommentId());
			dto.setUserId(comment.getUser().getUserId());
			dto.setUserName(comment.getUser().getUserName());
			dto.setComment(comment.getComment());
			dto.setTaskId(comment.getTask().getTaskId());
			dto.setUpdateDateTime(comment.getUpdateDateTime());
			
			return dto;
		})
		.toList();
	}
	
	public List<CommentDto> postComment(Integer taskId,String userId,String commentText) {
		User user = userRepository.findById(userId).orElseThrow();
		Task task = taskRepository.findById(taskId).orElseThrow();
		Comment comment = new Comment();
		
		comment.setUser(user);
		comment.setTask(task);
		comment.setComment(commentText);
		
		commentRepository.saveAndFlush(comment);
		List<Comment> commentList = commentRepository.findByTask_TaskId(taskId);
		
		return commentList.stream()
				.map(CommentDto::from).toList();
	}
	
	public void deleteComment(Integer commentId, String userId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		if(comment.getUser().getUserId().equals(userId)) {
			commentRepository.deleteById(commentId);
		}
	}
	
	public CommentDto getComment(Integer commentId) {
		Comment comment = commentRepository.findById(commentId).orElseThrow();
		CommentDto dto = new CommentDto();
		
		dto.setCommentId(comment.getCommentId());
		dto.setUserId(comment.getUser().getUserId());
		dto.setUserName(comment.getUser().getUserName());
		dto.setComment(comment.getComment());
		dto.setTaskId(comment.getTask().getTaskId());
		dto.setUpdateDateTime(comment.getUpdateDateTime());
		
		return dto;
	}
}
