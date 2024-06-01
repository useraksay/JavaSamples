package test.concurrency;

import java.util.HashMap;
import java.util.Map;

public class HoursOfOperationResponse {
    Map<String, String> cap = new HashMap<>();
    public static void main(String[] args) {
        HoursOfOperationResponse response = new HoursOfOperationResponse();
        Map<String, String> map = new HashMap<>();
//        map.put("a", "a");
        response.cap = map;

        System.out.println(response.cap.get("b"));
    }

}


