package test.concurrency;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.WeakHashMap;

public class abc {
    public static void main(String[] args) {
       /* const int a = 30;
        System.out.println(a);*/
        int a, b;
        for(a = 1, b = 4; a < b; a++, b--){
            System.out.println(a);
            System.out.println(b);
        }

        String s = new String("Hello");
        HashMap<Integer, String> hashMap = new HashMap<>();
        WeakHashMap<Integer, String> weakHashMap = new WeakHashMap<>();
        IdentityHashMap<Integer, String> identityHashMap = new IdentityHashMap<>();
        hashMap.put(1, s);
        weakHashMap.put(1, s);
        identityHashMap.put(1, s);
        s = null;
        System.out.println(s);
        System.out.println(weakHashMap);
        System.out.println(identityHashMap);
    }
}
