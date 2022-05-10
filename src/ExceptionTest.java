public class ExceptionTest {
    public void divideByZero(String a, String b){
        try{
            int i = Integer.parseInt(a) / Integer.parseInt(b);
        }catch (ArithmeticException ae){
            System.out.println("ArithmeticException " + ae);
        }catch (NumberFormatException nfe){
            System.out.println("NumberFormatException " + nfe);
        }catch (Exception e){
            System.out.println("Exception " + e);
        }
    }

    public void test() throws Exception {
        throw new Exception();
    }

    public void test2() throws CustomException {
        throw new CustomException();
    }
}

class CustomException extends Exception{
    CustomException(){
        super();
    }
}
