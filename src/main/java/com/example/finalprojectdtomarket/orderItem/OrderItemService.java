package com.example.finalprojectdtomarket.orderItem;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderItemService {
    private final OrderItemJPARepository orderItemRepo;

}
