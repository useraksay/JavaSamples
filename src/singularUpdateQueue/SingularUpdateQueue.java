package singularUpdateQueue;


import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/*
* Use a single thread to process requests asynchronously to maintain order without blocking the caller.
*
* Implement a work queue and a single thread working off the queue.
* Multiple concurrent clients can submit state changes to the queue
* */
interface Logging {}

public class SingularUpdateQueue<Req, Res> extends Thread implements Logging {
  private ArrayBlockingQueue<RequestWrapper<Req, Res>> workQueue
      = new ArrayBlockingQueue<RequestWrapper<Req, Res>>(100);
  private Function<Req, Res> handler;
  private volatile boolean isRunning = false;

  public CompletableFuture<Res> submit(Req request) {
    try {
      RequestWrapper requestWrapper = new RequestWrapper<Req, Res>(request);
      workQueue.put(requestWrapper);
      return requestWrapper.getFuture();
    }
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void run() {
    isRunning = true;
    while(isRunning) {
      Optional<RequestWrapper<Req, Res>> item = take();
      item.ifPresent(requestWrapper -> {
        try {
          Res response = handler.apply(requestWrapper.getRequest());
          requestWrapper.complete(response);

        } catch (Exception e) {
          requestWrapper.completeExceptionally(e);
        }
      });
    }
  }

  private Optional<RequestWrapper<Req, Res>> take() {
    try {
      return Optional.ofNullable(workQueue.poll(2, TimeUnit.MILLISECONDS));
    } catch (InterruptedException e) {
      return Optional.empty();
    }
  }

  public void shutdown() {
    this.isRunning = false;
  }
}

class RequestWrapper<Req, Res> {
  private final CompletableFuture<Res> future;
  private final Req request;

  public RequestWrapper(Req request) {
    this.request = request;
    this.future = new CompletableFuture<Res>();
  }

  public CompletableFuture<Res> getFuture() {
    return future;
  }

  public Req getRequest() {
    return request;
  }

  public void complete(Res response) {
    future.complete(response);
  }

  public void completeExceptionally(Exception e) {
    e.printStackTrace();
    getFuture().completeExceptionally(e);
  }
}