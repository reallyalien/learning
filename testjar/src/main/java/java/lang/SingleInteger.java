package java.lang;

public class SingleInteger {

    //java.lang.SecurityException: Prohibited package name: java.lang
    //java.lang是核心api包，不允许强制加载
    public static void main(String[] args) {
        System.out.println("aaaaaaaaaa");
    }
}
