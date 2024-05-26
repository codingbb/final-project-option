package com.example.finalprojectdtomarket.product;


import com.example.finalprojectdtomarket._core.errors.exception.Exception404;
import com.example.finalprojectdtomarket.cart.CartJPARepository;
import com.example.finalprojectdtomarket.orderItem.OrderItemJPARepository;
import com.example.finalprojectdtomarket._core.common.ImgSaveUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productRepo;
    private final OrderItemJPARepository orderItemRepo;
    private final CartJPARepository cartRepo;

    //상품 삭제하기
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

        //근데 admin 1명 밖에 없어서 삭제 권한 여부 확인할 필요 없지않나? -> 생략함

        //orderItem이랑 cart에 있다고 제약조건 걸리는건 좀 이상한듯
        cartRepo.deleteByProductId(productId);
        orderItemRepo.deleteByProductId(productId);
        productRepo.deleteByProductId(productId);

    }

    //상품 상세보기
    public ProductResponse.DetailDTO getDetail(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

//        System.out.println("dto확인 " + product);
        return new ProductResponse.DetailDTO(product);

    }

    // 상품 등록하기
    @Transactional
    public void save(ProductRequest.SaveDTO reqDTO) {
        productRepo.save(reqDTO.toEntity());
    }


    //상품 목록보기
    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productRepo.findAll(sort); // return에 sort 객체 안 넣어주면 DESC 안 됨
    }

    //키워드 용
    public List<Product> findAll(String keyword) {
        List<Product> products = productRepo.findKeyword(keyword);
        return products;
    }

    //상품 업데이트폼 줘
    public ProductResponse.UpdateDTO findByIdUpdate(Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

        return new ProductResponse.UpdateDTO(product);

    }

    //업데이트
    @Transactional
    public void updateProduct(Integer id, ProductRequest.UpdateDTO requestDTO, String imgFileName) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

        // TODO: set으로 말고 의미있는 메소드 만들어서 하는건?
        product.setName(requestDTO.getName());
        product.setQty(requestDTO.getQty());
        product.setPrice(requestDTO.getPrice());
        product.setImg(imgFileName);

    }

}
