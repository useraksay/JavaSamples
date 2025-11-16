package lld.messageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopicHandler {
    public Topic createTopic(String name,
                             int partitionSize) {
        Topic topic = new Topic();
        topic.setName(name);
        List<Partition> partitions = new ArrayList<>();
        for (int i = 0; i < partitionSize; i++) {
            Partition partition = new Partition();
            partitions.add(partition);
        }
        return null;
    }

    public void addConsumer(String consumerName,
                            Topic topic) {
        Random random = new Random();
        int partitionId = random.nextInt(getPartitions(topic));
        Consumer consumer = new Consumer();

    }

    private int getPartitions(Topic topic) {
        return topic.getPartitions().size();
    }
}
