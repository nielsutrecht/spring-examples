package com.nibado.example.spring.shared;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public record Pair<F, S>(F first, S second) {
    public Map.Entry<F, S> entry() {
        return entry(first, second);
    }

    public Pair<F, S> withFirst(F first) {
        return pair(first, this.second);
    }

    public Pair<F, S> withSecond(S second) {
        return pair(this.first, second);
    }

    public <X, Y> Pair<X, Y> map(Function<F, X> mapFirst, Function<S, Y> mapSecond) {
        return pair(mapFirst.apply(first), mapSecond.apply(second));
    }

    public <X> Pair<X, S> mapFirst(Function<F, X> mapFirst) {
        return map(mapFirst, second -> second);
    }

    public <Y> Pair<F, Y> mapSecond(Function<S, Y> mapSecond) {
        return map(first -> first, mapSecond);
    }


    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }

    public static <F, S> Map<F, S> toMap(Collection<Pair<F, S>> pairs) {
        return pairs.stream().collect(toMap());
    }

    public static <F, S> Pair<F, S> pair(F first, S second) {
        return new Pair<>(first, second);
    }

    public static <F, S> Pair<F, S> pair(Map.Entry<F, S> entry) {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    public static <F, S> Map.Entry<F, S> entry(F first, S second) {
        return new AbstractMap.SimpleEntry<>(first, second);
    }

    public static <F, S> Collector<Pair<F, S>, ?, Map<F, S>> toMap() {
        return Collectors.toMap(Pair::first, Pair::second);
    }
}
