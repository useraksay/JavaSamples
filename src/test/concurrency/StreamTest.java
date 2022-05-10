package test.concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class StreamTest {
    private static final List<String> ncpdpList = new ArrayList<>();

    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
        for(int i = 0; i < 150_000; i++){
                ncpdpList.add("" + Math.random());
        }

        Instant start = Instant.now();

        List<String> sFilteredList = ncpdpList.stream().
                map(ncpdp -> ncpdp.trim()).
                distinct().
                collect(Collectors.toList());

        Instant end = Instant.now();

        System.out.println("Seq time: " + Duration.between(start, end).toMillis() + " executing thread: " + Thread.currentThread().getName());
//        System.out.println("O/p List\n" + sFilteredList);

/*        start = Instant.now();

        List<String> pFilteredList = ncpdpList.parallelStream()
                .map(ncpdp -> ncpdp.trim())
                .distinct()
                .collect(Collectors.toList());

        end = Instant.now();

        System.out.println("Par time " + Duration.between(start, end).toMillis() + " executing thread " + Thread.currentThread().getName());
//        System.out.println("O/p List\n" + pFilteredList);

        System.out.println(sFilteredList.stream()
                .filter(element -> !pFilteredList.contains(element))
                .collect(Collectors.toList()).size());*/

        System.out.println("getParallelism=" + ForkJoinPool.commonPool().getParallelism());

    }


}
