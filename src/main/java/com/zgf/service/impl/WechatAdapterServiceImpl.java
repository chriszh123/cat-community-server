package com.zgf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zgf.constant.Constant;
import com.zgf.dto.SessionDTO;
import com.zgf.error.CommonErrorCode;
import com.zgf.error.ErrorCodeException;
import com.zgf.service.WechatAdapterService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("wechatAdapterService")
public class WechatAdapterServiceImpl implements WechatAdapterService {
    private final Logger logger = LoggerFactory.getLogger(WechatAdapterServiceImpl.class);

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;

    @Override
    public SessionDTO jscode2session(String code) {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("content-type", "application/json")
                    .url(String.format(url, "wx7277976cd1ab9064",
                            "b854a7ab20934902d5ccf9b6a4aac0f4", code))
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                SessionDTO sessionDTO = JSONObject.parseObject(response.body().toString(), SessionDTO.class);
                logger.info("jscode2session get url -> {}, info -> {}", String.format(url, appid, secret, code),
                        JSON.toJSONString(sessionDTO));
                return sessionDTO;
            } else {
                logger.error("jscode2session authorize error -> {}", code);
                throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
            }
        } catch (Exception ex) {
            logger.error("jscode2session authorize error -> {}", code, ex);
            throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
        }
    }
}
