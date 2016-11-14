package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Ranking;

public class Hand implements Comparable<Hand>{
  
  private static final boolean DEBUG = true;
  
  private Card[] myCards;
  private int[] myRankValue;
  private boolean[] myRankings; // contains true values for Rankings
  
  public Hand(Card[] cards) {
    
    myRankValue = new int[4];
    myCards = new Card[3];
    myRankings = new boolean[Ranking.values().length + 1];
    
    for(int i = 0; i < 3; i++){
      myCards[i] = cards[i];
    }
    
    int[] ranks = new int[14];
    for(int i = 0; i < myCards.length; i++) {
      ranks[myCards[i].getRank().getValue()]++;
    }
    
    if(DEBUG == true) {
      System.out.println(Arrays.toString(ranks));
    }
    
    int numSameCards = 1;
    int sameCardsRank = 1;
    
    for(int i = 0; i < ranks.length; i++){
      if(ranks[i] > numSameCards){
        numSameCards = ranks[i];
        sameCardsRank = i;
      }
    }
    
    System.out.println("numSameCards: " + numSameCards);
    System.out.println("sameCardsRank: " + sameCardsRank);
    
    switch (numSameCards) {
      case 1 : 
        myRankings[Ranking.HIGH_CARD.getValue()] = true;
        break;
      case 2 : 
        myRankings[Ranking.PAIR.getValue()] = true;
        break;
      case 3 : 
        myRankings[Ranking.THREE_OF_A_KIND.getValue()] = true;
        break;
    }
    
    /** */
    if(myRankings[Ranking.HIGH_CARD.getValue()]) {
      myRankValue[0] = Ranking.HIGH_CARD.getValue();
      myRankValue[1] = myCards[2].getRankAsInt();
      myRankValue[2] = myCards[1].getRankAsInt();
      myRankValue[3] = myCards[0].getRankAsInt();
    }
    
    /** If numSameCards == 2, we have a PAIR Ranking */
    if(myRankings[Ranking.PAIR.getValue()]) {
      myRankValue[0] = Ranking.PAIR.getValue();
      myRankValue[1] = sameCardsRank;
      myRankValue[2] = myCards[0].getRankAsInt() == sameCardsRank ? myCards[2].getRankAsInt() : myCards[0].getRankAsInt(); 
    }
    
    /** Calculate FLUSH Ranking */
    if(myCards[0].getSuit() == myCards[1].getSuit() &&
        myCards[1].getSuit() == myCards[2].getSuit()) {
      myRankings[Ranking.FLUSH.getValue()] = true;
      myRankValue[0] = Ranking.FLUSH.getValue();
    }
      
    /** Calculate STRAIGHT Ranking */
    
    int straightVal = 0;
    /** Low Straight with Ace at the Bottom */
    if(ranks[CardRank.ACE.getValue()] == 1 && 
       ranks[CardRank.TWO.getValue()] == 1 && 
       ranks[CardRank.THREE.getValue()] == 1)
    {
      myRankings[Ranking.STRAIGHT.getValue()] = true;
      straightVal = CardRank.THREE.getValue();
    }
    
    /** High Straight with Ace at the Top */
    if(ranks[CardRank.QUEEN.getValue()] == 1 &&
        ranks[CardRank.KING.getValue()] == 1 &&
        ranks[CardRank.ACE.getValue()] == 1) {
      myRankings[Ranking.STRAIGHT.getValue()] = true;
      straightVal = CardRank.KING.getValue() + CardRank.ACE.getValue();
    }
    
    /** If it wasn't a Low or a High Straight, check
     *  to see if there exists a straight in the rest 
     *  of the cards */
    for(int i = 2; i < 12; i++){
      if(ranks[i] == 1 && ranks[i+1] == 1 && ranks[i+2] == 1) {
        myRankings[Ranking.STRAIGHT.getValue()] = true;
        straightVal = i+2;
      }
    }
    
    
    /** Update if it is a STRAIGHT_*/
    if(myRankings[Ranking.STRAIGHT.getValue()]) {
      myRankValue[0] = Ranking.STRAIGHT.getValue();
      myRankValue[1] = straightVal;
      myRankValue[2] = straightVal-1;
      myRankValue[3] = straightVal-2;
    }
    
    /** Update if Ranking is THREE_OF_A_KIND */
    if(myRankings[Ranking.THREE_OF_A_KIND.getValue()]) {
      myRankValue[0] = Ranking.THREE_OF_A_KIND.getValue();
      myRankValue[1] = sameCardsRank;
    }
    
    
    /** Update if Ranking is STRAIGHT_FLUSH */
    boolean flush = myRankings[Ranking.FLUSH.getValue()];
    boolean straight = myRankings[Ranking.STRAIGHT.getValue()];
    if(flush && straight) {
      
      myRankings[Ranking.STRAIGHT_FLUSH.getValue()] = true;
      myRankValue[0] = Ranking.STRAIGHT_FLUSH.getValue();
    }
    
    if(DEBUG) {
      System.out.println(Arrays.toString(myRankings));
      System.out.println(Arrays.toString(myRankValue));
    }
  }
  
  public List<Integer> getRankingsAsList() {
    List<Integer> rankings = new ArrayList<Integer>();
    for(int i = 0; i < myRankings.length; i++){
      if(myRankings[i]){
        rankings.add(i);
      }
    }
    return rankings;
    
  }
  public Card[] getMyCards() {
    return myCards;
  }


  public void setMyCards(Card[] myCards) {
    this.myCards = myCards;
  }


  public int[] getMyRankValue() {
    return myRankValue;
  }


  public void setMyRankValue(int[] myRankValue) {
    this.myRankValue = myRankValue;
  }


  public boolean[] getMyRankings() {
    return myRankings;
  }


  public void setMyRankings(boolean[] myRankings) {
    this.myRankings = myRankings;
  }


  public void printHand() {
    for(int i = 0; i < myCards.length; i++){
      System.out.println(myCards[i].toString());
    }
  }

  @Override
  public int compareTo(Hand o) {
    for(int i = 0; i < myRankValue.length; i++){
      if(this.myRankValue[i] < o.myRankValue[i]) {
        return 1;
      } else if(this.myRankValue[i] > o.myRankValue[i]) {
        return -1;
      }
    }
    return 0;
  }
  
  

}
