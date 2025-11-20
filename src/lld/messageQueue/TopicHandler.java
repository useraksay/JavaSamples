package lld.messageQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TopicHandler {
    private static final TopicHandler INSTANCE = new TopicHandler();

    private TopicHandler() {}

    public static TopicHandler getInstance() {
        return INSTANCE;
    }

    public Topic createTopic(String name, int partitionSize) {
        Topic topic = new Topic();
        topic.setName(name);
        List<Partition> partitions = new ArrayList<>();
        for (int i = 0; i < partitionSize; i++) {
            Partition partition = new Partition(name + "-" + i, new ArrayList<>());
            partitions.add(partition);
        }
        topic.setPartitions(partitions);
        return topic;
    }

    public void addConsumer(String consumerName, Topic topic) {
        Random random = new Random();
        int partitionId = random.nextInt(topic.getPartitions().size());
        Partition partition = topic.getPartitions().get(partitionId);
    }
}
