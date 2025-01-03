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
            return new ArrayList<>();
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
}
