package main;

/**
 * The Card Class contains the blue print for
 * a {@link=Player} Card from a Deck of 52 Cards.
 * Each Card has a {@link=CardSuit} (SPADE, DIAMOND,
 * HEARTS, CLUBS) and a {@link=CardRank} (A,1,2,3,4,5,6
 * 7,8,9,10,J,Q,K) The ACE is the highest ranking card
 * and 2 is the Lowest Ranking Card.  
 */
public class Card implements Comparable<Card>{

  private CardSuit suit;
  private CardRank rank;

  public Card(CardRank rank, CardSuit suit) {
    this.suit = suit;
    this.rank = rank;
  }

  public CardSuit getSuit() {
    return this.suit;
  }

  public void setsuit(CardSuit suit) {
    this.suit = suit;
  }

  public CardRank getRank() {
    return this.rank;
  }

  public void setMyRank(CardRank rank) {
    this.rank = rank;
  }
  
  public Integer getRankAsInt() {
    if(this.rank == CardRank.ACE) {
      return 14;
    }
    return rank.getValue();
  }
  
  @Override
  public int compareTo(Card other) {
    if(this.getRankAsInt() < other.getRankAsInt()){
      return -1;
    } else if (this.getRankAsInt() > other.getRankAsInt()){
      return 1;
    }
    return 0;
    
  }

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
      case CLUBS:
        cardString.append((char) '\u2663');
        break;
    }
    cardString.append(']');
    return cardString.toString();
  }

}
