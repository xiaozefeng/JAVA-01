package io.github.mickey.cache.cache;

import java.util.Objects;

/**
 * @author mickey
 * @date 2/18/21 16:19
 */
public class CacheKey {

    private String method;
    private int timeout;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return timeout == cacheKey.timeout && Objects.equals(method, cacheKey.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, timeout);
    }
}
