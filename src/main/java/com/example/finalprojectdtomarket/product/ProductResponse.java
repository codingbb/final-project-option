package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.category.Category;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public class ProductResponse {


    @Data
    public static class CategoryDTO {
        private Integer id;
        private String categoryName;    //과일 채소 유제품
        private Boolean isSelected;

        public CategoryDTO(Category category, Integer selectedCategoryId) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.isSelected = selectedCategoryId.equals(category.getId()) ? true : false;
        }
    }

    //상품 상세보기
    @Data
    public static class DetailDTO {
        private Integer id;  //productId
        private String name;
        private Integer price;
        private Integer qty;
        private String categoryName;
        private String img;

        public DetailDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();

            this.categoryName = product.getCategory().getCategoryName();
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

            this.img = product.getImg();
        }
    }
}
