package test.concurrency;

public class EagerSingleton {
    private static EagerSingleton INSTANCE = null;
    private EagerSingleton(){}

    public static EagerSingleton getInstance(){
        if(INSTANCE == null){
            INSTANCE = new EagerSingleton();
        }
        return INSTANCE;
    }

    public static void main(String[] args) {
        System.out.println(EagerSingleton.INSTANCE);
        System.out.println(EagerSingleton.getInstance().hashCode());
        System.out.println(EagerSingleton.INSTANCE.hashCode());
        System.out.println(EagerSingleton.getInstance().hashCode());
    }
}
