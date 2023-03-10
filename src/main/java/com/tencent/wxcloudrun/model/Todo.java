package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Todo {

    private Integer id;

    private Integer userId;

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
     * 截止日期
     */
    private LocalDateTime deadline;

    private LocalDateTime completeDate;

    /**
     * 备注
     */
    private String remark;

    private Integer createdBy;
    private LocalDateTime createdAt;

    private Integer updatedBy;
    private LocalDateTime updatedAt;

    private Integer deleteFlag;


}
