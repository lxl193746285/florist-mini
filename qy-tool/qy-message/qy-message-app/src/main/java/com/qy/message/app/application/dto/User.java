package com.qy.message.app.application.dto;

import java.io.Serializable;

/**
 * @数表名称 st_v2_company_user
 * @开发日期 2020-02-22
 * @开发作者 by:qiuhoujian 
 */
public class User implements Serializable {
    /** 用户ID (主健ID) (无默认值) */
    private Integer id;

    /** 公司ID(必填项)  (默认值为: 0) */
    private Integer companyId;

    /** 真实姓名(必填项)  (默认值为: ) */
    private String realname;

    public User(Integer id, Integer companyId, String realname) {
        this.id = id;
        this.companyId = companyId;
        this.realname = realname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }
}