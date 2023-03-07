package com.tencent.wxcloudrun.dto.wx;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxWatermark implements Serializable {

    private Long timestamp;
    private String appid;
}
