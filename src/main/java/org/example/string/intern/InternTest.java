package org.example.string.intern;

public class InternTest {

    public static void main(String[] args) {
        String str1 = new String("ab");
        String str2 = new String("ab");
        String str3 = "ab";

        // 使用intern会将值推进到常量池

        System.out.println(str1 == str2); // false 两个new出来的对象地址肯定不同
        System.out.println(str1 == str2.intern()); // false
        System.out.println(str1.intern() == str2.intern()); // true 将两个值都做了该操作 比对的是常量池的值
        System.out.println(str1 == str3); // false
        System.out.println(str1.intern() == str3); // true JVM编译器做了优化 不能重新创建对象

    }

}
