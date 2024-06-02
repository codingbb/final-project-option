package com.example.finalprojectdtomarket.cart;

import com.example.finalprojectdtomarket.option.OptionService;
import com.example.finalprojectdtomarket.product.Product;
import com.example.finalprojectdtomarket.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {
    private final CartService cartService;
    private final HttpSession session;

    @GetMapping("/cart-list")
    public String cartList(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        List<CartResponse.ListDTO> cartList = cartService.cartList(sessionUser.getId());
//        System.out.println("cartList = " + cartList);
        request.setAttribute("cartList", cartList);

        return "/cart/cart-form";
    }

}
