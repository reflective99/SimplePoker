package main;

public class Card {
  
  private CardSuit suit;
  private CardRank rank;

  public Card(CardSuit suit, CardRank rank) {
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

  public void setMyrank(CardRank rank) {
    this.rank = rank;
  }
  
  public Integer getRankAsInt() {
    return rank.ordinal();
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
    return "Suit: " + this.suit.toString() + ", Rank: " + this.rank.toString();
  }

}
