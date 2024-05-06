package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;
    private final HttpSession session;

    // 상품목록보기
    @GetMapping("/")
    public String list(HttpServletRequest request) {
    List<Product> productList = productService.findAll();
    request.setAttribute("productList", productList);
        return "product/list";
    }

    // 상품 상세보기
    @GetMapping("/product/{id}")
    public String detail() {
        return "product/detail";
    }

    // 상품 등록하기
    @GetMapping("/product/save-form")
    public String saveForm() {
        return "product/save-form";
    }

    @PostMapping("/product/save")
    public String save(ProductRequest.SaveDTO reqDTO) {
        productService.save(reqDTO);
        return "redirect:/";
    }

    // 상품 수정하기
    @GetMapping("/product/{id}/update-form")
    public String updateForm() {
        return "product/update-form";
    }

    @PostMapping("/product/{id}/update")
    public String update() {
        return "redirect:/";
    }

    // 상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete() {
        return "redirect:/";
    }


}
