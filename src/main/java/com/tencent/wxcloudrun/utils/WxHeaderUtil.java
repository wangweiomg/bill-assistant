package com.tencent.wxcloudrun.utils;

import static com.tencent.wxcloudrun.constants.WxRequestHeaderNamesConstant.*;
import com.tencent.wxcloudrun.dto.WxRequestHeader;
import org.springframework.http.HttpHeaders;


public class WxHeaderUtil {


    /**
     * header	说明
     * X-WX-OPENID	小程序用户 openid
     * X-WX-APPID	小程序 AppID
     * X-WX-UNIONID	小程序用户 unionid，并且满足 unionid 获取条件时有
     * X-WX-FROM-OPENID	调用来源方小程序/公众号用户 openid，跨账号调用时有
     * X-WX-FROM-APPID	调用来源方小程序/公众号 AppID，跨账号调用时有
     * X-WX-FROM-UNIONID	调用来源方用户 unionid，跨账号调用时有，并且满足 unionid 获取条件时有
     * X-WX-ENV	所在云环境 ID
     * X-WX-SOURCE	调用来源（本次运行是被什么触发）
     * X-WX-CLOUDBASE-ACCESS-TOKEN	临时 access_token（资源方身份）
     * X-WX-FROM-CLOUDBASE-ACCESS-TOKEN	临时 access_token（来源方身份，跨账号调用时有）
     * X-Forwarded-For	客户端 IPv4 或IPv6 地址
     * @param headers
     * @return
     */
    public static WxRequestHeader covertToWxRequest(HttpHeaders headers) {

        WxRequestHeader dto = new WxRequestHeader();
        dto.setOpenId(headers.getFirst(OPEN_ID));
        dto.setAppId(headers.getFirst(APP_ID));
        dto.setUnionId(headers.getFirst(UNION_ID));
        dto.setFromOpenId(headers.getFirst(FROM_OPEN_ID));
        dto.setFromAppId(headers.getFirst(FROM_APP_ID));
        dto.setFromUnionId(headers.getFirst(FROM_UNION_ID));
        dto.setEnv(headers.getFirst(ENV));
        dto.setSource(headers.getFirst(SOURCE));
        dto.setCloudbaseAccessToken(headers.getFirst(CLOUDBASE_ACCESS_TOKEN));
        dto.setFromCloudbaseAccessToken(headers.getFirst(FROM_CLOUDBASE_ACCESS_TOKEN));
        dto.setXForwardedFor(headers.getFirst(X_FORWARDED_FOR));

        return dto;

    }
}
