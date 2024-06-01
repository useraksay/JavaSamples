package retry;

public class RetryStack {
  public static void main(String[] args) {
//    scheduleJob(3);
    scheduler(3);
  }


  private static void scheduleJob(int retryCount) {
    if (retryCount != 0 && runJob(retryCount).equals(JobStatus.FAIL)) {
      scheduleJob(--retryCount);
    }
  }

  private static void scheduler(int retryCount) {
    JobStatus jobStatus = JobStatus.FAIL;
    while (retryCount > 0 && jobStatus.equals(JobStatus.FAIL)) {
      jobStatus = runJob(retryCount);
      retryCount = retryCount - 1;
    }
  }

  private static JobStatus runJob(int retry) {
    JobStatus jobStatus;
    jobStatus = JobStatus.PASS;
    jobStatus = JobStatus.FAIL;
    System.out.println("....running job....for " + retry + " time");
    return jobStatus;
  }

  private enum JobStatus {
    PASS,
    FAIL
  }
}
