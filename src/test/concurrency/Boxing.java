package test.concurrency;

public class Boxing {
    public static void main(String[] args) {
        Integer integer = new Integer(10);
        Long aLong = new Long(100);
        Boxing boxing = new Boxing();
        boxing.m1(integer);
//        boxing.m2(new Long(100));
    }

    public void m1(int i){
        System.out.println("printing primitive int: " + i);
    }

    public void m1(Integer i){
        System.out.println("printing Integer: " + i);
    }

    public void m2(Float f){
        System.out.println("printing float: " + f);
    }

    /*public void m2(Integer i){
        System.out.println("printing Integer: " + i);
    }*/
}
