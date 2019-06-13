SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `type_handler_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `business_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `business_type_desc` varchar(200) DEFAULT NULL COMMENT '类型描述',
  `service_name` varchar(200) DEFAULT NULL,
  `temp_desc` varchar(512) DEFAULT NULL COMMENT '模板内容',
  `version` varchar(16) DEFAULT NULL COMMENT '版本，默认v1',
  `temp_version` varchar(16) DEFAULT NULL COMMENT '使用短信模板的版本，默认v1',
  `status` int(11) DEFAULT NULL COMMENT '1使用，0废弃',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='类型处理器映射表';


CREATE TABLE `type_temp_rel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `business_type` varchar(20) DEFAULT NULL COMMENT '类型',
  `temp_code` varchar(200) DEFAULT NULL COMMENT '模板编码',
  `temp_version` varchar(16) DEFAULT NULL COMMENT '使用短信模板的版本，默认v1',
  `status` int(11) DEFAULT NULL COMMENT '1使用，0废弃',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='业务类型与模板映射';


SET FOREIGN_KEY_CHECKS = 1;
