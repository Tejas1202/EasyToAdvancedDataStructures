package com.tejas;

import java.util.Iterator;

@SuppressWarnings({"unchecked", "SpellCheckingInspection","WeakerAccess"})
public class DoublyLinkedList<T> implements Iterable<T>{
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    //Internal node class to represent data
    private static class Node<T> {
        private T data; //data inside the Node
        private Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    //Empty the linked list, O(n)
    public void clear() {
        Node<T> trav = head; //trav pointer pointing to head
        while (trav != null) {
            Node<T> next = trav.next;
            trav.prev = trav.next = null;
            trav.data = null;
            trav = next; //moving our trav to next node
        }
        head = tail = trav = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    //Add an element to the tail of the linked list, O(1)
    public void add(T elem) {
        addLast(elem);
    }

    //Add a node to the tail of the linked list, O(1)
    private void addLast(T elem) {
        if(isEmpty()) {
            head = tail = new Node<>(elem, null, null);
        } else {
            tail.next = new Node<>(elem, tail, null);
            tail = tail.next;
        }
        size++;
    }

    //Add an element to the beginning of the linked list, O(1)
    public void addFirst(T elem) {
        if(isEmpty()) {
            head = tail = new Node<>(elem, null,null);
        } else {
            head.prev = new Node(elem, null, head);
            head = head.prev;
        }
        size++;
    }

    //Add an element at a specified index
    public void addAt(int index, T data) throws Exception {
        if(index < 0) {
            throw new Exception("Illegal index");
        }
        if(index == 0) {
            addFirst(data);
            return;
        }
        if(index == size) {
            addLast(data);
            return;
        }

        Node<T> temp = head;
        // Traversing our pointer upto the node prior to the index
        for(int i = 0; i < index - 1; i++) {
            temp = temp.next;
        }
        Node<T> newNode = new Node(data, temp, temp.next);
        temp.next.prev = newNode; // setting the next node's previous pointer
        temp.next = newNode; // setting the previous's node's next pointer

        size++;
    }

    // Check the value of the first node if it exists, O(1)
    public T peekFirst() {
        if(isEmpty()) throw new RuntimeException("Empty list");
        return head.data;
    }

    // Check the value of the last node if it exists, O(1)
    public T peekLast() {
        if(isEmpty()) throw new RuntimeException("Empty list");
        return tail.data;
    }

    //Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        if(isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the head and move the head pointer forward one node
        T data = head.data;
        head = head.next;
        --size;

        if(isEmpty()) tail = null;
        else head.prev = null; // do a memory cleanup of the previous node

        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        if(isEmpty()) throw new RuntimeException("Empty list");

        // Extract the data at the tail and move the tail pointer backwards one node
        T data = tail.data;
        tail = tail.prev;
        --size;

        if(isEmpty()) head = null;
        else tail.next = null; // do a memory clean of the node that was just removed

        return data;
    }

    // Remove an arbitrary node from the linked list, O(1)
    private T remove(Node<T> node) {
        // If the node to remove is somewhere either at the head or the tail
        if(node.prev == null) return removeFirst();
        if(node.next == null) return removeLast();

        // Make the pointers of adjacent nodes skip over 'node'
        node.next.prev = node.prev;
        node.prev.next = node.next;

        T data = node.data;

        // Memory cleanup
        node.data = null;
        node = node.prev = node.next = null;
        --size;

        return data;
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        if(index < 0 || index >= size) {
            throw new IllegalArgumentException();
        }

        int i;
        Node<T> trav;

        // Search from the front of the list
        if(index < size/2) {
            for(i = 0, trav = head; i != index; i++) {
                trav = trav.next;
            }
        }
        //Search from the back of the list
        else {
            for(i = size - 1, trav = tail; i != index; i--){
                trav = trav.prev;
            }
        }

        return remove(trav);
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(Object obj) {
        Node<T> trav;

        if(obj == null) {
            for(trav = head; trav != null; trav = trav.next) {
                if(trav.data == null) {
                    remove(trav);
                    return true;
                }
            }
        }
        else {
            for(trav = head; trav != null; trav = trav.next) {
                if(obj.equals(trav.data)){
                    remove(trav);
                    return true;
                }
            }
        }
        return false;
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(Object obj) {
        int index = 0;
        Node<T> trav = head;

        if(obj == null) {
            for(; trav != null; trav = trav.next, index++) {
                if(trav.data == null) {
                    return index;
                }
            }
        }
        else {
            for(; trav !=null; trav = trav.next, index++) {
                if(obj.equals(trav.data)) {
                    return index;
                }
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
            private Node<T> trav = head;
            @Override
            public boolean hasNext() {
                return trav != null;
            }

            @Override
            public T next() {
                T data = trav.data;
                trav = trav.next;
                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav.data + ", ");
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }

}
