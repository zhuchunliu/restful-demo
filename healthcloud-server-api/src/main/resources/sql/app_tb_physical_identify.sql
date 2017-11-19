CREATE DATABASE healthcloud_server;
USE healthcloud_server;
CREATE TABLE `app_tb_physical_identify` (
  `id` varchar(32) NOT NULL COMMENT 'uuid',
  `openid` varchar(32) DEFAULT NULL COMMENT '微信openid',
  `content` varchar(200) DEFAULT NULL COMMENT '回答内容',
  `result` varchar(200) DEFAULT NULL COMMENT '评分结果',
  `del_flag` varchar(1) NOT NULL DEFAULT '0' COMMENT '使用标志',
  `create_by` varchar(32) DEFAULT NULL COMMENT '新建者id',
  `create_date` datetime DEFAULT NULL COMMENT '新建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='中医体质辨识表';