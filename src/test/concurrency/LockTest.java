package test.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
    private static Lock lock = new ReentrantLock();

    private static void accessResrc(){
        lock.lock();
        try {

        }catch (Exception e){

        }
        lock.unlock();
    }

    public static void main(String[] args) {

    }
}
