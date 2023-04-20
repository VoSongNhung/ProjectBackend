package com.example.SpendingMana.controller;
import com.example.SpendingMana.Service.CategoryService;
import com.example.SpendingMana.dto.CategoryDTO;
import com.example.SpendingMana.dto.CategoryUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    public ResponseEntity<?> getAllCategory(Pageable pageable){
        return new ResponseEntity<>(categoryService.getAllCategory(pageable), HttpStatus.OK);
    }
    @PostMapping
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDTO categoryDTO){
        return new ResponseEntity<>(categoryService.addCategory(categoryDTO),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO, @PathVariable Long id){
        return new ResponseEntity<>(categoryService.updateCategory(categoryUpdateDTO,id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("delete success",HttpStatus.OK);
    }
}
