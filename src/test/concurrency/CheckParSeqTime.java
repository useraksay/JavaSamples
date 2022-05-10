package test.concurrency;

import java.time.Duration;
import java.time.Instant;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class CheckParSeqTime {
    private static final int start = 0;
    private static final int end = 150;

    public static void main(String[] args) {
        Instant seqStrt = Instant.now();
        IntStream.range(start, end)
                .map(num -> num * 2)
                .forEach(n -> System.out.println("Thread " + Thread.currentThread().getName() + " value: " + n));
        Instant seqEnd = Instant.now();

        System.out.println("");
        System.out.println("********************Parallelization**********************");
        System.out.println("");

        Instant parStrt = Instant.now();
        IntStream.range(start, end)
                .parallel()
                .map(num -> num * 2)
                .forEach(n -> System.out.println("Thread " + Thread.currentThread().getName() + " value: " + n));
        Instant parEnd = Instant.now();

        System.out.println("");
        System.out.println("Sequential time " + Duration.between(seqStrt, seqEnd).toMillis());
        System.out.println("Parallel time " + Duration.between(parStrt, parEnd).toMillis());
        System.out.println("");

        System.out.println("getParallelism=" + ForkJoinPool.commonPool().getParallelism());

    }
}
