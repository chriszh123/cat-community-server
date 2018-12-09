package com.zgf.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zgf.dto.ResultDTO;
import com.zgf.error.CommonErrorCode;
import com.zgf.model.User;
import com.zgf.service.UserService;
import com.zgf.session.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by zgf
 * Date 2018/12/8 13:38
 * Description
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //请求之前，验证通过返回true，验证失败返回false
        String token = request.getHeader("token");
        log.info("[LoginInterceptor-preHandle]token:{}", token);
        if (StringUtils.isBlank(token)) {
            makeFail(response);
            return false;
        }
        // 通过 token 从数据库中获取信息，如果没有验证失败
        // 如果通过一台设备登录，再通过另一台设备登录，第一台设备会自动登出
        User user = userService.getByToken(token);
        log.info("[LoginInterceptor-preHandle] user:{}", JSONObject.toJSONString(user));
        if (user == null) {
            makeFail(response);
            return false;
        }
        //把获取到的user信息暂存到 ThreadLocal 里面，以便上线文中方便的使用
        SessionUtil.setUser(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //请求结束以后移除 user
        SessionUtil.removeUser();
        log.info("[LoginInterceptor-postHandle] SessionUtil.removeUser().");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void makeFail(HttpServletResponse response) {
        ResultDTO resultDTO = ResultDTO.fail(CommonErrorCode.NO_USER);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            PrintWriter out = response.getWriter();
            out.print(JSON.toJSONString(resultDTO));
            out.close();
        } catch (Exception e) {
            log.error("LoginInterceptor preHandle", e);
        }
    }
}
