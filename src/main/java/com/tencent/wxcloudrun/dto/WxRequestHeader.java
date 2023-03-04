package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxRequestHeader implements Serializable {


    /**
     * 小程序用户 openid
     */
    private String openId;

    /**
     * 小程序 AppID
     */
    private String appId;

    /**
     * 小程序用户 unionid，并且满足 unionid 获取条件时有
     */
    private String unionId;

    /**
     * 调用来源方小程序/公众号用户 openid，跨账号调用时有
     */
    private String fromOpenId;

    /**
     * 调用来源方小程序/公众号 AppID，跨账号调用时有
     */
    private String fromAppId;

    /**
     * 调用来源方用户 unionid，跨账号调用时有，并且满足 unionid 获取条件时有
     */
    private String fromUnionId;

    /**
     * 所在云环境 ID
     */
    private String env;

    /**
     * 调用来源（本次运行是被什么触发）
     */
    private String source;

    /**
     * 临时 access_token（资源方身份）
     */
    private String accessToken;

    /**
     * 临时 access_token（来源方身份，跨账号调用时有）
     */
    private String cloudbaseAccessToken;

    /**
     * 临时 access_token（来源方身份，跨账号调用时有）
     */
    private String fromCloudbaseAccessToken;

    /**
     * 客户端 IPv4 或IPv6 地址
     */
    private String xForwardedFor;

}
