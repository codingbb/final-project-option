package com.example.finalprojectdtomarket.order;

import com.example.finalprojectdtomarket.cart.CartResponse;
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

    // 주문취소
    @PostMapping("/order-cancel")
    public @ResponseBody String cancel(@RequestBody List<OrderRequest.CancelDTO> requestDTO) {
//        System.out.println("받는지 확인 " + requestDTO);
        orderService.orderCancel(requestDTO);

        return "redirect:/order-list";

    }


    // 주문 목록
    @GetMapping("/order-list")
    public String list(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findUserId(sessionUser.getId());

        List<OrderResponse.ListDTO> orderItemList = orderService.orderList(user.getId());
//        System.out.println("ffdd = " + orderItemList);
        request.setAttribute("orderItemList", orderItemList);
        return "/order/list";
    }


    @PostMapping("/order-save")
    public String orderSave(OrderRequest.SaveDTO requestDTO) {

        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findUserId(sessionUser.getId());

        //Enum 쓰기!!
        requestDTO.setStatus(OrderStatus.ORDER_COMPLETE);
        //구매하기 로직
        orderService.saveOrder(requestDTO, user);

        return "redirect:/order-list";

    }

    @GetMapping("/order-save-form")
    public String saveForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.findUserId(sessionUser.getId());

        List<CartResponse.saveFormList> saveFormLists = orderService.orderCartList(user.getId());

        Integer totalSum = saveFormLists.stream().mapToInt(value -> value.getSum()).sum();
//        System.out.println("totalSum = " + totalSum);
//        System.out.println("saveFormLists = " + saveFormLists);

        //TODO: 한번에 어떻게 담나요
        request.setAttribute("saveFormLists", saveFormLists);
        request.setAttribute("user", user);
        request.setAttribute("totalSum", totalSum);

        return "/order/save-form";
    }

}
