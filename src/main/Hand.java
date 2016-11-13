package main;

public class Hand {
  
  private Card[] myCards;

  public Hand(Card[] cards) {
    myCards = cards;
  }
  
  public Card[] getCards() {
    return this.myCards;
  }

}
