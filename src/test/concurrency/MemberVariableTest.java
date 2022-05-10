package test.concurrency;

public class MemberVariableTest {
    public int i = 1;

    public MemberVariableTest(int i){
        i = i;
    }

    public static void main(String[] args) {
        int x = new MemberVariableTest(10).i;
        System.out.println(x);
    }
}
