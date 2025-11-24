package com.qy.utils;

@FunctionalInterface
public interface PermissionDelegate<T, U, R> {
    R check(T t, U u);
}
