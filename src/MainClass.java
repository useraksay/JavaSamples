import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MainClass {
    private Timer timer = new Timer();
    private Map<Long, UUID> map = new ConcurrentHashMap<>();

    public Timer getTimer() {
        return timer;
    }

    public Map<Long, UUID> getMap() {
        return this.map;
    }

    public static void main(String[] args) {
        /*System.out.println("Java 8 timer.\nexamples");
        MainClass aClass = new MainClass();
        Task task = new Task(aClass.getMap());
        //start the scheduler
        aClass.getTimer().scheduleAtFixedRate(task, 1000, 1000);
        // new login happens every 1 second store id
        boolean flag = false;
        while(!flag){
           long now = new Date().getTime();
           UUID uuid = UUID.randomUUID();
           System.out.println(now);
           aClass.getMap().put(now, uuid);
           try {
               Thread.sleep(1000);
           }catch (InterruptedException e) {
               e.printStackTrace();
           }
           //flag = true;
           System.out.println("Main class map size " + aClass.getMap().size());
        }*/

        ExceptionTest exceptionTest = new ExceptionTest();
        exceptionTest.divideByZero("1", "0");

    }
}
