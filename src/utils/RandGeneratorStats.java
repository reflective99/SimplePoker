package utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import main.Card;
import main.CardRank;
import main.CardSuit;
import main.Hand;

public class RandGeneratorStats {

  private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private static Lock readLock = readWriteLock.readLock();
  private static Lock writeLock = readWriteLock.writeLock();
  protected static boolean isDone = false;
  static CardSuit[] suits = CardSuit.values();
  static CardRank[] ranks = CardRank.values();
  
  static class RandomDeckGenerator {
    
    static Card[] myDeck;
    
    public static void generateDeck() {
      Card[] deck = new Card[suits.length * ranks.length];
      int deckSize = deck.length;

      for(int i = 0; i < ranks.length; i++){
        for(int j = 0; j < suits.length; j++){
          Card newCard = new Card(ranks[i], suits[j]);
          deck[suits.length*i + j] = newCard;
          //System.out.println(newCard);
        }
      }
      myDeck = deck;
    }
    
    public synchronized static void shuffleDeck() {
      for(int i = 0; i < myDeck.length; i++){
        int rand = i + (int) (Math.random() * (myDeck.length - i));
        Card temp = myDeck[rand];
        myDeck[rand] = myDeck[i];
        myDeck[i] = temp;
      }
    }
    
    static Random random = new Random();
    
    public synchronized static void shuffleOneCard() {
        int i = random.nextInt(52);
        int j = random.nextInt(52);
        Card temp = myDeck[i];
        myDeck[i] = myDeck[j];
        myDeck[j] = temp;
        return;
    }
    
    public synchronized static Card getCard(int index) {
      return myDeck[index];
    }
    
  }

  static class Stats {

    static BigInteger totalHearts   = BigInteger.ZERO;   static BigDecimal totalHeartsPercent   = BigDecimal.ZERO;
    static BigInteger totalSpades   = BigInteger.ZERO;   static BigDecimal totalSpadesPercent   = BigDecimal.ZERO;
    static BigInteger totalClubs    = BigInteger.ZERO;   static BigDecimal totalClubsPercent    = BigDecimal.ZERO;
    static BigInteger totalDiamonds = BigInteger.ZERO;   static BigDecimal totalDiamondsPercent = BigDecimal.ZERO;

    static BigInteger totalHandsSoFar = BigInteger.ZERO;
    static BigInteger totalCardsSoFar = BigInteger.ZERO;

    static BigInteger highCards       = BigInteger.ZERO;    static BigDecimal highCardsP        = BigDecimal.ZERO;
    static BigInteger pairs           = BigInteger.ZERO;    static BigDecimal pairsP            = BigDecimal.ZERO;
    static BigInteger flushes         = BigInteger.ZERO;    static BigDecimal flushesP          = BigDecimal.ZERO;
    static BigInteger straights       = BigInteger.ZERO;    static BigDecimal straightsP        = BigDecimal.ZERO;
    static BigInteger threeOfAKinds   = BigInteger.ZERO;    static BigDecimal threeOfAKindsP    = BigDecimal.ZERO;
    static BigInteger straightFlushes = BigInteger.ZERO;    static BigDecimal straightFlushesP  = BigDecimal.ZERO;
    
    public synchronized static void receiveStats(Hand hand) throws InterruptedException {
      totalHandsSoFar = totalHandsSoFar.add(BigInteger.ONE);
      totalCardsSoFar = totalCardsSoFar.add(BigInteger.valueOf(3));
      totalHearts     = totalHearts.add(BigInteger.valueOf(hand.getNumOfSuits(CardSuit.HEARTS)));
      totalSpades     = totalSpades.add(BigInteger.valueOf(hand.getNumOfSuits(CardSuit.HEARTS)));
      totalClubs      = totalClubs.add(BigInteger.valueOf(hand.getNumOfSuits(CardSuit.CLUBS)));
      totalDiamonds   = totalDiamonds.add(BigInteger.valueOf(hand.getNumOfSuits(CardSuit.DIAMOND)));
      switch (hand.getMyRankValue()[0]) {
        case 1:
          highCards = highCards.add(BigInteger.ONE);
          break;
        case 2:
          pairs = pairs.add(BigInteger.ONE);
          break;
        case 3:
          flushes = flushes.add(BigInteger.ONE);
          break;
        case 4:
          straights = straights.add(BigInteger.ONE);
          break;
        case 5:
          threeOfAKinds = threeOfAKinds.add(BigInteger.ONE);
          break;
        case 6:
          straightFlushes = straightFlushes.add(BigInteger.ONE);
          break;
      }
      totalSpadesPercent    = calculatePercentage( totalSpades,     totalCardsSoFar);
      totalHeartsPercent    = calculatePercentage( totalHearts,     totalCardsSoFar);
      totalSpadesPercent    = calculatePercentage( totalSpades,     totalCardsSoFar); 
      totalClubsPercent     = calculatePercentage( totalClubs,      totalCardsSoFar); 
      totalDiamondsPercent  = calculatePercentage( totalDiamonds,   totalCardsSoFar); 
      
      highCardsP            = calculatePercentage( highCards,       totalHandsSoFar);
      pairsP                = calculatePercentage( pairs,           totalHandsSoFar); 
      flushesP              = calculatePercentage( flushes,         totalHandsSoFar); 
      straightsP            = calculatePercentage( straights,       totalHandsSoFar); 
      threeOfAKindsP        = calculatePercentage( threeOfAKinds,   totalHandsSoFar); 
      straightFlushesP      = calculatePercentage( straightFlushes, totalHandsSoFar); 

    }

