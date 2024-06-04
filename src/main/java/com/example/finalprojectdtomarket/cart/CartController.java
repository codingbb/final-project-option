package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.OptionService;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final HttpSession session;

    @PostMapping("/cart/update")
    public ResponseEntity<?> update(@RequestBody List<CartRequest.UpdateDTO> requestDTOs) {
//        System.out.println("장바구니 값 받니? : " + requestDTOs);
        cartService.updateCart(requestDTOs);

        System.out.println("4. 카트 수량이랑 status 업데이트 끝남!! cart/update 끝");

        return ResponseEntity.ok().body("선택한 상품의 구매를 진행 하시겠습니까?");
    }


    // 장바구니 담기
    @PostMapping("/cart/save")
    public ResponseEntity<?> saveCart(@RequestBody List<CartRequest.saveDTO> requestDTO) {
        System.out.println("cart 확인 : " + requestDTO);

        User sessionUser = (User) session.getAttribute("sessionUser");
        cartService.cartSave(requestDTO, sessionUser);

        return ResponseEntity.ok().body("장바구니에 담겼습니다");
    }


    // 장바구니 리스트
    @GetMapping("/cart-list")
    public String cartList(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        String status = cartService.findCartStatus();
//        System.out.println("status 확인" + status);

        if (status.equals("CART_ING")) {
            request.setAttribute("confirmOrder", true);
//            return "redirect:/order-save-form";

        } else {
            List<CartResponse.ListDTO> cartList = cartService.cartList(sessionUser.getId());
//            System.out.println("cartList = " + cartList);
            request.setAttribute("confirmOrder", false);
            request.setAttribute("cartList", cartList);

        }
        return "/cart/cart-form";
    }


    // confirm 취소 누르면 이쪽으로 갈거임..
    @PostMapping("/cart-list-update")
    public ResponseEntity<?> listUpdate(@RequestBody CartRequest.ListUpdateDTO requestDTO) {
        System.out.println("장바구니 값 받니? : " + requestDTO);
        cartService.cartListUpdate(requestDTO);
        return ResponseEntity.ok().body("CART_ING");
    }


    @GetMapping("/cart-form")
    public String cartForm() {
        return "cart-form";
    }


}
