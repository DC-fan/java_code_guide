package org.example.string;

public class StringTest {


    public static void main(String[] args) {
        // String 的类和用于存放字符串的方法都用了final修饰
        String str_01 = "abc"; // 创建了一个对象
        String str_02 = "abc" + "def"; // 编译期优化 会将两个字符串相连 创建一个对象
        String str_03 = str_01 + "def"; // 会创建StringBuilder对象

    }

}
