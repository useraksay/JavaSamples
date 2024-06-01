package test.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSortConcurrently {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();
        nums.add(8);
        nums.add(9);
        nums.add(0);
        nums.add(1);
        nums.add(2);
        nums.add(4);
        nums.add(5);

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<List<Integer>> sortedListFuture = executorService.submit(new ArraySorter(nums));

        try {
            List<Integer> sortedList = sortedListFuture.get();
            System.out.println(sortedList);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        while(!executorService.isTerminated()){
            executorService.shutdown();
            break;
        }

    }
}


class ArraySorter implements Callable<List<Integer>> {
    private List<Integer> arrayToSort;

    public ArraySorter(List<Integer> arrayToSort) {
        this.arrayToSort = arrayToSort;
    }

    @Override
    public List<Integer> call() throws Exception {
        if (this.arrayToSort.size() < 2) {
            return arrayToSort;
        }

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        int mid = this.arrayToSort.size() / 2;

        for (int i = 0; i < mid; i++) {
            leftList.add(arrayToSort.get(i));
        }

        for (int i = mid; i < this.arrayToSort.size(); i++) {
            rightList.add(arrayToSort.get(i));
        }

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<List<Integer>> leftSortedList = executorService.submit(new ArraySorter(leftList));
        Future<List<Integer>> rightSortedList = executorService.submit(new ArraySorter(rightList));

        List<Integer> leftSortedArray = leftSortedList.get();
        List<Integer> rightSortedArray = rightSortedList.get();

        while(!executorService.isTerminated()){
            executorService.shutdown();
            break;
        }

        List<Integer> sortedList = new ArrayList<>();

        int i = 0, j = 0;

        while (i < leftSortedArray.size() && j < rightSortedArray.size()) {
            if (leftSortedArray.get(i) < rightSortedArray.get(j)) {
                sortedList.add(leftSortedArray.get(i));
                i++;
            } else {
                sortedList.add(rightSortedArray.get(j));
                j++;
            }
        }

        while (i < leftSortedArray.size()) {
               sortedList.add(leftSortedArray.get(i));
               i++;
        }

        while (j < rightSortedArray.size()) {
               sortedList.add(rightSortedArray.get(j));
               j++;
        }

        return sortedList;
    }
}
