package com.boss.demo.controller.front;

import com.boss.demo.entity.Category;
import com.boss.demo.entity.Consumer;
import com.boss.demo.entity.DishFlavor;
import com.boss.demo.entity.Member;
import com.boss.demo.repository.ConsumerRepository;
import com.boss.demo.tools.R;
import com.boss.demo.tools.RandomValidateCodeUtil;
import com.boss.demo.tools.ValidatorUtil;
import com.boss.demo.vo.DishVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/front")
public class FrontConsumerController {

    @Autowired
    private ConsumerRepository consumerRepository;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     *  取得驗證碼
     * @param phone
     * @return
     */
    @GetMapping("/smsCode")
    @ResponseBody
    public R getSmsCode(@RequestParam String phone){
        String smsCode = (String)redisTemplate.opsForValue().get(phone);
        if( smsCode==null ){
            smsCode = ValidatorUtil.getRnd();
            //TODO發送簡訊
            redisTemplate.opsForValue().set(phone, smsCode,600, TimeUnit.SECONDS);
        }
        return R.ok().data("smsCode",smsCode);
    }


    @GetMapping("/register")
    public String register(Model model){
        this.setModel(model,new Consumer());
        return "front/register";
    }

    @PostMapping("/registerSave")
    public String registerSave(@Valid Consumer consumer, BindingResult result, Model model){
        try {
            Consumer dbConsumer = consumerRepository.findByPhone(consumer.getPhone());
            if( dbConsumer!=null ){
                throw new Exception("此電話已註冊，請登入");
            }
            String smsCode = (String)redisTemplate.opsForValue().get(consumer.getPhone());
            if( smsCode==null || !smsCode.equals(consumer.getSmsCode()) ){
                throw new Exception("簡訊認證碼錯誤");
            }
            this.consumerRepository.save(consumer);
        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", e.getMessage());
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,consumer);
            return "front/register";
        }
        return "redirect:/front/";
    }

    @GetMapping("/login")
    public String login(Model model){
        this.setModel(model,new Consumer());
        return "front/login";
    }

    @PostMapping("/doLogin")
    public String doLogin(Consumer consumer, BindingResult result, Model model, HttpSession session){
        try {
            String random = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if( !consumer.getCheckCode().equalsIgnoreCase(random) ){
                throw new Exception("驗證碼錯誤");
            }


            Consumer dbConsumer = consumerRepository.findByPhone(consumer.getPhone());
            if( dbConsumer==null ){
                throw new Exception("查無此人");
            }
            if( !dbConsumer.getPassword().equals(consumer.getPassword()) ){
                throw new Exception("密碼錯誤");
            }
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(consumer.getPhone(), consumer.getPassword());
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dbConsumer);
            redisTemplate.opsForValue().set("consumer_"+dbConsumer.getId(),json,1,TimeUnit.DAYS);

        }catch(Exception e){
            e.printStackTrace();
            ObjectError error = new ObjectError("globalError", e.getMessage());
            result.addError(error);
        }
        if( result.hasErrors() ){
            this.setModel(model,consumer);
            return "front/login";
        }
        return "redirect:/front/";
    }



    private void setModel(Model model, Consumer consumer){
        model.addAttribute("consumer", consumer);
    }


}
