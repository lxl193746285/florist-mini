package com.qy.rest.memoize;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 缓存化
 *
 * @author legendjw
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Memoize {
    @Aspect
    @Component
    public static class MemoizeAspect {
        @Autowired
        private ResultCache resultCache;

        /**
         * Matches the execution of any methods in a type annotated with @Memoize.
         */
        @Pointcut("execution(* (@com.qy.rest.memoize.Memoize *).*(..))")
        public void methodInMemoizeType() {}

        /**
         * Matches the execution of any methods annotated with @Memoize.
         */
        @Pointcut("execution(@com.qy.rest.memoize.Memoize * *.*(..))")
        public void methodAnnotatedWithMemoize() {}

        @Around("methodAnnotatedWithMemoize() || methodInMemoizeType()")
        public Object memoize(ProceedingJoinPoint pjp) throws Throwable {
            ExecutionContext executionContext = new ExecutionContext(
                    pjp.getSignature().getDeclaringType(),
                    pjp.getSignature().getName(),
                    pjp.getArgs()
            );

            Object result = resultCache.findCachedResult(executionContext);
            if (result == ResultCache.NONE)
                result = resultCache.save(executionContext, pjp.proceed());

            return result == ResultCache.NULL ? null : result;
        }
    }
}
