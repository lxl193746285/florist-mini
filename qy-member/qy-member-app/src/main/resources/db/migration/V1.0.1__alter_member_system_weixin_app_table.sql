ALTER TABLE `ark_mbr_member_system_weixin_app`
    ADD COLUMN `qr_code` BIGINT(20) UNSIGNED NOT NULL DEFAULT '0' COMMENT '二维码图片' AFTER `app_secret`;

