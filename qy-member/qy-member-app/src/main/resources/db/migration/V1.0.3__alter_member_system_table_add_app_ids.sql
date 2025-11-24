ALTER TABLE `ark_mbr_member_system`
    ADD COLUMN `app_ids` VARCHAR(200) NOT NULL DEFAULT '' COMMENT '应用系统id' AFTER `organization_id`;
