package lld.readwritelock;

public class SimpleReadWriteLock {
    private int readers = 0;
    private boolean isWriting = false;

    public synchronized void lockRead() throws InterruptedException {
        while (isWriting) {
            wait(); // wait until writer releases
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        if (readers == 0) notifyAll(); // writer may proceed
    }

    public synchronized void lockWrite() throws InterruptedException {
        while (isWriting || readers > 0) {
            wait(); // wait for all readers/writers to finish
        }
        isWriting = true;
    }

    public synchronized void unlockWrite() {
        isWriting = false;
        notifyAll(); // allow waiting readers or writers
    }
}
