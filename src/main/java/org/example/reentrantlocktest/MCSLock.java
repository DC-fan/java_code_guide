package org.example.reentrantlocktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 类似于CLH 但是Node有一个next节点 并且没有头节点
 * 仍然是一个公平锁
 */
public class MCSLock implements Lock {

    private ThreadLocal<MCSLock.Node> node;

    private AtomicReference<MCSLock.Node> tail = new AtomicReference<>(null);

    private static class Node {
        private volatile boolean locked;
        private volatile Node next;
    }

    @Override
    public void lock() {
        final Node node = this.node.get();
        // 此处是通过AtomicReference来保证原子性的
        Node preNode = this.tail.getAndSet(node);
        if(preNode == null) {
            node.locked = true;
            return;
        }
        node.locked = false;
        preNode.next = node;
        while (!node.locked) ; // 等前一个节点去释放这个锁
    }

    @Override
    public void unlock() {
        final Node node = this.node.get();
        if(node.next == null) {
            // 在多线程中突然加入新的线程尝试获取锁
            if(tail.compareAndSet(node, null)) {
                return;
            }
            while(node.next == null) ;
        }
        node.next.locked = true;
        node.next = null;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
