package com.tencent.wxcloudrun.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Card implements Serializable {

    private Integer id;

    private Integer userId;
    /**
     * 卡片名称， 非空
     */
    private String name;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 支付密码
     */
    private String password;

    /**
     * 所属银行ID,非空
     */
    private Integer bankId;
    /**
     * 卡片类型 1. 贷记卡 2.储蓄卡
     */
    private Integer cardType;
    /**
     * 卡片额度
     */
    private BigDecimal cardLimit;
    /**
     * 账单日
     */
    private Integer billDay;
    /**
     * 还款日类型 1.固定还款日 2.账单日后多少天
     */
    private Integer repayDayType;
    /**
     * 还款日记数
     * 如果是固定还款日，就是还款日
     * 如果是账单日后多少天， 就是多少天
     */
    private Integer repayDayNum;
    /**
     * 卡片状态 1，启用中
     */
    private Integer status;

    private String remark;

    private Integer createdBy;
    private LocalDateTime createdAt;

    private Integer updatedBy;
    private LocalDateTime updatedAt;

    private Integer deleteFlag;

    
}
