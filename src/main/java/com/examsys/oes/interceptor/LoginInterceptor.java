package com.examsys.oes.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 登录检查
 * 1.配置到拦截器要拦截哪些请求
 * 2.把这些配置放在容器中
 *
 * 实现HandlerInterceptor接口
 */
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * 目标方法执行之前
     * 登录检查写在这里，如果没有登录，就不执行目标方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
/*    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(request.getCookies() == null) {
            return false;
        }
        for (Cookie cookie : request.getCookies()) {
            if("rb_token".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().equals("")) {
                return true;
            }
        }
        return false;
    }*/

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getCookies() == null) {
            return false;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName() !=null && cookie.getValue() != null && !cookie.getValue().equals("")) {
                // 检查Cookie值是否包含非ISO-8859-1字符，如果包含则进行解码
                String decodedValue;
                try {
                    decodedValue = URLDecoder.decode(cookie.getValue(), StandardCharsets.ISO_8859_1.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }

                // 继续根据您的逻辑判断是否登录有效
                if (!decodedValue.isEmpty()) {
                    return true;
                }
            }
        }

        return false;
    }
}
