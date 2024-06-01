package test.concurrency;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSearcher extends RecursiveTask<Boolean> {
    private int[] arr;
    private int searchableElement;

    ForkJoinSearcher(int[] arr, int search) {
        this.arr = arr;
        this.searchableElement = search;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    @Override
    protected Boolean compute() {
        int mid = (0 + arr.length) / 2;
        System.out.println(Thread.currentThread().getName() + " says : After splitting the array length is :: "+ arr.length + " Midpoint is " + mid);
        if(arr[mid] == searchableElement){
            System.out.println(" FOUND !!!!!!!!!");
            return true;
        }else if(mid == 1 || mid == arr.length){
            System.out.println("NOT FOUND !!!!!!!!!");
            return false;
        }else if (searchableElement < arr[mid]){
            System.out.println(Thread.currentThread().getName() + " says :: Doing Left-search");
            int[] left = Arrays.copyOfRange(arr,0, mid); //could be expensive
            ForkJoinSearcher forkTask = new ForkJoinSearcher(left, searchableElement);
            forkTask.fork();
            return forkTask.join();
        }else if(searchableElement > arr[mid]){
            System.out.println(Thread.currentThread().getName() + " says :: Doing Right-search");
            int[] right = Arrays.copyOfRange(arr, mid, arr.length); //could be expensive
            ForkJoinSearcher forkTask = new ForkJoinSearcher(right, searchableElement);
            forkTask.fork();
            return forkTask.join();
        }
        return false;
    }
}
