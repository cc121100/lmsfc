use lmsfc;
-- filter
-- tbl_filter
DROP TABLE IF EXISTS `tbl_filter`;
CREATE TABLE `tbl_filter` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `filter_class_name` varchar(255) NOT NULL,
  `filter_class_params` varchar(255) NOT NULL,
  `filter_name` varchar(255) NOT NULL,
  `param_num` varchar(255) DEFAULT NULL,
  `param_type` varchar(255) NOT NULL,
  `set_param_method_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_filter_rule
DROP TABLE IF EXISTS `tbl_filter_rule`;
CREATE TABLE `tbl_filter_rule` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `source_domain` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_filter_detail
DROP TABLE IF EXISTS `tbl_filter_detail`;
CREATE TABLE `tbl_filter_detail` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `category` varchar(255) NOT NULL,
  `param_value1` varchar(255) DEFAULT NULL,
  `param_value2` varchar(255) DEFAULT NULL,
  `sub_num` int(11) NOT NULL,
  `filter_id` varchar(255) DEFAULT NULL,
  `filter_rule_id` varchar(255) DEFAULT NULL,
  `parent_node_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_r3mobl1e3y5h54wadnivvxpht` (`filter_id`),
  KEY `FK_qwaeror7r5pkhlbxu13ecbevr` (`filter_rule_id`),
  KEY `FK_8rbks35frlk90snfhx36xb8cj` (`parent_node_id`),
  CONSTRAINT `FK_8rbks35frlk90snfhx36xb8cj` FOREIGN KEY (`parent_node_id`) REFERENCES `tbl_filter_detail` (`id`),
  CONSTRAINT `FK_qwaeror7r5pkhlbxu13ecbevr` FOREIGN KEY (`filter_rule_id`) REFERENCES `tbl_filter_rule` (`id`),
  CONSTRAINT `FK_r3mobl1e3y5h54wadnivvxpht` FOREIGN KEY (`filter_id`) REFERENCES `tbl_filter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- article task job
-- tbl_batch_article_tkj
DROP TABLE IF EXISTS `tbl_batch_article_tkj`;
CREATE TABLE `tbl_batch_article_tkj` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL default '0',
  `type` int(11) NOT NULL default '0',
  `finish_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_article_tkj
DROP TABLE IF EXISTS `tbl_article_tkj`;
CREATE TABLE `tbl_article_tkj` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `state` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `url` varchar(255) NOT NULL,
  `batch_art_tkj_id` varchar(255) DEFAULT NULL,
  `filter_rule_id` varchar(255) DEFAULT NULL,
  `finish_time` datetime DEFAULT NULL,
  `is_whole` int(11) NOT NULL DEFAULT '0',
  `target_catgory` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_kg7hl1g4jivpkdh0dloeubq1d` (`batch_art_tkj_id`),
  KEY `FK_lc6erf6ensv84tcl7dcwxrhee` (`filter_rule_id`),
  CONSTRAINT `FK_kg7hl1g4jivpkdh0dloeubq1d` FOREIGN KEY (`batch_art_tkj_id`) REFERENCES `tbl_batch_article_tkj` (`id`),
  CONSTRAINT `FK_lc6erf6ensv84tcl7dcwxrhee` FOREIGN KEY (`filter_rule_id`) REFERENCES `tbl_filter_rule` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_art_tkj_run_log
DROP TABLE IF EXISTS `tbl_art_tkj_run_log`;
CREATE TABLE `tbl_art_tkj_run_log` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `art_task_step` int(11) NOT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `art_tkj_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3756tflph20ksntcl1nyo85dx` (`art_tkj_id`),
  CONSTRAINT `FK_3756tflph20ksntcl1nyo85dx` FOREIGN KEY (`art_tkj_id`) REFERENCES `tbl_article_tkj` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- article
-- tbl_article_category
DROP TABLE IF EXISTS `tbl_article_category`;
CREATE TABLE `tbl_article_category` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `path_name` varchar(100) NOT NULL,
  `status` varchar(255) NOT NULL,
  `sequence` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_article_element
DROP TABLE IF EXISTS `tbl_article_element`;
CREATE TABLE `tbl_article_element` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `file_location` varchar(255) NOT NULL,
  `files` varchar(256) NOT NULL,
  `name` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `article_tkj_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n259pt2w3q3tw0t00pjo8dt83` (`article_tkj_id`),
  CONSTRAINT `FK_n259pt2w3q3tw0t00pjo8dt83` FOREIGN KEY (`article_tkj_id`) REFERENCES `tbl_article_tkj` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_article
DROP TABLE IF EXISTS `tbl_article`;
CREATE TABLE `tbl_article` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `art_file_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `generate_time` datetime DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `art_cate_id` varchar(255) DEFAULT NULL,
  `art_ele_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_5exbvobr6wnhmpfdqi0l6ea8l` (`art_cate_id`),
  KEY `FK_emilslsudrvfsbvd75sjanwa3` (`art_ele_id`),
  CONSTRAINT `FK_5exbvobr6wnhmpfdqi0l6ea8l` FOREIGN KEY (`art_cate_id`) REFERENCES `tbl_article_category` (`id`),
  CONSTRAINT `FK_emilslsudrvfsbvd75sjanwa3` FOREIGN KEY (`art_ele_id`) REFERENCES `tbl_article_element` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- sys
-- tbl_sys_resource
DROP TABLE IF EXISTS `tbl_sys_resource`;
CREATE TABLE `tbl_sys_resource` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `permission` varchar(50) NOT NULL,
  `resource_name` varchar(50) NOT NULL,
  `state` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `url` varchar(50) NOT NULL,
  `parent_resource_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_15dp836ilit7l57fu9mrdlyv` (`parent_resource_id`),
  CONSTRAINT `FK_15dp836ilit7l57fu9mrdlyv` FOREIGN KEY (`parent_resource_id`) REFERENCES `tbl_sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_sys_role
DROP TABLE IF EXISTS `tbl_sys_role`;
CREATE TABLE `tbl_sys_role` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `role_description` varchar(50) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_sys_user
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_dt` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `updated_dt` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `state` int(11) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_sys_role_res
DROP TABLE IF EXISTS `tbl_sys_role_res`;
CREATE TABLE `tbl_sys_role_res` (
  `role_id` varchar(255) NOT NULL,
  `resource_id` varchar(255) NOT NULL,
  KEY `FK_jn00cu1cwwk4c3isopr944nwq` (`resource_id`),
  KEY `FK_749rbm88ufvcd18u2u4c20mao` (`role_id`),
  CONSTRAINT `FK_749rbm88ufvcd18u2u4c20mao` FOREIGN KEY (`role_id`) REFERENCES `tbl_sys_role` (`id`),
  CONSTRAINT `FK_jn00cu1cwwk4c3isopr944nwq` FOREIGN KEY (`resource_id`) REFERENCES `tbl_sys_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- tbl_sys_user_role
DROP TABLE IF EXISTS `tbl_sys_user_role`;
CREATE TABLE `tbl_sys_user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  KEY `FK_onm66br5n9tic3qd3xdtkmfd2` (`role_id`),
  KEY `FK_52opresf1j8il2333dccd958w` (`user_id`),
  CONSTRAINT `FK_52opresf1j8il2333dccd958w` FOREIGN KEY (`user_id`) REFERENCES `tbl_sys_user` (`id`),
  CONSTRAINT `FK_onm66br5n9tic3qd3xdtkmfd2` FOREIGN KEY (`role_id`) REFERENCES `tbl_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


