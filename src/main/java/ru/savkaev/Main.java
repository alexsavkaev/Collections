package ru.savkaev;


import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CustomArrayList<Integer> arrayList = new CustomArrayList<>();
        CustomLinkedList<Integer> linkedList = new CustomLinkedList<>();
        fillListWIthNumbers(arrayList);
        System.out.println("array list: " + "\n" + arrayList);
        System.out.println(arrayList.size());
        arrayList.trim();
        arrayList.sort();
        System.out.println("sorted array list: " + "\n" + arrayList);
        arrayList.clear();
        System.out.println("cleared array list: " + "\n" + arrayList);
        fillListWIthNumbers(linkedList);
        System.out.println("linked list: " + "\n" + linkedList);
        linkedList.sort();
        System.out.println("sorted linked list: " + "\n" + linkedList);
        linkedList.clear();
        System.out.println("cleared linked list: " + "\n" + linkedList);


    }


    public static void fillListWIthNumbers(CustomArrayList<Integer> list) {
        Random random = new Random();
        int rndSize = random.nextInt(1001);
        for(int i = 0; i < rndSize; i++) {
            int rndValue = random.nextInt(101);
            list.add(rndValue, 0);
        }
    }
    public static void fillListWIthNumbers(CustomLinkedList<Integer> list) {
        Random random = new Random();
        int rndSize = random.nextInt(1001);
        for(int i = 0; i < rndSize; i++) {
            int rndValue = random.nextInt(101);
            list.add(rndValue);
        }
    }


}