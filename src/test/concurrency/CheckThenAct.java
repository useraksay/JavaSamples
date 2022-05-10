package test.concurrency;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.*;

public class CheckThenAct {
    public static final String MOVIE = "Phantom Menace";
    public static final int VIEWS = 100_000;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        Map<String, BigDecimal> movieViews = new ConcurrentHashMap<>();
        movieViews.put(MOVIE, BigDecimal.ONE);

       /* Instant startSeq = Instant.now();
        sequentialAdd(movieViews);
        System.out.println(Duration.between(startSeq, Instant.now()));*/

        Instant startConcurrency = Instant.now();
        concurrentAdd(movieViews);
        System.out.println(Duration.between(startConcurrency, Instant.now()));

        executorService.shutdown();
        try {
            while(!executorService.awaitTermination(1, TimeUnit.SECONDS));
                  System.out.println(movieViews);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private static void concurrentAdd(Map<String, BigDecimal> movieViews) {
        for (int i = 1; i < VIEWS; i++){
             executorService.submit(() -> addOneView(movieViews));
        }
    }

    public static void sequentialAdd(Map<String, BigDecimal> movieViews){
        for (int i = 1; i < VIEWS; i++){
            addOneView(movieViews);
        }
    }

    private static void addOneView(Map<String, BigDecimal> movieViews) {
        System.out.println(Thread.currentThread().getName());
        /*while (true){
            BigDecimal views = movieViews.get(MOVIE);
            if(views != null){
                if(movieViews.replace(MOVIE, views, views.add(BigDecimal.ONE))){
                   break;
                }
            }
        }*/
        //Above pattern can be written as below
        movieViews.computeIfPresent(MOVIE, (movie, views) -> views.add(BigDecimal.ONE));
    }
}
