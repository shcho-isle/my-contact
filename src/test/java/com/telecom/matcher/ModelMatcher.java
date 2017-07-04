package com.telecom.matcher;

import org.junit.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ModelMatcher<T> {

    private Equality<T> equality;

    public interface Equality<T> {
        boolean areEqual(T expected, T actual);
    }

    private ModelMatcher() {
        this((T expected, T actual) -> expected == actual || String.valueOf(expected).equals(String.valueOf(actual)));
    }

    private ModelMatcher(Equality<T> equality) {
        this.equality = equality;
    }

    public static <T> ModelMatcher<T> of() {
        return new ModelMatcher<>();
    }

    public static <T> ModelMatcher<T> of(Equality<T> equality) {
        return new ModelMatcher<>(equality);
    }

    private class Wrapper {
        private T entity;

        private Wrapper(T entity) {
            this.entity = entity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Wrapper that = (Wrapper) o;
            return entity != null ? equality.areEqual(entity, that.entity) : that.entity == null;
        }

        @Override
        public String toString() {
            return String.valueOf(entity);
        }
    }

    public void assertEquals(T expected, T actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    public void assertCollectionEquals(Collection<T> expected, Collection<T> actual) {
        Assert.assertEquals(wrap(expected), wrap(actual));
    }

    private Wrapper wrap(T entity) {
        return new Wrapper(entity);
    }

    private List<Wrapper> wrap(Collection<T> collection) {
        return collection.stream().map(this::wrap).collect(Collectors.toList());
    }
}
