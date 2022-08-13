package com.boss.demo.repository;

import com.boss.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface MemberRepository extends JpaRepository<Member,Long> {
    //https://stackoverflow.com/questions/39784344/check-date-between-two-other-dates-spring-data-jpa
    //https://www.baeldung.com/spring-data-sorting
    //Page<Member> findByNameLike(String name, Pageable pageable);
    //Page<Member> findByNameLike(String name, Pageable pageable);
    Page<Member> findAllByNameLikeAndCreateTimeBetween(String name, Date startDate, Date endDate, Pageable pageable);

    Page<Member> findAllByNameLikeOrUsernameContains(String name,String username, Pageable pageable);

    Page<Member> findAllByNameLike(String name, Pageable pageable);



    Member findByUsername(String username);


}
