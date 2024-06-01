package test.concurrency;

public class ImmutableDemo {
    public static void main(String[] args) {
        String msg = "Hello!!!";
        MessageService messageService = new MessageService(msg);
        MessageService messageService1 = new MessageService(msg.toUpperCase());
        System.out.println(messageService.getMessage());
        System.out.println(messageService1.getMessage());
    }
}

