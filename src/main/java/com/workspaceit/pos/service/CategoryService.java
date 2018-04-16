package com.workspaceit.pos.service;

import com.workspaceit.pos.dao.CategoryDao;
import com.workspaceit.pos.entity.Category;
import com.workspaceit.pos.exception.EntityNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    private CategoryDao categoryDao;

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Transactional
    public Category getCategory(int id) throws EntityNotFound {
        Category category =  this.categoryDao.findById(id);
        if(category==null)throw new EntityNotFound("Category not found by id :"+id);

        return category;
    }
    @Transactional
    public Category getById(int id){
       return this.categoryDao.findById(id);
    }
    @Transactional
    public List<Category> getAll(){
        return this.categoryDao.findAll();
    }
}
