package com.order.shop.service;

import com.order.shop.dto.OrderLineItemDto;
import com.order.shop.dto.OrderRequest;
import com.order.shop.model.Order;
import com.order.shop.model.OrderLineItem;
import com.order.shop.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNo(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDtoToList()
                .stream()
                .map(this::mapToDto).toList();

        order.setOrderLineItems(orderLineItems);
        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice(orderLineItemDto.getPrice());
        orderLineItem.setKsuCode(orderLineItemDto.getKsuCode());
        orderLineItem.setQuantity(orderLineItemDto.getQuantity());

        return orderLineItem;
    }

}
