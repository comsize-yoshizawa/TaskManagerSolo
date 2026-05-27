package com.example.todolist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.entity.Status;

public interface StatusRepository extends JpaRepository<Status, String>{

	List<Status> findAllByOrderByStatusCodeAsc();
}
