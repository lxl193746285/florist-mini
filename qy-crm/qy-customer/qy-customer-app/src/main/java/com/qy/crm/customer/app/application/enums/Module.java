package com.qy.crm.customer.app.application.enums;

/**
 * 模块
 *
 * @author legendjw
 */
public enum Module {
    CUSTOMER("crm_customer", "客户"),
    CONTACT("crm_contact", "联系人"),
    STORE("store", "店铺"),
    ;

    private String id;
    private String name;

    Module(String  id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
