package io.github.mickey.datasource.horizontal.expand.repository;

import io.github.mickey.datasource.horizontal.expand.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

}
