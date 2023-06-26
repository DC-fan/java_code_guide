package volatiletest;

public class ApiTest {

    public static void main(String[] args) {

        // ------ 通过volatile 保证可见性
        VT vt = new VT();
        Thread thread = new Thread(vt);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000l);
                } catch (Exception e) {

                }
                vt.flag = true;
                System.out.println("thread1 修改变量使得 thread 停止执行");
            }
        });

        thread.start();
        thread1.start();

        // -------- 通过sout 本质上是synchronized来保证可见性
        VTWithSout vtWithSout = new VTWithSout();
        new Thread(vtWithSout).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000l);
                } catch (Exception e) {

                }
                vtWithSout.flag = true;
                System.out.println("线程修改变量使得thread停止执行");
            }
        }).start();




    }

}


class VTWithSout implements Runnable {

    public boolean flag = false;

    @Override
    public void run() {
        while (!flag) {
            System.out.println("你好");
        }
        System.out.println("你不行");
    }
}

class VT implements Runnable {

    /**
     * 此处如果不加上volatile的话会导致进入死循环, 这是由于Java的内存机制导致的
     * volatile --> JVM提供的最轻量级的同步机制, 用于保证变量对所有线程的可见性
     *
     * 有无volatile关键字主要差别在于多了一个lock指令
     * lock指令相当于一个内存屏障
     * 1. 将本线程的缓存写入内存
     * 2. 重排序时不能将后面的指令重排序到内存屏障之前的位置
     * 3. 如果是写入动作会导致其他线程中对应的缓存无效
     *
     * 1,3 用来保证内存的可见性
     */
    public volatile boolean flag = false;

    @Override
    public void run() {
        while (!flag) {

        }
        System.out.println("你1111");
    }
}