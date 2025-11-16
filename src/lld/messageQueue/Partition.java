package lld.messageQueue;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Partition {
    ReadWriteLock lock = new ReentrantReadWriteLock();
    private int partitionId;
    private List<Message> messageList;
    private List<Consumer> consumerList;

    public void addMessage(Message message) {
        lock.writeLock().lock();
        try {
            messageList.add(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<Message> getMessageList(int offset) {
        lock.readLock().lock();
        try {
            return Collections.singletonList(messageList.get(offset));
        } finally {
            lock.readLock().unlock();
        }
    }
}
