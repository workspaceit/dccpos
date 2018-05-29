package com.workspaceit.dccpos.restendpoint;

import com.workspaceit.dccpos.constant.EndpointRequestUriPrefix;
import com.workspaceit.dccpos.entity.Category;
import com.workspaceit.dccpos.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndpointRequestUriPrefix.endPointPublic+"/category")
@CrossOrigin
public class CategoryEndPoint {
    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Category> categories = this.categoryService.getAll();
        return ResponseEntity.ok(categories);
    }
    @RequestMapping("/get-by-id/{id}")
    public ResponseEntity<?> getAll(@PathVariable int id){
        Category category = this.categoryService.getById(id);
        return ResponseEntity.ok(category);
    }
}