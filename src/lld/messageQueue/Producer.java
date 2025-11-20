package lld.messageQueue;

import java.util.Random;

public class Producer {
    private String name;
    private Topic topic;
    private Random random = new Random();

    public Producer(String name, Topic topic) {
        this.name = name;
        this.topic = topic;
    }

    public void publish(Message message) {
        int partitionIdx = random.nextInt(this.topic.getPartitions().size());
        Partition partition = this.topic.getPartitions().get(partitionIdx);
        partition.addMessage(message);
        System.out.println("Timestamp: "+ System.currentTimeMillis() +
            " Publisher " + this.name + " published message to " + this.topic.getName() +
            " Partition " + partition.getPartitionId());
    }
}
