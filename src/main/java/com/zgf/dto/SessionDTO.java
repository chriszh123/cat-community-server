package com.zgf.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by MI
 * Date 2018/12/2 15:38
 * Description
 */
@Data
public class SessionDTO {
    private String openid;

    @JSONField(name = "session_key")
    private String sessionKey;
}
