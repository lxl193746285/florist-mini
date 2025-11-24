package com.qy.organization.app.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ark_company_user_weixin")
public class UserWeiXin implements Serializable {

    private Long userId;
    private String unionid;
    private String openid;
}
