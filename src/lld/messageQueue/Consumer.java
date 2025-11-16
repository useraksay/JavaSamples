package lld.messageQueue;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements Runnable {
    String consumerId;
    String topic;
    Partition partition;
    AtomicInteger offset;

    public Consumer() {

    }

    public Consumer(String consumerId, String topic, Partition partition) {
        this.consumerId = consumerId;
        this.topic = topic;
        this.partition = partition;
    }

    public void consume() {
        while (true) {
            //read from offset
            partition.getMessageList(offset.getAndIncrement());
        }
    }

    @Override
    public void run() {

    }


}
