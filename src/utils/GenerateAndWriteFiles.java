package utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GenerateAndWriteFiles {
  
  private static final int NUM_CARDS = 3;
  private static final int NUM_THREADS = 1000;
  private static CustomThreadPoolExecutor executor;
  
  public static void main(String[] args) throws InterruptedException {
    
    long startTime = System.currentTimeMillis();
    int threads = 0;
    final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(1);
    executor = new CustomThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS, queue);
    executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {

      @Override
      public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        String name = ((FileWriterThread) r).getName();
        System.out.println("Worker Task Rejected: " + name);
        System.out.println("Waiting for a split second");
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        System.out.println("Trying again...");
        executor.execute(r);
      }
      
    });
    System.out.println("Adding FileWriterThread...");
    while(true) {
      threads++;
      int randHands = (2 + (int) (Math.random() * NUM_THREADS));
      System.out.println("Adding FileWriterThread..." + threads);
      executor.execute(new FileWriterThread("FileWriterThread-"+threads, randHands, NUM_CARDS));
      if(threads == NUM_THREADS){
        break;
      }
    }
    if(threads == NUM_THREADS) {
      executor.shutdown();
      executor.awaitTermination(1, TimeUnit.MINUTES);
      System.out.println("All calculations done!");
    }
    long endTime = System.currentTimeMillis();
    long timeElapsed = (endTime - startTime) / 1000;
    System.out.println("Total time taken: " + timeElapsed +"s");
  }
}
