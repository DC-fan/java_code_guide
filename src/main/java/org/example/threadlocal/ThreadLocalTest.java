package org.example.threadlocal;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

public class ThreadLocalTest {

    @Test
    public void test_threadLocalHashCode() throws NoSuchFieldException, IllegalAccessException {
        for(int i = 0, lastValue = -1; i < 5; i++) {
            ThreadLocal<Object> objectThreadLocal = new ThreadLocal<>();
            Field threadLocalHashCode = objectThreadLocal.getClass().getDeclaredField("threadLocalHashCode");
            threadLocalHashCode.setAccessible(true);
            int nowValue = (int) threadLocalHashCode.get(objectThreadLocal);
            System.out.println("objectThreadLocal: " + nowValue);
            if(i > 0) {
                System.out.println("test: " + (nowValue - lastValue));
            }
            lastValue = nowValue;
        }
    }

}
