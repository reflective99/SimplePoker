package utils;


public class FileWriterThread implements Runnable {
   
  private String myName;
  private int numHands;
  private int handSize;

  public FileWriterThread(String name, int hands, int size)
  {
    this.myName = name;
    this.numHands = hands;
    this.handSize = size;
  }
  
  public String getName(){
    return this.myName;
  }

  @Override
  public void run() {
    
    try {
      RandomHandGenerator rGenerator = new RandomHandGenerator(numHands, handSize);
      synchronized(rGenerator) {
        rGenerator.generateHands();
      }
    } finally {
      
    }
  }

}