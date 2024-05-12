package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final CartJPARepository cartJPARepository;
    private final HttpSession session;


    // 장바구니 목록
    @GetMapping({"/cart-list"})
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<CartResponse.ListDTO> cartList = cartService.getCartList(sessionUser.getId());
        request.setAttribute("cartList", cartList);

        return "cart/cart-form";
    }


    // 장바구니 담기
    @PostMapping("/cart/save")
    public String saveCart(CartRequest.saveDTO requestDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        cartService.cartSave(requestDTO, sessionUser);

        System.out.println("cart 확인 : " + requestDTO);

        return "redirect:/cart-list";
    }

    @PostMapping("/cart/update")
    public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> reqDTO) {
        System.out.println("장바구니 값 받니? : " + reqDTO);


        return ResponseEntity.ok().body("선택한 상품의 구매를 진행 하시겠습니까?");
    }

//    @PostMapping("/cart/update")
//    public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> reqDTO) {
//        System.out.println("장바구니 값 받니? : " + reqDTO);
//        cartJPARepository.updateCheckedById();
//
//        return ResponseEntity.ok().body("선택한 상품의 구매를 진행 하시겠습니까?");
//    }

//    public @ResponseBody String update() {
//        return "구매를 진행하겠습니다.";
//    }
}
