package com.example.finalprojectdtomarket.category;


import com.example.finalprojectdtomarket.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryJPARepository categoryRepo;

    //DB에 어떤 카테고리가 있는지 카테고리 list
    public List<ProductResponse.CategoryDTO> categoryList(Integer categoryId) {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(category -> new ProductResponse.CategoryDTO(category, categoryId)).toList();
    }

    public List<Category> categoryList() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList;
    }
}
