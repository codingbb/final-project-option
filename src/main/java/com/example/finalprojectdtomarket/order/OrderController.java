package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.user.User;
import com.example.finalprojectdtomarket.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final HttpSession session;

    // 관리자 확인 용
    @GetMapping("/admin-list")
    public String adminList(HttpServletRequest request) {

        List<OrderResponse.ListDTO> orderItemList = orderService.orderList();
//        System.out.println("ffdd = " + orderItemList);
        request.setAttribute("orderItemList", orderItemList);
        return "/admin/list";
    }

    // 주문 목록
    @GetMapping({"/order-list"})
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findUserId(sessionUser.getId());

        List<OrderResponse.ListDTO> orderItemList = orderService.orderList(user.getId());
//        System.out.println("ffdd = " + orderItemList);
        request.setAttribute("orderItemList", orderItemList);
        return "/order/list";
    }

}
