package com.example.finalprojectdtomarket.product;

import com.example.finalprojectdtomarket.category.Category;
import com.example.finalprojectdtomarket.image.Image;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ProductResponse {

    @Data
    public static class IndexDTO {
        private Integer id;     // productId
        private String name;
        private Integer price;  // 뿌릴용.. price ...

        // img는 경로만 있으면 됨
        private String img;
//        private List<String> imgs = new ArrayList<>();

        public IndexDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.img = product.getImages().get(0).getFilePath();
        }
    }


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

        private List<ImageDTO> images = new ArrayList<>();

        public DetailDTO(Product product, List<Image> images) {
            this.id = product.getId();
            this.name = product.getName();
            this.price = product.getPrice();
            this.categoryName = product.getCategory().getCategoryName();
            this.images = images.stream().filter(image ->
                    image.getProduct().getId().equals(product.getId()))
                    .map(image -> new ImageDTO(image)).toList();
        }

        @Data
        public class ImageDTO {
            private String img;

            public ImageDTO(Image img) {
                this.img = img.getFilePath();
            }
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
            //TODO: 이미지
//            this.img = product.getImg();
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

        private List<ImageDTO> images = new ArrayList<>();

        public UpdateDTO(Product product, List<Image> images) {
            this.id = product.getId();
            this.name = product.getName();

            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getCategoryName();
            this.images = images.stream().filter(image ->
                            image.getProduct().getId().equals(product.getId()))
                    .map(image -> new UpdateDTO.ImageDTO(image)).toList();
        }

        @Data
        public class ImageDTO {
            private String img;

            public ImageDTO(Image img) {
                this.img = img.getFilePath();
            }
        }

    }



}
