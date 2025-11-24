package com.qy.message.app.application.parse;

public enum Global {
	USER_ID("USER_ID","com.qy.system.common.parse.impl.UserId",1),
	USER_NAME("USER_NAME","com.qy.system.common.parse.impl.UserName",1),
	COMPANY_ID("COMPANY_ID","com.qy.system.common.parse.impl.CompanyId",1),
	COMPANY_NAME("COMPANY_NAME","com.qy.system.common.parse.impl.CompanyName",1),
	SYSDATE("SYSDATE","com.qy.system.common.parse.impl.Sysdate",1),
	SYSDATETIME("SYSDATETIME","com.qy.system.common.parse.impl.Sysdatetime",1),
	STORE_ID("STORE_ID","",2),
	STORE_NAME("STORE_NAME","",2),
	TABLE_NAME("TABLE_NAME","",2),
	ROW_ID("ROW_ID","",2);
	private String name;
	private String index;
	private int flag;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	private Global(String index, String name, int flag) {
		this.name = name;
		this.index = index;
		this.flag = flag;
	}

	private Global() {
	}

	/**
	 * 根据值获取名称
	 * 
	 * @param index
	 * @return
	 */
	public static String getName(String index) {
		for (Global m : Global.values()) {
			if (m.getIndex().equals(index)) {
				return m.name;
			}
		}

		return "";
	}
	/**
	 * 根据值获取名称
	 * 
	 * @param index
	 * @return
	 */
	public static Global getClass(String index) {
		for (Global m : Global.values()) {
			if (m.getIndex().equals(index)) {
				return m;
			}
		}
		
		return null;
	}
}
