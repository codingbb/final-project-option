package com.example.finalprojectdtomarket.option;


import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.category.Category;
import com.example.finalprojectdtomarket.category.CategoryJPARepository;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.product.ProductJPARepository;
import com.example.finalprojectdtomarket.product.ProductRequest;
import com.example.finalprojectdtomarket.product.ProductResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class OptionService {
    private final OptionJPARepository optionRepo;

    public List<Option> findOption(Integer productId) {
        System.out.println("몇번? " + productId);
        // 옵션이 해당하는 상품에 대한 옵션만 나와야하니까 productId 받아야할듯
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<Option> optionList = optionRepo.findOption(sort, productId);
        return optionList;
    }

}
