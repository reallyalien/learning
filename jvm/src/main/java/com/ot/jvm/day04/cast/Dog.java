package com.ot.jvm.day04.cast;

public class Dog extends Animal {


    public static void main(String[] args) {
        Animal animal = new Dog();
        //java5新增的类型转换方法
        Class<Dog> dogClass = Dog.class;
        Dog dog = dogClass.cast(animal);
        System.out.println(dog);
    }
}
