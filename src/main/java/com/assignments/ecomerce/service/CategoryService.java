package com.assignments.ecomerce.service;

import com.assignments.ecomerce.model.Category;
import com.assignments.ecomerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private int pageSize = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategory() {
        return (List<Category>) categoryRepository.findByStatusActivated();
    }

    public Category save(Category category) {
        category.setStatus(1);
        return categoryRepository.save(category);
    }

    public Page<Category> pageCategory(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, this.getPageSize());
        return categoryRepository.pageCategory(pageable);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }
    public Category     update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepository.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    public Category updateStatus(Integer id) {

        Category categoryUpdate = null;
        try {
            categoryUpdate = categoryRepository.findById(id).get();
            categoryUpdate.setStatus(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categoryRepository.save(categoryUpdate);
    }

    public void enableById(Integer id) {
        Category category = categoryRepository.getById(id);
        category.setStatus(0);
        categoryRepository.save(category);
    }

    public Page<Category> searchCategory(int pageNo, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, this.getPageSize());
        List<Category> categorys = transfer(categoryRepository.searchCategory(keyword));
        Page<Category> categoryPages = toPage(categorys, pageable);
        return categoryPages;
    }

    private Page toPage(List<Category> list, Pageable pageable) {
        if (pageable.getOffset() >= list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List subList = list.subList(startIndex, endIndex);
        return new PageImpl(subList, pageable, list.size());
    }

    public List<Category> transfer(List<Category> categories) {
        List<Category> categoryList = new ArrayList<>();
        for (Category category : categories) {
            Category newCategory = new Category();
            newCategory.setId(category.getId());
            newCategory.setName(category.getName());
            newCategory.setSupplier(category.getSupplier());
            categoryList.add(newCategory);
        }
        return categoryList;
    }
}
