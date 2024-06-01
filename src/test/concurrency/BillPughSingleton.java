package test.concurrency;

/*
* Thread safe and efficient way of creating singleton object
* */
public class BillPughSingleton {
    private BillPughSingleton(){}

    //static inner class holds singleton object
    private static class SingletonHolder{
       private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance(){
        return SingletonHolder.INSTANCE;
    }

    //When getInstance is called SingletonHolder is initialized, it also holds static object of the 'singleton' class
    //and that object is returned. This also works as lazy loading.

}
