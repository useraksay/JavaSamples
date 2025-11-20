package lld.messageQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Partition {
    ReadWriteLock lock = new ReentrantReadWriteLock();
    private String partitionId;
    private List<Message> messageList;
    private List<Consumer> consumerList;

    public Partition(String partitionId, List<Message> messageList) {
        this.partitionId = partitionId;
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        lock.writeLock().lock();
        try {
            messageList.add(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Message> getMessageList(int offset) {
//        System.out.println("offset = " + offset);
//        lock.readLock().lock();
        try {
            if (messageList != null && messageList.size() > 0) {
                if (offset >= 0 && offset < messageList.size()) {
                    return Collections.singletonList(messageList.get(offset));
                }
            } else {
                return new ArrayList<>();
            }
        } finally {
//            lock.readLock().unlock();
        }
        return new ArrayList<>();
    }

    public String getPartitionId() {
        return this.partitionId;
    }
}
