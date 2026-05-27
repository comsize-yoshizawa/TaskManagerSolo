package com.example.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{

	List<Comment> findByTask_TaskId(Integer taskId);

}
