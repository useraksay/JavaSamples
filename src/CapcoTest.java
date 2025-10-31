public class CapcoTest {
    A a1 = new A();
    A a2 = new A();
    public static void main(String[] args) {
        CapcoTest test = new CapcoTest();
        Thread t1 = new Thread(test.a1);
        Thread t2 = new Thread(test.a2);
        t1.start();
        t2.start();
    }
}

class A implements Runnable {
    private int x = 10;

    @Override
    public void run() {
        while (x < 10000) {
            System.out.println("Value: " + x);
            x++;
        }
        System.out.println(x);
    }
}
