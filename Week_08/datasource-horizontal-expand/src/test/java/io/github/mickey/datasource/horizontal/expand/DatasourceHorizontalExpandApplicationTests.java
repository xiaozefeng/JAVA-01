package io.github.mickey.datasource.horizontal.expand;

import io.github.mickey.datasource.horizontal.expand.domain.Order;
import io.github.mickey.datasource.horizontal.expand.repository.OrderRepository;
import org.junit.Assert;
import org.junit.internal.requests.OrderingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class DatasourceHorizontalExpandApplicationTests {

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void testInsert() {
        Order order = new Order();
        order.setUserId(1L);
        order.setStatus("ok");
        orderRepository.save(order);
    }



    @Test
    public void testListAll() {
        List<Order> orders = orderRepository.findAll();
        Assertions.assertTrue(orders.size() == 1);
    }

    @Test
    public void testGetByOrderId() {
        Optional<Order> op = orderRepository.findById(577666228278505472L);
        Assertions.assertTrue(op.isPresent());
    }

    @Test
    public void testGetByUserId() {
        List<Order> orders = orderRepository.findAllByUserId(1L);
        Assertions.assertTrue(orders.size()==1);
        Assertions.assertTrue(orders.get(0).getUserId() ==1);
    }



}
