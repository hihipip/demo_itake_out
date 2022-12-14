package com.boss.demo.repository;

import com.boss.demo.entity.Consumer;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsumerRepository extends JpaRepository<Consumer,Long> {

    public Consumer findByPhone(String phone);
}
