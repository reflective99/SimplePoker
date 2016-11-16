package utils;

import java.util.Random;

import main.Card;
import main.Hand;
import utils.RandGeneratorStats.Stats;
import utils.RandGeneratorStats.RandomDeckGenerator;


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
      
      Hand hand = generateOneHand();
      Stats.receiveStats(hand);
    } catch (InterruptedException e){
      e.printStackTrace();
    }
  }
  
  private synchronized Hand generateOneHand() {
      Card[] handCards = new Card[3];
      Random generator = new Random();
      for(int i = 0; i < 3; i++) {
        ; // hard code the deck values for now
        handCards[i] = RandomDeckGenerator.getCard(generator.nextInt(52));
      }
      Hand newHand = new Hand(handCards);
      RandomDeckGenerator.shuffleOneCard();
      //for(int i = 0; i < 10; i++){
       // RandomDeckGenerator.shuffleDeck();
      //}
      return newHand;
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
