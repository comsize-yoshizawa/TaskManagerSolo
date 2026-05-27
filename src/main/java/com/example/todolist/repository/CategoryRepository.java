package com.example.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todolist.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
