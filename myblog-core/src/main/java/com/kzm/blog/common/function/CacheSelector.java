package com.kzm.blog.common.function;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
