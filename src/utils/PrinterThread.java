package utils;

import utils.RandGeneratorStats.Stats;

public class PrinterThread implements Runnable {
  
  private String myName;
  private long myWaitTime;

  public PrinterThread(long waitTime, String name) {
    this.myWaitTime = waitTime;
    this.myName = name;
  }
  
  public String getName(){
    return this.myName;
  }

  @Override
  public void run() {
    printStats();
  }
  
  private void printStats() {
    try {
      Stats.requestPrintJob();
      Thread.sleep(myWaitTime);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}
