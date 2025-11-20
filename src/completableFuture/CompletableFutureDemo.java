package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import utils.CustomPrintStream;

public class CompletableFutureDemo {
  static {
    System.setOut(new CustomPrintStream(System.out));
  }
  private static final int NO_OF_THREADS = 2;
  public static void main(String[] args) throws ExecutionException, InterruptedException {
    synchronousCall();
//    asynchronousCallGetApproach();
//    asynchronousCallJoinApproach();
//    asynchronousCallAllOfApproach();
    asynchronousCallAllOfAndJoinApproach();
  }

  private static ResponseObj asynchronousCallAllOfAndJoinApproach() {
    ResponseObj responseObj = null;
    ExecutorService executorService = Executors.newFixedThreadPool(NO_OF_THREADS);
    ApiProxy apiProxy = new ApiProxy();
    long startTime = System.currentTimeMillis();
    CompletableFuture<String> api1Future = CompletableFuture.supplyAsync(() -> apiProxy.api1(
        "arg1"), executorService);
    handleFuture(api1Future);
    CompletableFuture<Integer> api2Future = CompletableFuture.supplyAsync(() -> apiProxy.api2(
        "arg2"), executorService);
    handleFuture(api2Future);

    CompletableFuture<Void> future = CompletableFuture.allOf(api1Future, api2Future);
    try {
      future.get();
      String s = api1Future.join();
      Integer i = api2Future.join();
      responseObj = new ResponseObj(s, i);
    } catch (Exception e) {
      System.out.println("Exception occurred");
    }
    while (executorService.isTerminated()) {}
    executorService.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println("ASYNC::ALLOF:JOIN:Execution Complete for API 1 API 2 Time->" + (endTime - startTime) + "ms");
    return responseObj;
  }

  private static ResponseObj asynchronousCallAllOfApproach() {
    ResponseObj responseObj = null;
    ExecutorService executorService = Executors.newFixedThreadPool(NO_OF_THREADS);
    ApiProxy apiProxy = new ApiProxy();
    long startTime = System.currentTimeMillis();
    CompletableFuture<String> api1Future = CompletableFuture.supplyAsync(() -> apiProxy.api1(
        "arg1"), executorService);
    handleFuture(api1Future);
    CompletableFuture<Integer> api2Future = CompletableFuture.supplyAsync(() -> apiProxy.api2(
        "arg2"), executorService);
    handleFuture(api2Future);

    CompletableFuture<Void> future = CompletableFuture.allOf(api1Future, api2Future);
    try {
      future.get();
      String s = api1Future.get();
      Integer i = api2Future.get();
      responseObj = new ResponseObj(s, i);
    } catch (Exception e) {
      System.out.println("Exception occurred");
    }
    while (executorService.isTerminated()) {}
    executorService.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println("ASYNC::ALLOF:Execution Complete for API 1 API 2 Time->" + (endTime - startTime) + "ms");
    return responseObj;
  }

  private static ResponseObj asynchronousCallJoinApproach() {
    ResponseObj responseObj = null;
    ExecutorService executorService = Executors.newFixedThreadPool(NO_OF_THREADS);
    ApiProxy apiProxy = new ApiProxy();
    long startTime = System.currentTimeMillis();
    CompletableFuture<String> api1Future = CompletableFuture.supplyAsync(() -> apiProxy.api1(
        "arg1"), executorService);
    handleFuture(api1Future);
    CompletableFuture<Integer> api2Future = CompletableFuture.supplyAsync(() -> apiProxy.api2(
        "arg2"), executorService);
    handleFuture(api2Future);

    //two api calls are made in parallel
    //combine the results into a pojo\
    String s = api1Future.join();
    Integer i = api2Future.join();
    responseObj = new ResponseObj(s, i);

    while (executorService.isTerminated()) {}
    executorService.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println("ASYNC::JOIN:Execution Complete for API 1 API 2 Time->" + (endTime - startTime) + "ms");
    return responseObj;
  }

  private static ResponseObj synchronousCall() {
    ApiProxy apiProxy = new ApiProxy();
    long startTime = System.currentTimeMillis();
    String s = apiProxy.api1("arg1");   //5s
    Integer i = apiProxy.api2("arg2");   //3s
    long endTime = System.currentTimeMillis();
    System.out.println("SYNC:Execution Complete for API 1 API 2 Time->" + (endTime - startTime) +
        "ms");
    return new ResponseObj(s, i);
  }

  private static ResponseObj asynchronousCallGetApproach() throws ExecutionException, InterruptedException {
    ResponseObj responseObj = null;
    ExecutorService executorService = Executors.newFixedThreadPool(NO_OF_THREADS);
    ApiProxy apiProxy = new ApiProxy();
    long startTime = System.currentTimeMillis();
    CompletableFuture<String> api1Future = CompletableFuture.supplyAsync(() -> apiProxy.api1(
        "arg1"), executorService);
    handleFuture(api1Future);
    CompletableFuture<Integer> api2Future = CompletableFuture.supplyAsync(() -> apiProxy.api2(
        "arg2"), executorService);
    handleFuture(api2Future);

    //two api calls are made in parallel
    //combine the results into a pojo\

    String s = api1Future.get();
    Integer i = api2Future.get();
    responseObj = new ResponseObj(s, i);

    while (executorService.isTerminated()) {}
    executorService.shutdown();
    long endTime = System.currentTimeMillis();
    System.out.println("ASYNC::GET:Execution Complete for API 1 API 2 Time->" + (endTime - startTime) + "ms");
    return responseObj;
  }

  private static <T> void handleFuture(CompletableFuture<T> api1Future) {
    api1Future.handle((s, throwable) -> {
      if (throwable != null) {
        System.out.println("Exception " + throwable.getMessage());
        return null;
      }
      return s;
    });
  }
}

class ResponseObj {
  private String s;
  private Integer i;

  public ResponseObj(String s, Integer i) {
    this.s = s;
    this.i = i;
  }
}


class ApiProxy {
  public String api1(String arg) {
    System.out.println("API 1 Execution started ThreadName->" + Thread.currentThread().getName());
    sleep(3000);
//    int i = 1 / 0;
    System.out.println("API 1 Execution complete");
    return "RESPONSE-API-1";
  }

  public Integer api2(String arg) {
    System.out.println("API 2 Execution  started ThreadName->" + Thread.currentThread().getName());
    sleep(5000);
    System.out.println("API 2 Execution complete");
    return 2;
  }

  public void sleep(long timeInMilli) {
    try {
      Thread.sleep(timeInMilli);
    } catch (InterruptedException e) {
      System.out.println("Exception -" + e.getMessage());
    }
  }
}
