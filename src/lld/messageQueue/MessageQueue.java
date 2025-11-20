package lld.messageQueue;

import java.util.Iterator;

public class MessageQueue {
    public static void main(String[] args) {
        TopicHandler topicHandler = TopicHandler.getInstance();
        Topic topic1 = topicHandler.createTopic("t-1", 3);
        Message m1 = new Message("message1");
        Producer producer = new Producer("p1", topic1);
        Iterator<Partition> iterator = topic1.getPartitions().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Partition partition = iterator.next();
            String name = "c-1-t-1";
            Consumer consumer = new Consumer(name + "-" + i, "t-1", partition);
            Thread thread = new Thread(consumer);
            thread.setName("Consumer-"+ i);
//            System.out.println("starting consumer = " + consumer.name);
            thread.start();
            i++;
        }
        producer.publish(m1);
    }
}
