import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplitBatch {
    public static void main(String[] args) {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int batchSize = 3;
        System.out.println(splitBatch(l, batchSize));

    }

    private static <T> List<T> splitBatch(List<T> list, int batchSize) {
        List<T> batches = new ArrayList<>();
        int numBatches = (list.size() + batchSize - 1) / batchSize;
        for (int i = 0; i < numBatches; i++) {
            int start = i * batchSize;
            int end = Math.min(list.size(), (i + 1) * batchSize);
            batches.add((T) list.subList(start, end));
        }
        return batches;
    }
}
