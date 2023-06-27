package org.example.synchronziedtest;

/**
 * 证明synchronzied是可重入锁
 */
public class ReentryTest extends A{

    public synchronized void doA() {
        System.out.println("子类方法: ReentryTest.doA() ThreadId: " + Thread.currentThread().getId());
        doB();
    }

    private synchronized void doB() {
        super.doA();
        System.out.println("子类方法: ReentryTest.doB() ThreadId: " + Thread.currentThread().getId());
    }

}


class A {
    public synchronized void doA() {
        System.out.println("父类方法: A.doA() ThreadId: " + Thread.currentThread().getId());
    }
}
