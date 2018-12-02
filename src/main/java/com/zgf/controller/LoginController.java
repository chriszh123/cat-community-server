package com.zgf.controller;

import com.zgf.dto.LoginDTO;
import com.zgf.dto.ResultDTO;
import com.zgf.dto.SessionDTO;
import com.zgf.dto.TokenDTO;
import com.zgf.error.CommonErrorCode;
import com.zgf.error.ErrorCodeException;
import com.zgf.service.WechatAdapter;
import com.zgf.service.WechatAdapterService;
import com.zgf.util.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * RestController 定义 RESTFUL Controller，默认返回 JSON 数据
 * Slf4j 添加 LOG 注解，自动注入 log以便打印日志
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private WechatAdapterService wechatAdapterService;

//    @Autowired
//    private WechatAdapter wechatAdapter;

    // 定义 domain/api/login 访问接口，用于实现登录
    // 使用 LoginDTO 自动解析传递过来的 JSON 数据
    @RequestMapping("/api/login")
    public ResultDTO login(@RequestBody LoginDTO loginDTO) {
        try {
            log.info("login request : {}", loginDTO);
            // 使用 code 调用微信 API 获取 session_key 和 openid
            SessionDTO sessionDTO = wechatAdapterService.jscode2session(loginDTO.getCode());
            log.info("login get session : {}", sessionDTO);
            // 检验传递过来的使用户信息是否合法
            DigestUtil.checkDigest(loginDTO.getRawData(), sessionDTO.getSessionKey(), loginDTO.getSignature());
            //TODO: 储存 token
            //生成token，用于自定义登录态，这里的存储逻辑比较复杂，放到下一讲
            TokenDTO data = new TokenDTO();
            data.setToken(UUID.randomUUID().toString());
            return ResultDTO.ok(data);
        } catch (ErrorCodeException e) {
            log.error("login error, info : {}", loginDTO, e.getMessage());
            return ResultDTO.fail(e);
        } catch (Exception e) {
            log.error("login error, info : {}", loginDTO, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }
}
