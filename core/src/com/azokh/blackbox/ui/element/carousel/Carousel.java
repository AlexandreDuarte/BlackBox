package com.azokh.blackbox.ui.element.carousel;

import java.util.ListIterator;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Carousel<T extends Enum<T>> implements Iterable<T> {

    T carouselEnum;

    public Carousel(T carouselEnum) {
        this.carouselEnum = carouselEnum;
    }

    @Override
    public ListIterator<T> iterator() {
        return new CarouselIterator<T>(carouselEnum);
    }

    static class CarouselIterator<T extends Enum<T>> implements ListIterator<T> {

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
            next = enumValues(next.getDeclaringClass())[nextIndex()];
            return next;
        }

        public T[] enumValues(Class<T> enumType) {
            return enumType.getEnumConstants();
        }


        @Override
        public boolean hasPrevious() {
            return true;
        }

        @Override
        public T previous() {
            next = enumValues(next.getDeclaringClass())[previousIndex()];
            return next;
        }

        @Override
        public int nextIndex() {
            return (next.ordinal() + 1) % enumValues(next.getDeclaringClass()).length;
        }

        @Override
        public int previousIndex() {
            return (next.ordinal() - 1) == -1 ? enumValues(next.getDeclaringClass()).length-1 : (next.ordinal() - 1) % enumValues(next.getDeclaringClass()).length;
        }

        @Override
        public void remove() {
            throw new NotImplementedException();
        }

        @Override
        public void set(T t) {

        }

        @Override
        public void add(T t) {

        }
    }

}
