package com.example.finalprojectdtomarket.product;


import com.example.finalprojectdtomarket._core.errors.exception.CategoryExistException;
import com.example.finalprojectdtomarket._core.errors.exception.ProductExistException;
import com.example.finalprojectdtomarket._core.errors.exception2.Exception404;
import com.example.finalprojectdtomarket.category.Category;
import com.example.finalprojectdtomarket.category.CategoryJPARepository;
import com.example.finalprojectdtomarket.image.Image;
import com.example.finalprojectdtomarket.image.ImageJPARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productRepo;
    private final CategoryJPARepository categoryRepo;
    private final ImageJPARepository imageRepo;


    //상품 삭제하기
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ProductExistException());

        //근데 admin 1명 밖에 없어서 삭제 권한 여부 확인할 필요 없지않나? -> 생략함

        //orderItem이랑 cart에 있다고 제약조건 걸리는건 좀 이상한듯
//        cartRepo.deleteByProductId(productId);
        //TODO : 이거 다시 터짐
//        orderItemRepo.deleteByProductId(productId);
        productRepo.deleteByProductId(productId);

    }

    //상품 상세보기
    public ProductResponse.DetailDTO getDetail(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductExistException());

        Product newProduct = productRepo.findByIdWithCategory(product.getId());
//        System.out.println("newProduct = " + newProduct);
        List<Image> images = imageRepo.findAll();

//        System.out.println("dto확인 " + product);
        return new ProductResponse.DetailDTO(newProduct, images);

    }

    // 상품 등록하기
    @Transactional
    public void save(ProductRequest.SaveDTO reqDTO) {
        Category category = categoryRepo.findById(reqDTO.getCategoryId())
                .orElseThrow(() -> new CategoryExistException());

        Product product = productRepo.save(reqDTO.toEntity(category));

        // 애가 ProductRequest.SaveDTO 타입이 아니고 MultipartFile 타입임 이미지니까!
        for (MultipartFile file : reqDTO.getImg()) {
            //각 MultipartFile을 받아서 이미지 엔티티를 생성
            imageRepo.save(reqDTO.toImgEntity(file, product));
        }
    }

    // 메인 창
    @Transactional
    public List<ProductResponse.IndexDTO> findProductAndImgAll() {
//        Sort sort = Sort.by(Sort.Direction.DESC, "id");
//        List<Product> productList = productRepo.findAll(sort);
        List<Product> productList = productRepo.findAllWithImages();
//        System.out.println("productList = " + productList);
//
//        // toString 없어... img 조회 안되어서 직접 함
//        for (Product product : productList) {
//            System.out.println("이미지 확인 : " + product.getImages());
//        }

        List<ProductResponse.IndexDTO> indexDTOList = productList.stream().map(product ->
                new ProductResponse.IndexDTO(product)).toList();

        return indexDTOList;

    }


    @Transactional
    public List<ProductResponse.IndexDTO> findProductAndImgAll(String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Product> productList = productRepo.findAll(sort);
//        System.out.println("productList = " + productList);

        List<Image> imageList = imageRepo.findAll();
//        System.out.println("imageList = " + imageList);

        return null;
    }


    //상품 목록보기
    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<Product> productList = productRepo.findAll(sort);
        return productList;
    }

    //키워드 용
    public List<Product> findAll(String keyword) {
        List<Product> products = productRepo.findKeyword(keyword);
        return products;
    }

    //상품 업데이트폼 줘
    public ProductResponse.UpdateDTO findByIdUpdate(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductExistException());

        Product newProduct = productRepo.findByIdWithCategory(product.getId());

        return new ProductResponse.UpdateDTO(newProduct);

    }

    //업데이트
    @Transactional
    public void updateProduct(Integer id, ProductRequest.UpdateDTO requestDTO, String imgFileName) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductExistException());

        //카테고리를 한번 조회하고 들어와야함
        Category category = categoryRepo.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new CategoryExistException());

        // TODO: set으로 말고 의미있는 메소드 만들어서 하는건?
        product.setName(requestDTO.getName());

        product.setCategory(category);

//        product.setImg(imgFileName);

    }

}
