package com.example.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.todolist.dto.CategoryDto;
import com.example.todolist.entity.Category;
import com.example.todolist.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository repository;

	public List<CategoryDto> getAllCategories() {
		List<Category> categoryList = repository.findAll();
		return categoryList.stream()
				.map(CategoryDto::from).toList();
	}
	
	public Category findById(Integer id) {
		return repository.getReferenceById(id);
	}
}
