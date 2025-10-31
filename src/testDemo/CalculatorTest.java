package testDemo;

public class CalculatorTest {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        int a = 1;
        int b = 2;

        int result = calculator.add(a, b);
        if (result == 3) {
            System.out.println("Test passed: add() method works correctly.");
        } else {
            System.out.println("Test failed: add() method returned " + result + ", expected 3.");
        }
    }
}
