package synchronizedtest;

import org.example.synchronziedtest.ReentryTest;
import org.junit.jupiter.api.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class ApiTest {


    /**                                                     锁类型                                                     是否偏向锁标记   锁类型
     * 对象结构介绍                                                               61bit                                     1bit       2bit
     *                        对象头结构                      无锁     未使用25bit HashCode 31bit 未使用1bit 分代年龄4bit        0          01
     *                        mark-down 标记字段     ------>  偏向锁   当前线程指针54bit Epoch2bit 未使用1bit 分代年龄4bit        1          01
     * 1. 对象头(header) -->   Klass Point 类型指针            轻量锁   指向线程栈中lock record指针 62bit                                   00
     *                        数组对象 数组长度                重量锁    指向重量级锁的指针 62bit                                            10
     * 2. 实例数据   --->       对象实际数据                    GC标记                                                                    11
     * 3. 填充数据   --->       对齐(可选) 按8字节对齐
     *
     */
    @Test
    public void ObjectStruct() {
        System.out.println(VM.current().details());
        Object o = new Object();
        System.out.println(o + " 十六进制哈希: " + Integer.toHexString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }

    /**
     * 锁升级机制 --> monitor --> 基于C++中ObjectMonitor实现
     * synchronized 当JVM检测对象在不同的竞争状况中, 会自动切换到适合的锁去实现
     *
     */

    /**
     * 验证可重入性
     * 执行结果：都是同一个线程Id 说明这个线程拿到了多次请求到了自己持有对象锁的临界资源
     * 子类方法: ReentryTest.doA() ThreadId: 1
     * 父类方法: A.doA() ThreadId: 1
     * 子类方法: ReentryTest.doB() ThreadId: 1
     *
     * synchronized: 修饰普通方法的是锁对象是this对象, 修饰静态方式的锁对象是字节码文件对象
     */
    @Test
    public void reenTryTest() {
        ReentryTest reentryTest = new ReentryTest();
        reentryTest.doA();
    }

    /**
     * 偏向锁
     * 轻量级锁: 当锁是偏向锁时, 被另一个线程所访问, 偏向锁就会升级为轻量级锁, 其他线程会通过自旋的形式尝试获取锁
     * 自旋n次失败了 就会升级为重量级的锁
     */

}
