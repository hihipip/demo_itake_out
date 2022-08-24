package com.boss.demo.repository;

import com.boss.demo.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface MemberRepository extends JpaRepository<Member,Long> {
    //https://stackoverflow.com/questions/39784344/check-date-between-two-other-dates-spring-data-jpa
    //https://www.baeldung.com/spring-data-sorting
    //Page<Member> findByNameLike(String name, Pageable pageable);
    //Page<Member> findByNameLike(String name, Pageable pageable);


    //使用Spring提供的方法去搜尋
    Page<Member> findAllByNameLikeAndCreateTimeBetween(String name, Date startDate, Date endDate, Pageable pageable);
    //使用Query的方法去搜尋
    //可用 @Param("name")  來代替 ?1 ?2 ?3
    @Query(value = "select m.*,d.* from tb_member m left join tb_dept d on m.dept_id=d.id where m.create_time>?2 and m.create_time< ?3 and m.name like ?1 ",
           countQuery = "SELECT count(*) from tb_member m where m.create_time>?2 and m.create_time< ?3 and m.name like ?1 ",
           nativeQuery = true)
    Page<Member> findAllByQuery(String name, Date startDate, Date endDate, Pageable pageable);



    Page<Member> findAllByNameLikeOrUsernameContains(String name,String username, Pageable pageable);

    Page<Member> findAllByNameLike(String name, Pageable pageable);



    Member findByUsername(String username);


}