    public synchronized static void receiveStats(Hand[] myHands) throws InterruptedException {
      int hearts   = 0;   
      int spades   = 0;   
      int clubs    = 0;   
      int diamonds = 0;   

      int totalHands = myHands.length;
      int totalCards = myHands.length*3;

      for(int i = 0; i < myHands.length; i++) {
        hearts   += myHands[i].getNumOfSuits(CardSuit.HEARTS);
        spades   += myHands[i].getNumOfSuits(CardSuit.SPADE);
        clubs    += myHands[i].getNumOfSuits(CardSuit.CLUBS);
        diamonds += myHands[i].getNumOfSuits(CardSuit.DIAMOND);
        switch (myHands[i].getMyRankValue()[0]) {
          case 1:
            highCards = highCards.add(BigInteger.ONE);
            break;
          case 2:
            pairs = pairs.add(BigInteger.ONE);
            break;
          case 3:
            flushes = flushes.add(BigInteger.ONE);
            break;
          case 4:
            straights = straights.add(BigInteger.ONE);
            break;
          case 5:
            threeOfAKinds = threeOfAKinds.add(BigInteger.ONE);
            break;
          case 6:
            straightFlushes = straightFlushes.add(BigInteger.ONE);
            break;
        }
      }

      totalHearts = totalHearts     .add(BigInteger.valueOf( hearts     ));
      totalSpades = totalSpades     .add(BigInteger.valueOf( spades     ));
      totalClubs = totalClubs      .add(BigInteger.valueOf( clubs      ));
      totalDiamonds = totalDiamonds   .add(BigInteger.valueOf( diamonds   ));
      totalCardsSoFar = totalCardsSoFar .add(BigInteger.valueOf( totalCards ));
      totalHandsSoFar  = totalHandsSoFar .add(BigInteger.valueOf( totalHands ));
      
      totalSpadesPercent    = calculatePercentage( totalSpades,     totalCardsSoFar);
      totalHeartsPercent    = calculatePercentage( totalHearts,     totalCardsSoFar);
      totalSpadesPercent    = calculatePercentage( totalSpades,     totalCardsSoFar); 
      totalClubsPercent     = calculatePercentage( totalClubs,      totalCardsSoFar); 
      totalDiamondsPercent  = calculatePercentage( totalDiamonds,   totalCardsSoFar); 
      
      highCardsP            = calculatePercentage( highCards,       totalHandsSoFar);
      pairsP                = calculatePercentage( pairs,           totalHandsSoFar); 
      flushesP              = calculatePercentage( flushes,         totalHandsSoFar); 
      straightsP            = calculatePercentage( straights,       totalHandsSoFar); 
      threeOfAKindsP        = calculatePercentage( threeOfAKinds,   totalHandsSoFar); 
      straightFlushesP      = calculatePercentage( straightFlushes, totalHandsSoFar); 

    }

    private synchronized static BigDecimal calculatePercentage(BigInteger numerator,
        BigInteger denom) {
          if(denom.equals(BigDecimal.ZERO)){
            return BigDecimal.ZERO;
          }
          BigDecimal numer = new BigDecimal(numerator);
          BigDecimal denomin = new BigDecimal(denom);
          MathContext mc = new MathContext(8, RoundingMode.CEILING);
          BigDecimal result = numer.divide(denomin, mc);
          result = result.multiply(BigDecimal.valueOf(100)); 
          result = result.setScale(8, RoundingMode.CEILING);
          return result;
    }
    
