package com.qy.rest.memoize;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

/**
 * 指定结果
 *
 * @author legendjw
 */
public class ExecutionContext {
    public static final String TEMPLATE = "%s.%s(%s)";

    private final Class<?> targetClass;
    private final String   targetMethod;
    private final Object[] arguments;

    public ExecutionContext(Class<?> targetClass, String targetMethod, Object[] arguments) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.arguments = arguments;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public String getTargetMethod() {
        return targetMethod;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return String.format(TEMPLATE, targetClass.getName(), targetMethod, Arrays.toString(arguments));
    }
}