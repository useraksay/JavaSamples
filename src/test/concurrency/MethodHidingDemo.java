package test.concurrency;

public class MethodHidingDemo {
    public static void main(String[] args) {
        Demo d1 = new Demo();
        Demo d2 = new Sample();

        d1.doDemo();   // Inside Demo (reason: d1 is of type Demo & calls doDemo of Demo class)
        d2.doDemo();   // Inside Demo (reason: d2 is of type Demo and the doDemo method in Sample class is hidden)

        Sample s1 = new Sample();
        s1.doDemo();   // Inside Sample (reason: s1 is of type Sample)

        System.out.println(d1.send());
        System.out.println(d2.send(10));
//        System.out.println(d2.send(10, 10)); //compile time error cannot resolve method send(10, 10)
    }
}

class Demo{
    public static void doDemo(){
        System.out.println("Inside Demo");
    }

    public int send(){
        return 0;
    }

    public int send(int x){   // This is method overloading, same method with same return type and different arguments in the same class/ not in subclass
        return x;
    }
}

class Sample extends Demo{
    public static void doDemo(){
        System.out.println("Inside Sample");
    }

    public int send(int x, int y){   // This is not method overloading send is a method belonging to Sample class only
        return x + y;
    }
}
