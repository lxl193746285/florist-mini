package com.qy.utils;

import java.io.IOException;

public class HttpResDto {
	/**
	 * 状态码
	 */
	private Integer httpCode;
	/**
	 * 提示消息
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private String content;

	public Integer getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(Integer httpCode) {
		this.httpCode = httpCode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public <T> T toObject(Class<T> clazz) throws RuntimeException {
		T res = null;
		try {
			res = JsonUtils.toSnakeObject(getContent(), clazz);
		} catch (IOException e) {
			System.out.println(getContent());
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

		return res;
	}
}
