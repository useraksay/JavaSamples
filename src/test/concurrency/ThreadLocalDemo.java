package test.concurrency;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalDemo {
    public static void main(String[] args) {
        Service1 service1 = new Service1();
        service1.process();
    }
}

class Service1{
    public void process(){
        User user = new User(); // getUser(); may be from db
        user.name = "akshay";
        //instead of putting user object into map and using that map in other services to access the user
        UserData userData = new UserData();
        Map<User, String> userMap = userData.userMap;
        userMap.put(user, "1");
        // we could use ThreadContext
        UserContextHolder.holder.set(user); // Set the user object into Thread Local
        Service2 service2 = new Service2();
        service2.process();
    }
}

class Service2{
    public void process(){
        //get the user from Thread Local
        User user = UserContextHolder.holder.get();
        System.out.println(user);
        Service3 service3 = new Service3();
        service3.process();
    }
}

class Service3{
    public void process(){
        //get the user from Thread Local
        User user = UserContextHolder.holder.get();
        System.out.println(user);
        Service4 service4 = new Service4();
        service4.process();
    }
}

class Service4{
    public void process(){
        User user = UserContextHolder.holder.get();
        System.out.println(user);
        //process
        UserContextHolder.holder.remove();
    }
}

class User{
    String name;

    @Override
    public String toString(){
        return this.name;
    }
}

class UserContextHolder {
    public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class UserData {
    public Map<User, String> userMap = new ConcurrentHashMap<>();
}