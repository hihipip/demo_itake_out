package com.boss.demo.repository;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findAllByNameLike(String name, Pageable pageable);
}
