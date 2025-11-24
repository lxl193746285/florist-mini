package com.qy.utils;


@FunctionalInterface
public interface PermissionMethod<F> {
	/**
	 * 验证后台系统权限
	 * @param f
	 * @return
	 */
	Boolean check(F f);
}
