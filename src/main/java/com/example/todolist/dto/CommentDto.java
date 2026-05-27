package com.example.todolist.dto;

import java.time.LocalDateTime;

import com.example.todolist.entity.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
	private Integer commentId;
	
	private String userId;
	private String userName;
	
	private String comment;
	
	private Integer taskId;
	
	private LocalDateTime updateDateTime;
	
	public static CommentDto from(Comment comment) {
		CommentDto dto = new CommentDto();
		dto.setCommentId(comment.getCommentId());
		dto.setComment(comment.getComment());
		dto.setUserId(comment.getUser().getUserId());
		dto.setUserName(comment.getUser().getUserName());
		dto.setTaskId(comment.getTask().getTaskId());
		dto.setUpdateDateTime(comment.getUpdateDateTime());
		
		return dto;
	}
}
