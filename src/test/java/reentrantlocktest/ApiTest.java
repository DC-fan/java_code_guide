package reentrantlocktest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantLock;

public class ApiTest {

    /**
     * 在公平锁中会判断当前线程是否同步队列的首位
     */
    @Test
    public void reentrantLockTest() {
        ReentrantLock lock = new ReentrantLock(true); // 公平锁 默认为非公平锁
        lock.lock();
        try {
            // todo something
        } finally {
            lock.unlock();
        }
    }

}
