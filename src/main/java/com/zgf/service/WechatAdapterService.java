package com.zgf.service;

import com.zgf.dto.SessionDTO;

public interface WechatAdapterService {
    SessionDTO jscode2session(String code);
}
