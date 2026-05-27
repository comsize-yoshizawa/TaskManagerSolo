package com.example.todolist.dto;

import com.example.todolist.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	private String categoryName;
	
	public static CategoryDto from(Category category) {
		CategoryDto dto = new CategoryDto(); 
		dto.setCategoryId(category.getCategoryId());
		dto.setCategoryName(category.getCategoryName());
		
		return dto;
	}
}
