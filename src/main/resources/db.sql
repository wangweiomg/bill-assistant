CREATE TABLE `Counters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1',
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS t_card;
CREATE TABLE `t_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `name` varchar(30) NOT NULL DEFAULT '' COMMENT '卡片名称',
  `card_no` varchar(30) DEFAULT '' COMMENT '卡号，或尾号',
  `password` varchar(30) DEFAULT '' COMMENT '卡片支付密码',
  `bank_id` tinyint(2) DEFAULT NULL COMMENT '所属银行id',
  `card_type` tinyint(1) DEFAULT '1' COMMENT '卡片类型1贷记卡2储蓄卡',
  `card_limit` decimal(10,0) DEFAULT NULL COMMENT '卡片额度',
  `bill_day` tinyint(2) DEFAULT NULL COMMENT '账单日',
  `repay_day_type` tinyint(1) DEFAULT NULL COMMENT '还款日类型1固定还款日2账单日后n天',
  `repay_day_num` tinyint(2) DEFAULT NULL COMMENT '还款日记数',
  `status` tinyint(1) DEFAULT '1' COMMENT '卡状态1.启用 2停用',
  `remark` varchar(100) DEFAULT '' COMMENT '备注',
  `created_by` int(11) DEFAULT NULL COMMENT '创建人id',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT NULL COMMENT '更新人',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_flag` tinyint(1) DEFAULT '0' COMMENT '1已删除 0未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  COMMENT='信用卡卡片信息';

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(50) DEFAULT '',
  `email` varchar(50) DEFAULT '',
  `mobile` varchar(15) DEFAULT '',
  `wx_app_id` varchar(50) DEFAULT '',
  `wx_open_id` varchar(50) NOT NULL,
  `wx_union_id` varchar(50) DEFAULT '',
  `avatar_url` varchar(100) DEFAULT '',
  `gender` varchar(10) DEFAULT '',
  `country` varchar(20) DEFAULT '',
  `province` varchar(20) DEFAULT '',
  `city` varchar(20) DEFAULT '',
  `language` varchar(10) DEFAULT '',
  `status` tinyint(1) DEFAULT '1',
  `remark` varchar(30) DEFAULT '',
  `created_by` int(11) DEFAULT '1',
  `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `updated_by` int(11) DEFAULT '1',
  `updated_at` datetime DEFAULT CURRENT_TIMESTAMP,
  `delete_flag` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_t_card_open_id` (`wx_open_id`) USING BTREE
) ENGINE=InnoDB COMMENT='微信用户信息表';
