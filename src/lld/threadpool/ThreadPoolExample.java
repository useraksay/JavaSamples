package lld.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

class ThreadPool {
    private static ThreadPool INSTANCE;
    private final BlockingQueue<Runnable> taskQueue;
    private final Thread[] workers;
    private final AtomicBoolean isShutDownInitiated;

    // Private Constructor for Singleton
    private ThreadPool(int numberOfThreads) {
        taskQueue = new LinkedBlockingQueue<>();
        workers = new Thread[numberOfThreads];
        isShutDownInitiated = new AtomicBoolean(false);

        for (int i = 0; i < numberOfThreads; i++) {
            workers[i] = new Worker(taskQueue, isShutDownInitiated);
            workers[i].start();
        }
    }

    public static synchronized ThreadPool getInstance(int numberOfThreads) {
        if (INSTANCE == null) {
            INSTANCE = new ThreadPool(numberOfThreads);
        }
        return INSTANCE;
    }

    // Method to submit task
    public void submitTask(Runnable task) {
        if (!isShutDownInitiated.get()) {
            taskQueue.offer(task);
        }
    }

    // Method to shut down the pool
    public void shutdown() {
        isShutDownInitiated.set(true);
        for (Thread worker : workers) {
            worker.interrupt();
        }
    }
}

class Worker extends Thread {
    private final BlockingQueue<Runnable> taskQueue;
    private final AtomicBoolean isShutDownInitiated;

    public Worker(BlockingQueue<Runnable> taskQueue, AtomicBoolean isShutDownInitiated) {
        this.taskQueue = taskQueue;
        this.isShutDownInitiated = isShutDownInitiated;
    }

    @Override
    public void run() {
        while (!isShutDownInitiated.get() || !taskQueue.isEmpty()) {
            try {
                Runnable task = taskQueue.take();
                task.run();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Task implements Runnable {
    private final String taskName;

    public Task(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public void run() {
        System.out.println("Executing task: " + taskName);
        try {
            Thread.sleep(1000); // Simulate work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Completed task: " + taskName);
    }
}

public class ThreadPoolExample {
    public static void main(String[] args) {
        ThreadPool threadPool = ThreadPool.getInstance(3);

        // Submitting tasks to ThreadPool
        for (int i = 1; i <= 10; i++) {
            Task task = new Task("Task-" + i);
            threadPool.submitTask(task);
        }

        // Shutting down the thread pool
        threadPool.shutdown();
    }
}




