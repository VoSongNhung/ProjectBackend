package com.example.SpendingMana.controller;
import com.example.SpendingMana.Service.CategoryService;
import com.example.SpendingMana.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public ResponseEntity<?> getAllCategory(Pageable pageable){
        return new ResponseEntity<>(categoryService.getAllCategory(pageable), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.addCategory(categoryDTO),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
