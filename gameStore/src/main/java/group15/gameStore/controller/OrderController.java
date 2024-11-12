package group15.gameStore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group15.gameStore.dto.OrderRequestDto;
import group15.gameStore.dto.OrderResponseDto;
import group15.gameStore.exception.GameStoreException;
import group15.gameStore.model.Order;
import group15.gameStore.service.OrderService;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderToCreate) {
        try {
            Order createdOrder = orderService.createOrder(orderToCreate.getOrderNumber(), orderToCreate.getOrderStatus(), orderToCreate.getPrice(), orderToCreate.getCustomer());
            return new ResponseEntity<>(new OrderResponseDto(createdOrder), HttpStatus.CREATED);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @PutMapping("/order/{orderID}/update")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable("orderID") int orderID, OrderRequestDto orderToUpdate) {
        try {
            Order updatedOrder = orderService.updateOrder(orderID, orderToUpdate.getOrder(), orderToUpdate.getEmployee());
            return new ResponseEntity<>(new OrderResponseDto(updatedOrder), HttpStatus.OK); 
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        } 
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<OrderResponseDto> getOrderByID(@PathVariable("orderID") int orderID) {
        try {
            Order order = orderService.getOrderByID(orderID);
            OrderResponseDto orderResponse = new OrderResponseDto(order);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<OrderResponseDto> getOrderByNumber(@PathVariable("orderNumber") String orderNumber) {
        try {
            Order order = orderService.getOrderByOrderNumber(orderNumber);
            OrderResponseDto orderResponse = new OrderResponseDto(order);
            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @GetMapping("/order")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        try {
            List<Order> orderList = orderService.getAllOrders();
            if (orderList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<OrderResponseDto> orderResponseDtos = orderList.stream()
                    .map(OrderResponseDto::new)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(orderResponseDtos, HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }

    @DeleteMapping("/order/{orderID}/delete")
    public ResponseEntity<Void> deleteOrderByID(@PathVariable("orderID") int orderID, @RequestBody OrderRequestDto orderToDelete) {
        try {
            Order deletedOrder = orderService.getOrderByID(orderID);
            orderService.deleteOrder(deletedOrder, orderToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }
    
    @DeleteMapping("/order/{orderNumber}/delete")
    public ResponseEntity<Void> deleteOrderByOrderNumber(@PathVariable("orderNumber") String orderNumber, @RequestBody OrderRequestDto orderToDelete) {
        try {
            Order deletedOrder = orderService.getOrderByOrderNumber(orderNumber);
            orderService.deleteOrder(deletedOrder, orderToDelete.getEmployee());
            return new ResponseEntity<>(HttpStatus.OK);
        }

        catch (GameStoreException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  
        }
    }
    
}