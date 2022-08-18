package com.boss.demo.controller.front;

import com.boss.demo.entity.*;
import com.boss.demo.handler.MyException;
import com.boss.demo.repository.ConsumerRepository;
import com.boss.demo.repository.ShopCartRepository;
import com.boss.demo.security.CustomUser;
import com.boss.demo.security.roles.IsUser;
import com.boss.demo.service.CategoryService;
import com.boss.demo.service.DishService;
import com.boss.demo.service.SetmealService;
import com.boss.demo.tools.R;
import com.boss.demo.vo.SearchVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 購物車相關
 */
@Controller
@RequestMapping("front")
public class FrontShopCartController {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private ShopCartRepository shopCartRepository;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @IsUser
    @PostMapping("/addToCart")
    @ResponseBody
    public R addToCart(@RequestBody ShopCart shopCart){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        long userId = customUser.getId();

        if( shopCart.getDishId()!=0 ){
            //直接加一筆
            Dish dish = dishService.getDishWithFlavor(shopCart.getDishId());
            shopCart.setName(dish.getName());
            shopCart.setImage(dish.getImage());
            shopCart.setAmount(dish.getPrice());
            shopCart.setUserId(customUser.getId());
            this.shopCartRepository.save(shopCart);

        } else if( shopCart.getSetmealId()!=0 ) {
            //若購物車有套餐了，則更新套餐數量
            ShopCart dbShopCart = shopCartRepository.getByUserIdAndSetmealId(userId,shopCart.getSetmealId());
            if( dbShopCart!=null ){
                dbShopCart.setNumber(dbShopCart.getNumber()+shopCart.getNumber());
                this.shopCartRepository.save(dbShopCart);
            } else {
                Setmeal setmeal = setmealService.getSetmealWithDish(shopCart.getSetmealId());
                shopCart.setName(setmeal.getName());
                shopCart.setImage(setmeal.getImage());
                shopCart.setAmount(setmeal.getPrice());
                shopCart.setUserId(userId);
                this.shopCartRepository.save(shopCart);
            }
        }
        return R.ok();
    }

    @IsUser
    @GetMapping("/viewcart")
    public String list(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        long userId = customUser.getId();
        List<ShopCart> shopCarts = this.shopCartRepository.findAllByUserId(userId);
        int total = shopCarts.stream().mapToInt(item->item.getNumber()*item.getAmount()).sum();

        model.addAttribute("shopCarts", shopCarts);
        model.addAttribute("total", total);

        if( model.getAttribute("orderMain") == null ){
            //抓取個人資料
            ObjectMapper objectMapper = new ObjectMapper();
            String json = (String)redisTemplate.opsForValue().get("consumer_"+userId);
            Consumer consumer = null;
            if( json!=null ){
                try{
                    consumer = objectMapper.readValue(json, Consumer.class);
                }catch(Exception e){
                    throw new MyException(100,"轉換錯誤");
                }
            }
            //訂單填入
            OrderMain orderMain = new OrderMain();
            orderMain.setAddress("新北市新莊區中正路500號30樓");
            orderMain.setPhone(consumer!=null?consumer.getPhone():"");
            orderMain.setName(consumer!=null?consumer.getName():"");
            model.addAttribute("orderMain", orderMain);
        }

        return "front/view_cart";
    }

    @IsUser
    @GetMapping("/removeCart/{id}")
    public String removeCart(@PathVariable(value = "id") long id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser customUser = (CustomUser)authentication.getPrincipal();
        long userId = customUser.getId();
        try{
            this.shopCartRepository.deleteById(id);
        }catch(Exception e){
            e.printStackTrace();
        }
        return list(model);
    }
}
