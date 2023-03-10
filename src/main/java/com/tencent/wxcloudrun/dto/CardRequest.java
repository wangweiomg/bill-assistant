package com.tencent.wxcloudrun.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CardRequest implements Serializable {

    private Integer userId;

    private Integer cardId;

    /**
     * 卡片名称， 非空
     */
    private String name;

    private String remark;

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


}
