package com.boss.demo.repository;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Page<Category> findAllByNameLike(String name, Pageable pageable);


    //@Query("select u from User u where u.firstname = :#{#customer.firstname}")
    //List<User> findUsersByCustomersFirstname(@Param("customer") Customer customer);
    @Query("select c from Category c where c.type=:type")
    List<Category> findByType(@Param("type") int type, Sort sort);

}
