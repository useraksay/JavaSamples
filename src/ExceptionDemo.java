public class ExceptionDemo {
    public static void main(String[] args) {
        Server server = new Server();
        server.service1();
        try {
            server.service2();
        } catch (CustomizedException e) {
            e.printStackTrace();
        }
    }
}

class CustomizedException extends Exception{
    public CustomizedException(String msg){

    }
}

class CustomRuntimeException extends RuntimeException{
    public CustomRuntimeException(String msg){

    }
}


class Server{
    public void service1() throws CustomRuntimeException{

    }

    public void service2() throws CustomizedException{

    }
}