package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Todo {

    private String name;
    /**
     * 1. credit card
     * 2. promissory
     * 3. iou
     * 4. other
     */
    private Integer type;

    /**
     * 0. active
     * 1. overdue
     * 2. done_before_due
     * 3. done_after_due by customer
     * 4. done_after_due by system 长期未操作，系统处理为完成
     */
    private Integer status;

    /**
     * 多期账单， 某一期的起止日期
     */
    private String period;


    private String remark;

    private Integer createdBy;
    private LocalDateTime createdAt;

    private Integer updatedBy;
    private LocalDateTime updatedAt;


}
