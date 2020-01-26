package com.tejas;

public class Main {

    public static void main(String[] args) {
        var main = new Main();
        main.dynamicArrayCall();
        main.doublyLinkedListCall();
    }

    private void dynamicArrayCall() {
        var dynamicArray = new DynamicArray<Integer>(0);
        dynamicArray.add(5);
        dynamicArray.add(8);
        dynamicArray.add(10);
        dynamicArray.add(15);
        dynamicArray.removeAt(2);
        dynamicArray.add(17);
        /*Accessing element within capacity won't give an exception as our array has null padded element
        But beyond capacity(not the index variable in our class but capacity) will throw IndexOutOfBound*/
        System.out.println(dynamicArray.get(5));
        System.out.println(dynamicArray); //Calling the toString method in DynamicArray
        for (int number : dynamicArray ) { //Calling iterator
            System.out.println(number);
        }
    }

    private void doublyLinkedListCall() {
        var doublyLinkedList = new DoublyLinkedList<Integer>();
        doublyLinkedList.add(2);
        doublyLinkedList.add(4);
        doublyLinkedList.add(8);
        doublyLinkedList.add(11);
    }
}
