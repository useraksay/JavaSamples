package test.concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.*;

public class ExecutorDemo {
    public static void main(String[] args) {
        ExecutorService executors = Executors.newFixedThreadPool(4);
//        ExecutorService executors = Executors.newWorkStealingPool(4);
        Future<Integer>[] futures = new Future[5];
        Callable<Integer> w = new Worker();
        Instant start = Instant.now();
        try {
            for (int i = 0; i < 5; i++) {
                Future<Integer> future = executors.submit(w);
                futures[i] = future;
            }

            for (int i = 0; i < futures.length; i++) {
                try {
                    System.out.println("Result from Future " + i + "->" + futures[i].get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            executors.shutdown();
        }

        System.out.println(Duration.between(start, Instant.now()));
    }
}

class Worker implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < 5; i++) {
             sum += i;
        }
        return sum;
    }
}
