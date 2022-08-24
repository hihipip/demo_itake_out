package com.boss.demo.controller.front;


import com.boss.demo.tools.RandomValidateCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/front")
@Slf4j
public class VerifyController {

    /**
     * @description 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("image/jpeg");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            log.error("無法取得驗證碼", e);
        }
    }

    /**
     * @description 校验验证码
     */
    @PostMapping("/checkVerify")
    public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try {
            // 从session中获取随机数
            String inputStr = verifyInput;
            String random = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
            if (random == null) {
                return false;
            }
            if (random.equalsIgnoreCase(inputStr)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("验证码校验失败", e);
            return false;
        }
    }


}
