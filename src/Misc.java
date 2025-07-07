public class Misc {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        //join
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(Thread.currentThread().getName() + ":Hello World!");

//        Thread thread = new Thread(Misc::run);
//        thread.start();
//        while(true) {
//            System.out.println(Thread.currentThread().getName());
//        }
    }

    private static void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}
