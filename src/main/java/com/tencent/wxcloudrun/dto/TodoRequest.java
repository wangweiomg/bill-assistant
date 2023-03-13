package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TodoRequest implements Serializable {

    private Integer id;
    private Boolean checked;
    private Integer status;
}
