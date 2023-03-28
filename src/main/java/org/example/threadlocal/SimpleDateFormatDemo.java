package org.example.threadlocal;

import jdk.jfr.StackTrace;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 验证 SimpleDateFormat 是线程不安全的
 */
public class SimpleDateFormatDemo {

    private static SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 使用ThreadLocal优化这段代码
    private static ThreadLocal<SimpleDateFormat> threadLocal = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    );

    public static void main(String[] args) {
        while (true) {
            new Thread(() -> {
//                String dateStr = f.format(new Date());
                String dateStr = threadLocal.get().format(new Date());
                try {
                    Date parseDate = threadLocal.get().parse(dateStr);
                    String dateStrCheck = threadLocal.get().format(parseDate);
                    boolean equals = dateStrCheck.equals(dateStr);
                    if(!equals) {
                        System.out.println(false + " " + dateStr + " " + dateStrCheck);
                    } else {
                        System.out.println(true);
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }).start();
        }
    }

    private static final int HASH_INCREMENT = 0x61c88647;

    @Test
    public void test_idx() {
        int hashCode = 0;
        for(int i = 0; i < 16; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;
            int idx = hashCode & 15;
            System.out.println("斐波那契数列: " + idx + "普通散列： " + (String.valueOf(i).hashCode() & 15));
        }
    }
}
