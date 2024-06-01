package test.concurrency;

public class EvenOddWithTwoThreads {
    public static int n = 11;
    private volatile int count = 1;

    public synchronized void printOdd(){
        while (count < n ){
            while(count % 2 == 0){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "->" + count);
            count++;
            notify();
        }

    }

    public synchronized void printEven(){
        while (count < n){
            while (count % 2 == 1){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "->" + count);
            count++;
            notify();
        }
    }

    public static void main(String[] args) {
        EvenOddWithTwoThreads evenOddWithTwoThreads = new EvenOddWithTwoThreads();
        new Thread(() -> evenOddWithTwoThreads.printOdd(), "OddThread").start();
        new Thread(() -> evenOddWithTwoThreads.printEven(), "EvenThread").start();
    }
}
