package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    private Integer id;
    private String nickname;
    private String email;
    private String mobile;
    private String wxAppId;
    private String wxOpenId;
    private String wxUnionId;
    private String avatarUrl;
    private String gender;
    private String country;
    private String province;
    private String city;
    private String language;
    /**
     * 1, 正常 2.停用
     */
    private Integer status;

    private String remark;

    private Integer createdBy;
    private LocalDateTime createdAt;

    private Integer updatedBy;
    private LocalDateTime updatedAt;

    private Integer deleteFlag;

}
