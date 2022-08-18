package com.boss.demo.repository;

import com.boss.demo.entity.OrderDetail;
import com.boss.demo.entity.OrderMain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {

    List<OrderDetail> findAllByOrderId(long orderId);

}
