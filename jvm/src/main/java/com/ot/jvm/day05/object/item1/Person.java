package com.ot.jvm.day05.object.item1;

/**
 */
public class Person {
    private String name;

    private int age;

    private String address;

    private String phone;

    public Person() {
    }

    public Person(String name) {
    }

    public Person(String name, int age, String address, String phone) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static void main(String[] args) {
        Person fame = new Person("Fame",18,null,null);
        System.out.println(fame);

        //  锁
        Person coly = new Person();
        coly.setName("coly");
        coly.setAge(18);
        coly.setAddress("长沙市");
        coly.setPhone("18615976325");
        System.out.println(coly);
        //  锁的释放
//
        System.out.println(new Builder("Coly",18).phone("18615976325").builder());
    }

    public static class Builder{
        private String name;
        private int age;
        private String address = null;
        private String phone = null;
        public Builder(String name,int age){
            this.name = name;
            this.age = age;
        }

        public Builder address(String val){
            address = val;
            return this;
        }

        public Builder phone(String val){
           phone = val;
           return this;
        }

        public Person builder(){
            return getPerson(this);
        }

        private Person getPerson(Builder builder){
            this.name = builder.name;
            this.age = builder.age;
            this.address = builder.address;
            this.phone = builder.phone;
            return new Person(name, age, address, phone);
        }
    }
}
