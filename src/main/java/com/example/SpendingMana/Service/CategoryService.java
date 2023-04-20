package com.example.SpendingMana.Service;

import com.example.SpendingMana.dto.CategoryDTO;
import com.example.SpendingMana.dto.CategoryUpdateDTO;
import com.example.SpendingMana.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    public Page<Category> getAllCategory(Pageable pageable);
    public Category addCategory(CategoryDTO categoryDTO);
    public Category updateCategory(CategoryUpdateDTO categoryUpdateDTO, Long id);
    public boolean deleteCategory(Long id);
}