    public static void requestPrintJob() {
      try {
        Stats.printStats();
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    private synchronized static void printStats() throws InterruptedException {
      System.out.println("\n=======================");
      System.out.println("Some Statistics: ");

      System.out.print("\t Hearts   (\u2665):\t\t"       + Stats.totalHearts);    
      System.out.print(" Percent:           "                   + Stats.totalHeartsPercent);

      System.out.println();

      System.out.print("\t Spades   (\u2660):\t\t"       + Stats.totalSpades);    
      System.out.print(" Percent:           "                   +   Stats.totalSpadesPercent);

      System.out.println();

      System.out.print("\t Clubs    (\u2663):\t\t"       + Stats.totalClubs);     
      System.out.printf(" Percent:           "                  + Stats.totalClubsPercent);

      System.out.println();

      System.out.print("\t Diamonds (\u2666):\t\t"       + Stats.totalDiamonds);  
      System.out.printf(" Percent:           "                  + Stats.totalDiamondsPercent);

      System.out.println();

      System.out.println("\t Total Cards Generated:              " + Stats.totalCardsSoFar);
      System.out.println("\t Total Hands Generated:              " + Stats.totalHandsSoFar);

      System.out.println();

      System.out.print("\t High Card Hands:          "+ Stats.highCards + "        ");  
      System.out.printf("Percent:           "                   + Stats.highCardsP);
      
      System.out.println();

      System.out.print("\t Pair Hands                "+ Stats.pairs + "        ");  
      System.out.printf("Percent:           "                   + Stats.pairsP);
      
      System.out.println();

      System.out.print("\t Flush Hands:              "+ Stats.flushes + "        ");  
      System.out.printf("Percent:           "                   + Stats.flushesP);
      
      System.out.println();

      System.out.print("\t Straight Hands:           "+ Stats.straights + "        ");  
      System.out.printf("Percent:           "                   + Stats.straightsP);
      
      System.out.println();

      System.out.print("\t Three Of A Kind Hands:    "+ Stats.threeOfAKinds + "        ");  
      System.out.printf("Percent:           "                   + Stats.threeOfAKindsP);
      
      System.out.println();

      System.out.print("\t Straight Flush Hands:     "+ Stats.straightFlushes + "        ");  
      System.out.printf("Percent:           "                   + Stats.straightFlushesP);
      System.out.println("\n=======================");
    }
  }


  private static final long NUM_RUNS = 999999999;
  private static final int NUM_THREADS = 1000;
  private static final int NUM_HANDS_TO_GENERATE = 1400;
  private static CustomThreadPoolExecutor executor;
  @SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {

    long startTime = System.currentTimeMillis();
    int threadCounter = 0;
    final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(NUM_HANDS_TO_GENERATE);
    for(int i = 0; i < 5; i++){
      threadCounter++;
      queue.add(new PrinterThread(3000, "PrinterThread-"+threadCounter));
    }
    executor = new CustomThreadPoolExecutor(NUM_THREADS, NUM_THREADS,
            10, TimeUnit.SECONDS,
            queue);
    executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {

      @Override
      public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        String name = null;
        if(r instanceof PrinterThread) {
          name = ((PrinterThread) r).getName();
        } else {
          name = ((WorkerThread) r).getName();
        }
        //System.out.println("Worker Task Rejected: " + name);
        //System.out.println("Waiting for a split second");
        try {
          Thread.sleep(200);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        //for(int i = 0; i < 10; i++){
        //  RandomDeckGenerator.shuffleDeck();
        //}
        //RandomDeckGenerator.shuffleDeck();
        //System.out.println("Trying again...");
        executor.execute(r);
      }
      
    });
    RandomDeckGenerator.generateDeck();
    executor.prestartAllCoreThreads();
    while(true) {
      threadCounter++;
      //System.out.println("Adding WorkerThread..." + threadCounter);
      executor.execute(new WorkerThread(NUM_HANDS_TO_GENERATE, "Worker-"+threadCounter));
      if(threadCounter % 1000 == 0){
        //System.out.println("Adding PrinterThread... " + threadCounter);
        executor.execute(new PrinterThread(3000, "PrinterThread-"+threadCounter));
      }
      
      if(threadCounter == NUM_RUNS){
        break;
      }
    }
//    Thread printer = new Thread(new Runnable(){
//
//      @Override
//      public void run() {
//        try {
//          while(!RandGeneratorStats.isDone && NUM_RUNS % 10000 == 0){
//            System.out.println("PRINTER PRINTING");
//            int rand = 3000 + (int) Math.random() * 5000;
//            Stats.printStats();
//            Thread.sleep(rand);
//          }
//          
//
//        } catch (InterruptedException e) {
//          System.out.println("PRINTER DONE");
//          e.printStackTrace();
//        }        
//      }
//
//    });
    //printer.setDaemon(true);
    //printer.start();
    
//    long limit = 0;
//    for(long i = limit; i < limit+100 && i < NUM_RUNS; i++){
////      if(executor instanceof ThreadPoolExecutor) {
////        System.out.println("Pool size: " + ((ThreadPoolExecutor) executor).getActiveCount());
////      }
//      Runnable worker = new WorkerThread(NUM_HANDS_TO_GENERATE);
//      executor.execute(worker);
//    }
//    Thread.sleep(3000);
    
    
    if(threadCounter == NUM_RUNS) {
      executor.shutdown();
      executor.awaitTermination(1, TimeUnit.MINUTES);
      System.out.println("All calculations done!");
      isDone = true;
    }
    

    System.out.println("\n\nPrinting Final Stats:");
    Stats.printStats();

    long endTime = System.currentTimeMillis();
    long timeElapsedInSecs = (endTime - startTime)/1000;
    System.out.println("Time Elapsed: " + timeElapsedInSecs +"s");
  }

  public static Lock requestReadLock(){
    if(readLock.tryLock()){
      return readLock;
    } else {
      return readLock;
    }
  }

  public static Lock requestWriteLock(){
    if(writeLock.tryLock()){
      return writeLock;
    } else {
      return writeLock;
    }
  }

}
