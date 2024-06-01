package test.concurrency;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class BinarySearchUsingForkJoin {
    private int[] arr = new int[100];

    public BinarySearchUsingForkJoin(){
        init();
    }

    private void init(){
        for(int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }
        Arrays.sort(arr);
    }

    public void createForkJoinPool(int search){
        ForkJoinPool forkJoinPool = new ForkJoinPool(50);
        ForkJoinSearcher searcher = new ForkJoinSearcher(this.arr, search);
        Boolean status = forkJoinPool.invoke(searcher);
        System.out.println(" Element ::" + search +" has been found in array? :: " + status );
    }

    public static void main(String[] args) {
        BinarySearchUsingForkJoin searchUsingForkJoin = new BinarySearchUsingForkJoin();
        searchUsingForkJoin.createForkJoinPool(10);
    }

}
