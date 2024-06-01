package test.concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletableFuture<Integer> future = null;

        System.out.println("Starting a process...");

        future.supplyAsync(() -> longNetworkCall(5))
                .thenAccept(val -> System.out.println("Printing " + val));

//        sleep(4000);  //Comment & uncomment and check responses.

        System.out.println("Ending a process..");

    }

    private static int longNetworkCall(int value) {
        System.out.println("running long network call in thread::" + Thread.currentThread().getName());
        sleep(2000);
        return 1;
    }

    private static void veryLongProcess(int value) {

    }


    private static void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
