package test.concurrency;

public class SharedUnboundedQueue {
  class Node {
    Object task;
    Node next;

    public Node(Object task) {
      this.task = task;
      this.next = null;
    }
  }

  private Node head = new Node(null);
  private Node last = head;
  private int w; //count to hold number of waiting threads
  private Object putLock = new Object();
  private Object takeLock = new Object();

  public void put(Object task) {
      synchronized (putLock) {
      assert task != null : "cannot insert null task";
      Node p = new Node(task);
      last.next = p;
        if (w > 0) {
          //if number of waiting threads greater than 0 only then call notify
          putLock.notify();
        }
      }
  }

  public Object take() {
      Object task = null;
      synchronized (takeLock) {
        while (isEmpty()) {
          try {
            synchronized (putLock) {
              w++;
              putLock.wait();  //if empty wait till queue becomes non-empty
              w--;
            }
          } catch (InterruptedException e) {
            assert false;
          }
        }
      }
      return task;
  }

  private boolean isEmpty() {
    return head.next == null;
  }
}
