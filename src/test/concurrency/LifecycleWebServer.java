package test.concurrency;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {
  private static final int NTHREADS = 100;
  private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

  public void start() throws IOException {
    ServerSocket socket = new ServerSocket(80);
    while (!exec.isShutdown()) {
      try {
        final Socket conn = socket.accept();
        exec.execute(new Runnable() {
          public void run() {
            handleRequest(conn);
          }
        });
      } catch (RejectedExecutionException e) {
        if (!exec.isShutdown())
          System.out.println("task submission rejected" + e);
      }
    }
  }

  public void stop() { exec.shutdown(); }

  void handleRequest(Socket connection) {
    String req = readRequest(connection);
    if (isShutdownRequest(req))
      stop();
    else
      dispatchRequest(req);
  }

  private void dispatchRequest(String req) {
    System.out.println(req);
  }

  private String readRequest(Socket connection) {
    return connection.toString();
  }

  private boolean isShutdownRequest(String req) {
    return "SHUTDOWN".equalsIgnoreCase(req);
  }
}