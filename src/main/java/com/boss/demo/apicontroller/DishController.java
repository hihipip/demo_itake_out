package com.boss.demo.apicontroller;


import com.boss.demo.entity.Member;
import com.boss.demo.service.DishService;
import com.boss.demo.service.MemberService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/")
    public R list(SearchVo searchVo){
        return R.ok().data("data",dishService.getAll(searchVo));
    }


}
