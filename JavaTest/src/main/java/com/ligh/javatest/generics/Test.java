package com.ligh.javatest.generics;


import java.util.ArrayList;
import java.util.List;

class Fruit {
    int weight = 0;

}

class Apple extends Fruit {

}

class Banana extends Fruit {

}

public class Test {

    static Apple apple;

    static int calculateWidget(List<? extends Fruit> fruits) {
        int total = 0;
        for (Fruit fruit : fruits) {
            total += fruit.weight;
        }
        return total;
    }

    static void addDataToList(List<? super Apple> list) {
        list.add(apple);
    }

    public static void main(String[] args) {
//        List<Apple> apples = new ArrayList<Apple>();
////        Test.calculateWidget(apples);  //error
//        Test.calculateWidget(apples);


//        List<? extends Fruit> fruits = new ArrayList<Apple>();
//        fruits.add(new Banana()); //error
//        fruits.add(new Apple()); // error
//        fruits.add(new Fruit()); // error

        List<Fruit> fruits = new ArrayList();
        List<Apple> apples = new ArrayList();

        Test.addDataToList(fruits);
        Test.addDataToList(apples);

    }

}

class Test1<T> {
    T data;

    public void test() {
        Test1<String> list1 = new Test1<>();
        Test1<Integer> list2 = new Test1<>();
        list1.data = "123";
//        list1.data = 1; //error

    }
}

class TestString extends Test1<String> {

}

class Test2 {
    Object data;

    public void test() {
        Test2 list1 = new Test2();
        list1.data = "123";
        list1.data = 1;
    }
}


