package com.qy.message.app.application.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum MessageTemplateCustomParameterAction {
	VIEW("view","查看",1),
	ADD("add","增加",2),
	EDIT("edit", "编辑", 3),
	DELETE("delete", "删除", 4);

	private String id;
	private String name;
	private Integer sort;

	private MessageTemplateCustomParameterAction(String id, String name, Integer sort) {
		this.id = id;
		this.name = name;
		this.sort = sort;
	}

	private MessageTemplateCustomParameterAction() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
