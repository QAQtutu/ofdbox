package com.ofdbox.core.utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Stack<T> {

    private List<T> list = new LinkedList<>();

    public T pop() {
        return isEmpty() ? null : list.get(list.size() - 1);
    }

    public T poll() {
        if (isEmpty())
            return null;
        T t = list.get(list.size() - 1);
        list.remove(list.size() - 1);
        return t;
    }

    public void push(T t) {
        list.add(t);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return "Stack{" + list + '}';
    }

    public Iterator<T> getIteratorBeginBottom() {
        Stack<T> thisStack = this;
        Iterator<T> iterable = new Iterator<T>() {
            private Stack<T> stack = thisStack;
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < stack.list.size();
            }

            @Override
            public T next() {
                return list.get(index++);
            }
        };
        return iterable;
    }

    public Iterator<T> getIteratorBeginTop() {
        Stack<T> thisStack = this;
        Iterator<T> iterable = new Iterator<T>() {
            private Stack<T> stack = thisStack;
            private int index = thisStack.list.size() - 1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public T next() {
                return list.get(index--);
            }
        };
        return iterable;
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= 10; i++) {
            stack.push(i);
        }
        Iterator<Integer> iterable = stack.getIteratorBeginTop();
        while (iterable.hasNext()) {
            System.out.println(iterable.next());
        }

        iterable = stack.getIteratorBeginBottom();
        while (iterable.hasNext()) {
            System.out.println(iterable.next());
        }
    }

}
