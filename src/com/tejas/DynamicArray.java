package com.tejas;

import java.util.Iterator;

@SuppressWarnings("unchecked,WeakerAccess")
public class DynamicArray<T> implements Iterable<T> {

    private T[] arr; //our internal static array
    private int len; //length user thinks array is
    private int capacity; //Actual array size (because sometimes our internal array has free slots and we don't want
    //to tell the users that there are extra free slots available. It's just for our knowledge)

    public DynamicArray() {
        this(16);
    }

    public DynamicArray(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        //as the user hasn't added any element through constructor, so but obviously, why would the length increase
        arr = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public int size() {
        return len;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public T get(int index) {
        return arr[index];
    }

    public void set(int index, T elem) {
        arr[index] = elem;
    }

    public void clear() {
        for (int i = 0; i < len; i++) {
            arr[i] = null;
        }
        len = 0;
    }

    public void add(T elem) {
        //Resize!
        if (len + 1 >= capacity) {
            if (capacity == 0) capacity = 1;
            else capacity *= 2; //double the size
            T[] new_arr = (T[]) new Object[capacity];
            for (int i = 0; i < len; i++) {
                new_arr[i] = arr[i];
            }
            arr = new_arr; // arr has extra nulls padded
        }
        /*When adding the first element, length will be zero. So arr[0] = elem,
        after that incrementing the length so that next assignment can be done at index 1*/
        arr[len++] = elem;
    }

    // Removes an element at the specified index in this array.
    public T removeAt(int rm_index) {
        if (rm_index >= len || rm_index < 0)
            throw new IndexOutOfBoundsException();
        T data = arr[rm_index];
        T[] new_arr = (T[]) new Object[len - 1];
        for (int i = 0, j = 0; i < len; i++, j++) {
            if (i == rm_index) j--; // Skip over rm_index by fixing j temporarily
            else new_arr[j] = arr[i];
        }
        arr = new_arr;
        //reducing the capacity to len variable as the extra null padded elements won't be present in current arr instance
        capacity = --len;

        return data;
    }

    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index != -1) {
            removeAt(index);
            return true;
        }
        return false;
    }

    public int indexOf(Object obj) {
        for (int i = 0; i < len; i++) {
            if (obj == null) { //Null check
                if (arr[i] == null) return i;
            } else {
                if (obj.equals(arr[i])) return i;
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < len;
            }

            @Override
            public T next() {
                return arr[index++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        if(len == 0) return "[]";
        else {
            var sb = new StringBuilder().append("[");
            for(int i = 0; i < len - 1; i++) {
                sb.append(arr[i] + ", ");
            }
            return sb.append(arr[len - 1] + "]").toString();
        }
    }
}
