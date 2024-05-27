package com.example.finalprojectdtomarket.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class ProductResponse {

    //상품 상세보기
    @Data
    public static class DetailDTO {
        private Integer id;  //productId
        private String name;
        private Integer price;
        private Integer qty;
        private String img;

        public DetailDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }

    //상품 등록
    @Data
    public static class SaveDTO{
        private Integer id;
        private String name;
        private Integer price;
        private Integer qty;
        private String img;

        public SaveDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }

    // 상품 정보 수정
    @Data
    public static class UpdateDTO{
        private int id;
        private String name;
        private Integer price;
        private Integer qty;
        private Integer categoryId;
        private String categoryName;
        private String img;

        public UpdateDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getCategoryName();
            this.img = product.getImg();
        }
    }

    //상품 목록보기
    @Data
    public static class ListDTO{
        private Integer id;
        private String name;
        private Integer price;
        private Integer qty;
        private String img;

        public ListDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.qty = product.getQty();
            this.img = product.getImg();
        }
    }
}
