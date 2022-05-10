package test.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {
    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        MyCustomRunnable myRunnable=new MyCustomRunnable(lock);
        new Thread(myRunnable,"Thread-1").start();
        new Thread(myRunnable,"Thread-2").start();

    }
}

class MyRunnable implements Runnable{
    Lock lock;

    public MyRunnable(Lock lock) {
        this.lock=lock;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()
                +" is Waiting to acquire lock");

        lock.lock();
        System.out.println(Thread.currentThread().getName()
                +" has acquired lock.");

        try {
            Thread.sleep(5000);
            System.out.println(Thread.currentThread().getName()
                    +" is sleeping.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()
                +" has released lock.");
        lock.unlock();
    }
}
