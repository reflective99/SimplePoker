package utils;

import main.Hand;
import utils.RandGeneratorStats.Stats;

public class WorkerThread implements Runnable {
  
  //private RandomHandGenerator myGenerator;
  private String myName;
  private int numHands;
  
  public WorkerThread(int numHands, String name) {
    //this.myGenerator = new RandomHandGenerator(numHands);
    this.myName = name;
    this.numHands = numHands;
  }
  
  public void runGenerator(){
    try {
      //System.out.println(Thread.currentThread().getName()+" going to sleep");
      Thread.sleep(20);
      //System.out.println(Thread.currentThread().getName()+" is waking up");
      Hand[] hands = new Hand[numHands];
      RandomHandGenerator rGenerator = new RandomHandGenerator(14, 3);
      synchronized(rGenerator) {
        rGenerator.generateHands();
        hands = rGenerator.getHands();
      }
      Stats.receiveStats(hands);
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }
   
  public String getName() {
    return this.myName;
  }
  @Override
  public void run() {
    //System.out.println(Thread.currentThread().getName()+" Started.");
    runGenerator();
    //System.out.println(Thread.currentThread().getName()+" Ended. ");
  }

}
