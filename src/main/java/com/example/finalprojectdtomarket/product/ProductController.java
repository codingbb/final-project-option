package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.category.Category;
import com.example.finalprojectdtomarket.category.CategoryService;
import com.example.finalprojectdtomarket.option.Option;
import com.example.finalprojectdtomarket.option.OptionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final HttpSession session;
    private final OptionService optionService;

    @GetMapping("/product-list")
    public String productList(HttpServletRequest request) {
        List<Product> productList = productService.findAll();
        request.setAttribute("productList", productList);

        return "/product/product-list";
    }

    // 상품목록보기
    @GetMapping("/")
    public String list(HttpServletRequest request, @RequestParam(value = "keyword", defaultValue = "") String keyword) {
//    List<Product> productList = productService.findAll();
        List<ProductResponse.IndexDTO> productList = productService.findProductAndImgAll();
        request.setAttribute("productList", productList);

        if (keyword.isBlank()) {
            productList = productService.findProductAndImgAll();
            request.setAttribute("keyword", "");
            request.setAttribute("keywordList", productList);

        } else {
            productList = productService.findProductAndImgAll(keyword);
            request.setAttribute("keyword", keyword);
            request.setAttribute("keywordList", productList);
//            System.out.println("넘어오나");
            return "/product/search";
        }

        return "index";
    }

    // 상품 상세보기
    @GetMapping("/product/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        ProductResponse.DetailDTO product = productService.getDetail(id);
        request.setAttribute("product", product);

        List<Option> optionList = optionService.findOption(id);
        request.setAttribute("optionList", optionList);

        return "product/detail";
    }

    // 상품 등록하기
    @GetMapping("/product/save-form")
    public String saveForm(HttpServletRequest request) {
        List<Category> categoryList = categoryService.categoryList();
        request.setAttribute("categoryList", categoryList);
        return "product/save-form";
    }


    // 상품 save
    @PostMapping("/product/save")
    public String save(@ModelAttribute ProductRequest.SaveDTO requestDTO) {
//        System.out.println("save 확인" + requestDTO);
        productService.save(requestDTO);

        return "redirect:/";
    }

    //상품 수정
    @GetMapping("/product/{id}/update-form")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        ProductResponse.UpdateDTO product = productService.findByIdUpdate(id);
        List<ProductResponse.CategoryDTO> categoryList = categoryService.categoryList(product.getCategoryId());


//      TODO: 하나로 넣었으면 ...
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("product", product);
        return "product/update-form";
    }

    @PostMapping("/product/{id}/update")
    public String update(@PathVariable Integer id, ProductRequest.UpdateDTO requestDTO) {
        String imgFileName;

        // 이미지 파일이 존재할 경우, 새 파일명 생성 및 파일 저장
        if (!requestDTO.getImg().isEmpty()) {
            MultipartFile imgFile = requestDTO.getImg();
            imgFileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();

            Path imgPath = Paths.get("./upload/" + imgFileName);

            try {
                Files.write(imgPath, imgFile.getBytes());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            // 이미지 파일이 없을 경우, 기존 파일명 유지
            ProductResponse.UpdateDTO existImg = productService.findByIdUpdate(id);
            imgFileName = existImg.getImg(); // 기존의 imgFileName을 가져와서 사용

        }

        productService.updateProduct(id, requestDTO, imgFileName);
        return "redirect:/product/" + id;
    }


    // 상품 삭제하기
    @PostMapping("/product/{id}/delete")
    public String delete(@PathVariable Integer id) {

//        System.out.println("아이디 " + id);
        productService.deleteProduct(id);

        return "redirect:/";
    }


}
