package com.boss.demo.filter;

import com.boss.demo.tools.CodeMsg;
import com.boss.demo.tools.R;
import com.boss.demo.tools.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "InputFilter",urlPatterns = "/api/*")
@Slf4j
public class InputFilter implements Filter {

    @Autowired
    private RSAService rsaService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        String input = IOUtils.toString(requestWrapper.getBody(),"utf-8");
        log.info("filter request body is {}",input);

        //測試簽章
        try {
            System.out.println(rsaService.signSHA256RSA(input));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        //校驗輸入值
        String custom_sign = request.getHeader("custom_sign");
        try {
            if( custom_sign==null ){
                ResponseUtil.out(rsaService,response, R.error(CodeMsg.ERROR).message("請傳入custom_sign"));
                return ;
            }
            if( !rsaService.verify(input,custom_sign) ){
                ResponseUtil.out(rsaService,response,R.error(CodeMsg.ERROR).message("Request CustomSign不一致"));
                return ;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
