package org.example.reentrantlocktest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于链表, 高性能, 公平的自旋锁
 */
public class CLHLock implements Lock {

    private ThreadLocal<CLHLock.Node> preNode;

    private ThreadLocal<CLHLock.Node> node;

    private AtomicReference<CLHLock.Node> tail = new AtomicReference<>(new CLHLock.Node());

    private static class Node {
        private volatile boolean locked;
    }

    public CLHLock() {
        preNode = ThreadLocal.withInitial(() -> null);
        node = ThreadLocal.withInitial(CLHLock.Node::new);
    }

    @Override
    public void lock() {
        final Node node = this.node.get();
        node.locked = true;
        Node preNode = tail.getAndSet(node);
        this.preNode.set(preNode);

        while (preNode.locked); // 每一个线程都需要等前一个释放才能获取锁
    }

    @Override
    public void unlock() {
        final Node node = this.node.get();
        node.locked = false;
        // 只需要处理前一个节点的原因是释放锁需要从第一个节点开始
        this.node.set(this.preNode.get());
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
