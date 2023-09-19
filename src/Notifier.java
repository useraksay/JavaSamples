import java.util.Timer;
import java.util.TimerTask;

public class Notifier {
  Timer timer;

  public Notifier(int seconds) {
    timer = new Timer();
    timer.schedule(new RemindTask1(), seconds*1000); // schedule the task
    timer.schedule(new RemindTask2(), seconds*1000); // schedule the task
  }

  class RemindTask1 extends TimerTask {
    public void run() {
      System.out.println("Task running in Thread -" + Thread.currentThread().getName());
      System.out.println("You have a notification!");
      try {
        Thread.sleep(5000L);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      timer.cancel(); //Terminate the timer thread
    }
  }

  class RemindTask2 extends TimerTask {
    public void run() {
      System.out.println("Task running in Thread -" + Thread.currentThread().getName());
      System.out.println("You have a notification!");
//      timer.cancel(); //Terminate the timer thread
    }
  }

  public static void main(String args[]) {
    new Notifier(5);
  }
}