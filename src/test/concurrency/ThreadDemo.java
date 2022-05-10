package test.concurrency;

public class ThreadDemo{
    public static void main(String ar[]){
        System.out.println(hello());
    }

    private static int hello(){
        try{
            throw new RuntimeException("Exception");
        }finally{
            return 10;
        }
    }
}
