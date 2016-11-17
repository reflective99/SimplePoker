package main;

/**
 * <h1>The Card Class</h1> This class contains the blue print for a
 * <b>{@link Player}'s</b> <b>Card</b> from a deck of 52 stand cards.
 * <p>
 * Each <b>Card</b> has a {@link CardSuit Suit} and a {@link CardRank Rank} as
 * follows:
 * <p>
 * <b>Suits</b>: {@link CardSuit#SPADE Spade} {@link CardSuit#HEARTS Heart}
 * {@link CardSuit#DIAMOND Diamond} {@link CardSuit#CLUB Club}
 * <p>
 * <b>Ranks:</b> {@link CardRank#ACE ACE} {@link CardRank#TWO 2}
 * {@link CardRank#THREE 3} {@link CardRank#FOUR 4} {@link CardRank#FIVE 5}
 * {@link CardRank#SIX 6} {@link CardRank#SEVEN 7} {@link CardRank#EIGHT 8}
 * {@link CardRank#NINE 9} {@link CardRank#TEN 10} {@link CardRank#JACK JACK}
 * {@link CardRank#QUEEN QUEEN} {@link CardRank#KING KING}
 * <p>
 */
public class Card implements Comparable<Card>{

  private CardSuit suit;
  private CardRank rank;

  /**
   * Constructor for a Card Object. 
   * @param rank
   * @param suit
   */
  public Card(CardRank rank, CardSuit suit) {
    this.suit = suit;
    this.rank = rank;
  }
  
  /** Gets the Suit of a Card  */
  public CardSuit getSuit() {
    return this.suit;
  }
  
  /** Sets the Suit of a Card */
  public void setsuit(CardSuit suit) {
    this.suit = suit;
  }
  
  /** Gets the Rank of a Card */
  public CardRank getRank() {
    return this.rank;
  }
  
  /** Sets the Rank of a Card */
  public void setMyRank(CardRank rank) {
    this.rank = rank;
  }
  
  /** Gets the Rank of a Card as an int value */
  public Integer getRankAsInt() {
    if(this.rank == CardRank.ACE) {
      return 14;
    }
    return rank.getValue();
  }
  
  /** Compares two Card Objects based on their Rank */
  @Override
  public int compareTo(Card other) {
    if(this.getRankAsInt() < other.getRankAsInt()){
      return -1;
    } else if (this.getRankAsInt() > other.getRankAsInt()){
      return 1;
    }
    return 0;
    
  }
  
  /** Returns whether two Card Objects are equal to each 
   *  by checking if their Rank and the Suit are the Same.*/
  @Override 
  public boolean equals(Object other) {
    if(other == null) return false;
    if(this.getClass() != other.getClass()) return false;
    Card c = (Card) other;
    return this.rank.equals(c.getRank()) && this.suit.equals(c.getSuit());
  }
  
  @Override 
  public int hashCode() {
    int prime = 51;
    int hash = 1;
    hash = (prime * hash) + (rank == null ? 0 : String.valueOf(rank.ordinal()).hashCode());
    hash = (prime * hash) + (suit == null ? 0 : String.valueOf(suit.ordinal()).hashCode());
    return hash; 
  }
  
  
  @Override 
  public String toString() {
    StringBuilder cardString = new StringBuilder();
    int value = this.getRankAsInt();
    if(value == 14){
      cardString.append("A");
    } else if (value == 13) {
      cardString.append("K");
    } else if (value == 12) {
      cardString.append("Q");
    } else if (value == 11) {
      cardString.append("J");
    } else if (value == 10) {
      cardString.append("T");
    } else {
      cardString.append(Integer.toString(value));
    }
    if(this.getSuit().ordinal() == 0){
      cardString.append("s");
    } else if (suit.ordinal() == 1) {
      cardString.append("d");
    } else if (suit.ordinal() == 2) {
      cardString.append("c");
    } else {
      cardString.append("h");
    }
    return cardString.toString();
  }
  
  public String toUnicodeString() {
    StringBuilder cardString = new StringBuilder();
    cardString.append("[");
    int value = this.getRankAsInt();
    if(value == 14){
      cardString.append("Ace");
    } else if (value == 13) {
      cardString.append("King");
    } else if (value == 12) {
      cardString.append("Queen");
    } else if (value == 11) {
      cardString.append("Jack");
    } else {
      cardString.append(value);
    }
    switch(this.getSuit()) {
      case SPADE:
        cardString.append((char) '\u2660');         
        break;
      case DIAMOND:
        cardString.append((char) '\u2666');
        break;
      case HEARTS:
        cardString.append((char) '\u2665');
        break;
      case CLUB:
        cardString.append((char) '\u2663');
        break;
    }
    cardString.append(']');
    return cardString.toString();
  }

}
