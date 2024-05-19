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

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductJPARepository productJPARepository;
    private final OrderItemJPARepository orderItemJPARepository;
    private final CartJPARepository cartJPARepository;

    //상품 삭제하기
    @Transactional
    public void deleteProduct(Integer productId) {
        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

        //근데 admin 1명 밖에 없어서 삭제 권한 여부 확인할 필요 없지않나? -> 생략함

        //orderItem이랑 cart에 있다고 제약조건 걸리는건 좀 이상한듯
        cartJPARepository.deleteByProductId(productId);
        orderItemJPARepository.deleteByProductId(productId);
        productJPARepository.deleteByProductId(productId);

    }

    //상품 상세보기
    public ProductResponse.DetailDTO getDetail(Integer id) {
        Product product = productJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("상품이 존재하지 않습니다."));

//        System.out.println("dto확인 " + product);
        return new ProductResponse.DetailDTO(product);

    }

    // 상품 등록하기
    @Transactional
    public void save(ProductRequest.SaveDTO reqDTO) {
        productJPARepository.save(reqDTO.toEntity());
    }


    //상품 목록보기
    public List<Product> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return productJPARepository.findAll(sort); // return에 sort 객체 안 넣어주면 DESC 안 됨
    }

    //키워드 용
    public List<Product> findAll(String keyword) {
        List<Product> products = productJPARepository.findKeyword(keyword);
        return products;
    }

    // 상품 정보 수정
    @Transactional
    public ProductResponse.UpdateDTO updateProduct(Integer productId, ProductRequest.UpdateDTO reqDTO) {
        Product product = productJPARepository.findById(productId)
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다."));

        String imgName = ImgSaveUtil.save(reqDTO.getImg());
        product.setImg(imgName);
        product.setPrice(reqDTO.getPrice());
        product.setQty(reqDTO.getQty());
        productJPARepository.save(product);
        return new ProductResponse.UpdateDTO(product);
    }
}
