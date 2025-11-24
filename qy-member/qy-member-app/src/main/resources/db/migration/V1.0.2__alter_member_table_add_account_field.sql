ALTER TABLE `ark_mbr_member`
    ADD COLUMN `account_id` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '账号id' AFTER `system_id`;

ALTER TABLE `ark_mbr_member_system`
    ADD COLUMN `is_default` TINYINT(3) UNSIGNED NOT NULL DEFAULT '0' COMMENT '是否是默认的会员系统' AFTER `is_member_audit`;

