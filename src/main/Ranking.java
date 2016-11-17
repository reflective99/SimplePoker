package main;

/**
 * This Enum contains the Rankings 
 * of each individual Hand that a 
 * player can receive 
 */
public enum Ranking {
  
  HIGH_CARD(1),        // Ranking 1
  PAIR(2),             // Ranking 2
  FLUSH(3),            // Ranking 3
  STRAIGHT(4),         // Ranking 4
  THREE_OF_A_KIND(5),  // Ranking 5
  STRAIGHT_FLUSH(6);   // Ranking 6
  
  private int value;
  
  private Ranking(int val){
    value = val;
  }
  
  public int getValue() {
    return value;
  }
}
