package com.zgf.util;

import com.zgf.error.CommonErrorCode;
import com.zgf.error.ErrorCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by zgf
 * Date 2018/12/2 16:27
 * Description
 */
@Slf4j
public class DigestUtil {

    public static void checkDigest(String rawData, String sessionKey, String signature) {
        log.info("rawData:{},sessionKey:{},signature:{}", rawData, sessionKey, signature);
        // 调用 apache 的公共包进行 SHA1 加密处理
        String sha1 = DigestUtils.sha1Hex((rawData + sessionKey).getBytes());
        log.info("[DigestUtil.checkDigest] sha1:{},signature:{}", sha1, signature);
        boolean equals = sha1.equals(signature);
        if (!equals) {
            throw new ErrorCodeException(CommonErrorCode.SIGNATURE_ERROR);
        }
    }
}
