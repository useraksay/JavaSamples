import java.util.ArrayList;
import java.util.List;

public class Leaky {
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        for (int i = 0; ; i++) {
            User user = new User();
            user.name = i + "abdc";
            user.age = i;
            users.add(user);
        }
    }

    static class User {
        String name;
        int age;
    }
}
