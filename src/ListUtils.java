import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListUtils {
    /**
     * Find duplicate items in the list
     * @param list
     * @return
     * @param <T>
     */
    public static <T> List<T> findDuplicateItems(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }
        Set<T> seen = new HashSet<>();
        Set<T> duplicates = new HashSet<>();

        for (T t : list) {
            if (!seen.add(t)) {
                duplicates.add(t);
            }
        }
        if (!duplicates.isEmpty()) {
            return new ArrayList<>(duplicates);
        }
        return new ArrayList<>();
    }

    public static <T extends Comparable<T>> T findMax(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List must not be null or empty");
        }

        T max = list.get(0);
        for (T element : list) {
            if (element.compareTo(max) > 0) {
                max = element;
            }
        }
        return max;
    }
}
