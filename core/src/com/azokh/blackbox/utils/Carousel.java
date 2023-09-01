package com.azokh.blackbox.utils;

import java.util.Iterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Carousel<T extends Enum<T>> implements Iterable<T> {

    T carouselEnum;

    public Carousel(T carouselEnum) {
        this.carouselEnum = carouselEnum;
    }

    @Override
    public Iterator<T> iterator() {
        return new CarouselIterator<>(carouselEnum);
    }

    static class CarouselIterator<T extends Enum<T>> implements Iterator<T> {

        T next;

        public CarouselIterator(T carouselEnum) {
            this.next = carouselEnum;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            next = enumValues(next.getDeclaringClass())[(next.ordinal() + 1) % enumValues(next.getDeclaringClass()).length];
            return next;
        }

        public T[] enumValues(Class<T> enumType) {
            return enumType.getEnumConstants();
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

    }

}
