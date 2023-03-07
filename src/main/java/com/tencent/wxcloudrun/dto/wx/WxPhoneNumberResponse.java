package com.tencent.wxcloudrun.dto.wx;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxPhoneNumberResponse implements Serializable {

    /**
     * -1 system error	系统繁忙，此时请开发者稍候再试
     * 40029	code 无效	js_code无效
     */
    private Integer errcode;
    private String errmsg;
    private PhoneInfo phone_info;

    @Data class PhoneInfo implements Serializable {
        private String phoneNumber;
        private String purePhoneNumber;
        private String countryCode;
        private WxWatermark watermark;
    }


}
