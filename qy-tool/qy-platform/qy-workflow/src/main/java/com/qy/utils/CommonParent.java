package com.qy.utils;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * 实体通用字段父类
 */
public class CommonParent implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@TableField(exist = false)
	protected String createTimeName = "";
	/**
	 * 更新时间
	 */
	@TableField(exist = false)
	protected String updateTimeName = "";
	/**
	 * 删除时间
	 */
	@TableField(exist = false)
	protected String deteteTimeName = "";
	/**
	 * 创建人名
	 */
	@TableField(exist = false)
	protected String creatorName = "";
	/**
	 * 更新人名
	 */
	@TableField(exist = false)
	protected String updatorName = "";
	/**
	 * 删除人名
	 */
	@TableField(exist = false)
	protected String deletorName = "";
	/**
	 * token
	 */
	@TableField(exist = false)
	protected String accessToken = "";


	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getCreateTimeName() {
		return createTimeName;
	}

	public void setCreateTimeName(String createTimeName) {
		this.createTimeName = createTimeName;
	}

	public String getUpdateTimeName() {
		return updateTimeName;
	}

	public void setUpdateTimeName(String updateTimeName) {
		this.updateTimeName = updateTimeName;
	}

	public String getDeteteTimeName() {
		return deteteTimeName;
	}

	public void setDeteteTimeName(String deteteTimeName) {
		this.deteteTimeName = deteteTimeName;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdatorName() {
		return updatorName;
	}

	public void setUpdatorName(String updatorName) {
		this.updatorName = updatorName;
	}

	public String getDeletorName() {
		return deletorName;
	}

	public void setDeletorName(String deletorName) {
		this.deletorName = deletorName;
	}
}
