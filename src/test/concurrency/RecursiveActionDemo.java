package test.concurrency;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class RecursiveActionDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add((int)(Math.random() * 100));
        }
        Instant start = Instant.now();
        Task task = new Task(list);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        forkJoinPool.invoke(task);
        Instant end = Instant.now();

        Instant seqStrt = Instant.now();
        for (int i = 0; i < list.size(); i++){
            System.out.println("Current Thread:" + Thread.currentThread() + ",Value:" + list.get(i));
        }
        System.out.println("Time taken to complete task execute sequentially ->" + Duration.between(seqStrt, Instant.now()));
        System.out.println("Time taken to complete task using Fork Join Pool ->" + Duration.between(start, end));
    }
}

class Task extends RecursiveAction{
    private List<Integer> list;

    public Task(List<Integer> list) {
        this.list = list;
    }

    @Override
    protected void compute() {
        if (list.size() >= 10) {
            int size = (list.size() / 2) + 1;
            List<Integer> leftPartition = list.subList(0, size);
            List<Integer> rightPartition = list.subList(size, list.size());
            invokeAll(new Task(leftPartition), new Task(rightPartition));
        } else {
            print();
        }
    }

    private void print() {
        for (int i = 0; i < list.size(); i++)
            System.out.println("Current Thread:" + Thread.currentThread() + ",Value:" + list.get(i));
    }
}
