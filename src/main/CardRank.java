package main;

/**
 * The CardRank Enum contains all the Rankings
 * for each individual card in a regular deck of
 * 52 cards. The ACE can take on a value of 14 or 
 * 1 depending on the hand played.
 */
public enum CardRank {
  
  /** Represents an ACE Card's value */
  ACE(1),   
  /** Represents a TWO Card's value */
  TWO(2),   
  THREE(3), 
  FOUR(4), 
  FIVE(5), 
  SIX(6), 
  SEVEN(7), 
  EIGHT(8), 
  NINE(9), 
  TEN(10), 
  JACK(11), 
  QUEEN(12), 
  KING(13);
  
  /** The int value associated with a rank */
  private int value;
  
  private CardRank(int val) {
    value = val;
  }
  
  public int getValue() {
    return value;
  }

}
