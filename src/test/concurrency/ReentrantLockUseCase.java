package test.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockUseCase {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        TicketRunnable myRunnable = new TicketRunnable(lock);
        new Thread(myRunnable,"Passenger1 Thread").start();
        new Thread(myRunnable,"Passenger2 Thread").start();

    }
}

class TicketRunnable implements Runnable {
    int ticketsAvailable = 1;
    Lock lock;

    public TicketRunnable(Lock lock) {
        this.lock=lock;
    }

    public void run() {
        System.out.println("Waiting to book ticket for : " +
                Thread.currentThread().getName());

        lock.lock();
        if (ticketsAvailable > 0) {
            System.out.println("Booking ticket for : " +
                    Thread.currentThread().getName());
            //Let's say system takes some time in booking ticket
            //(here we have taken 1 second time)
            try {
                Thread.sleep(1000);
            } catch (Exception e) {}
            ticketsAvailable--;
            System.out.println("Ticket BOOKED for : " +
                    Thread.currentThread().getName());
            System.out.println("currently ticketsAvailable = " + ticketsAvailable);
        } else {
            System.out.println("Ticket NOT BOOKED for : " +
                    Thread.currentThread().getName());
        }
        lock.unlock();
    }
}