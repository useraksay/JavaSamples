package test.concurrency;

public class InfiniteConstructor {

    public InfiniteConstructor(){
        new InfiniteConstructor();
    }

    public static void main(String[] args) {
        InfiniteConstructor test = new InfiniteConstructor();
    }
}
