import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import utils.CustomPrintStream;

public class Async {
    static {
        System.setOut(new CustomPrintStream(System.out));
    }

    public static void main(String[] args) {
        Service service = new Service();
        int res = service.serve();
        System.out.println("Result is: " + res);
    }
}


class Service {
    int serve () {
        Srv srv1 = new Srv("srv1", 10000);
        Srv srv2 = new Srv("srv2", 5000);
        List<Srv> srvs = Arrays.asList(srv1, srv2);
        ExecutorService executorService = Executors.newFixedThreadPool(srvs.size());
        for (Srv srv : srvs) {
            Future<?> submit = executorService.submit(srv);
        }
        executorService.shutdown();
//        executorService.shutdownNow();
        return srvs.size();
    }
}

class Srv implements Runnable {
    String name;
    long duration;

    public Srv(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        try {
            System.out.println(name + " started by " + Thread.currentThread().getName());
            if (duration < 10000) {
                Thread.currentThread().interrupt();
            }
            Thread.sleep(this.duration);
        } catch (InterruptedException e) {
            System.err.println("Srv interrupted for Srv with duration: " + duration + "\n" + e);
        }
        System.out.println(name +  " finished by " + Thread.currentThread().getName() + " " +
            "in " + (System.currentTimeMillis() - start) +
            "ms");
    }
}