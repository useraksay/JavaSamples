package lld.messageQueue;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    String name;
    String topic;
    Partition partition;
    AtomicInteger offset;

    public Consumer(String name, String topic, Partition partition) {
        this.name = name;
        this.topic = topic;
        this.partition = partition;
        this.offset = new AtomicInteger(0);
    }

    @Override
    public void run() {
        while (true) {
            //read from offset
            synchronized (this) {
                List<Message> messages = partition.getMessageList(offset.get());
                if (messages == null || messages.isEmpty()) {
                    continue;
                } else {
                    Iterator<Message> iterator = messages.iterator();
                    while (iterator.hasNext()) {
                        Message message = iterator.next();
                        System.out.println("message:: " + message + " consumed by consumer thread::"
                            + Thread.currentThread().getName() + " at offset:: " + offset.get());
                    }
                    offset.set(messages.size());
                }
            }
        }
    }
}
