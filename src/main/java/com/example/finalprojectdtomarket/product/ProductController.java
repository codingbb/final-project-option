package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;
    private final HttpSession session;

    @GetMapping("/product-list")
    public String productList(HttpServletRequest request) {
        List<Product> productList = productService.findAll();
        request.setAttribute("productList", productList);

        return "/product/product-list";
    }

    // 상품목록보기
    @GetMapping("/")
    public String list(HttpServletRequest request, @RequestParam (value = "keyword", defaultValue = "") String keyword) {
    List<Product> productList = productService.findAll();
    request.setAttribute("productList", productList);

        if (keyword.isBlank()) {
            productList = productService.findAll();
            request.setAttribute("keyword", "");
            request.setAttribute("keywordList", productList);

        } else {
            productList = productService.findAll(keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("keywordList", productList);
//            System.out.println("넘어오나");
            return "/product/search";
        }

        return "product/list";
    }

    // 상품 상세보기
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        ProductResponse.DetailDTO product = productService.getDetail(id);
        request.setAttribute("product", product);
        return "product/detail";
    }

    // 상품 등록하기
    @GetMapping("/product/save-form")
    public String saveForm() {
        return "product/save-form";
    }

    @PostMapping("/product/save")
    public String save(ProductRequest.SaveDTO reqDTO) {
        System.out.println("dd" + reqDTO);
        productService.save(reqDTO);
        return "redirect:/";
    }

    // 상품 수정하기
    @GetMapping("/product/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        ProductResponse.DetailDTO product = productService.getDetail(id);
        request.setAttribute("product", product);
        return "product/update-form";
    }

    @PostMapping("/product/{id}/update")
    public String update(@PathVariable Integer id, ProductRequest.UpdateDTO reqDTO) {
        productService.updateProduct(id, reqDTO);
        return "redirect:/";
    }

    // 상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable Integer id) {
        productService.deleteProduct(id);

        return "redirect:/";
    }


}
