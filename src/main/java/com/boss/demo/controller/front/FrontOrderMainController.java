package com.boss.demo.controller.front;

import com.boss.demo.controller.ConsumerInfo;
import com.boss.demo.entity.Consumer;
import com.boss.demo.entity.OrderDetail;
import com.boss.demo.entity.OrderMain;
import com.boss.demo.entity.ShopCart;
import com.boss.demo.repository.ConsumerRepository;
import com.boss.demo.repository.OrderDetailRepository;
import com.boss.demo.repository.OrderMainRepository;
import com.boss.demo.repository.ShopCartRepository;
import com.boss.demo.security.roles.IsUser;
import com.boss.demo.vo.SearchVo;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@IsUser
@Controller
@RequestMapping("/front")
public class FrontOrderMainController {

    @Autowired
    private OrderMainRepository orderMainRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ShopCartRepository shopCartRepository;

    @Autowired
    private FrontShopCartController frontShopCartController;
    @Autowired
    private FrontIndexController frontIndexController;
    @Autowired
    private ConsumerInfo consumerInfo;

    /**
     * 我的訂單
     */
    @GetMapping("/order")
    public String list(Model model){
        long consumerId = consumerInfo.getLoginConsumerId();
        List<OrderMain> orderMains = orderMainRepository.findAllByConsumerId(consumerId);
        orderMains.stream().map((item)->{
            List<OrderDetail> orderDetails = this.orderDetailRepository.findAllByOrderId(item.getId());
            item.setOrderDetails(orderDetails);
            return item;
        }).collect(Collectors.toList());
        model.addAttribute("orderMains",orderMains);
        return "front/order_main";
    }

    /**
     * 新增訂單
     * @param orderMain
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/order/add")
    @Transactional
    public String add(@Valid OrderMain orderMain, BindingResult result,Model model){
        if( result.hasErrors() ){
            model.addAttribute("orderMain", orderMain);
            return frontShopCartController.list(model);
        }
        Consumer consumer = consumerInfo.getConsumer();

        List<ShopCart> shopCarts = this.shopCartRepository.findAllByUserId(consumer.getId());
        int total = shopCarts.stream().mapToInt(item->item.getNumber()*item.getAmount()).sum();
        orderMain.setOrderSn("A"+System.currentTimeMillis());
        orderMain.setStatus(0);
        orderMain.setOrderTime(new Date());
        orderMain.setConsumerId(consumer.getId());
        orderMain.setAmount(total);
        orderMain = orderMainRepository.save(orderMain);

        OrderMain finalOrderMain = orderMain;
        List<OrderDetail> orderDetails = shopCarts.stream().map((item) -> {
           OrderDetail orderDetail = new OrderDetail();
           BeanUtils.copyProperties(item,orderDetail);
           orderDetail.setOrderId(finalOrderMain.getId());
           return orderDetail;
        }).collect(Collectors.toList());
        this.orderDetailRepository.saveAll(orderDetails);
        this.shopCartRepository.deleteByUserId(consumer.getId());
        //返回首頁
        return frontIndexController.index(new SearchVo(),model);
    }


}
