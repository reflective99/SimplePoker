package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Ranking;

/**
 *  Hand Class contains the current hand dealt 
 *  to the {@link=Player}. It stores the Hand 
 *  as an array of {@link=Card} Objects. This 
 *  class is most essential in determining the 
 *  rankings of each {@link=Player}.
 */
public class Hand implements Comparable<Hand>{
  
  private static final boolean DEBUG = false;
  
  /** An array of Card Objects that belong to this Hand */
  private Card[] myCards;       
  /** An array of int[] which determine the ranking of this Hand 
   *  arr[0] = Special Ranking of the Hand (such as Flush, Straight, etc)
   *  arr[1] to arr[length] = values stored for breaking ties
   *  such as High Cards and Rank of Pair Cards*/
  private int[] myRankingValues;    
  /** An int denoting the size of this hand */
  private int myHandSize;
  /** An int denoting the number of Cards of different CardRanks in this Hand */
  private int numDiffRankCards;
  /** An int denoting the number of Cards of the same CardRank in this Hand */
  private int numSameRankCards;
  /** An array of booleans set to true indexed by CardRanks in this Hand */
  private boolean[] myRankCards; 
  /** The ID of the player to whom this Hand belongs */
  private int playerID;

  /**
   * An empty <m>Hand()</m> Construtor.
   */
  public Hand() {
  }

  /**
   * Hand Constructor takes in an array of Card Objects.
   * @param cards - cards dealt in this hand
   */
  public Hand(Card[] cards) {
    myHandSize = cards.length;
    myRankingValues = new int[cards.length+1];
    myCards = new Card[cards.length];
    myRankCards = new boolean[Ranking.values().length + 1];
    
    populateCards(cards);
    Arrays.sort(myCards);
    
    /** Calculates the number of cards of each CardRank in this hand
     *  and the specific number of times they appear */
    int[] ranks = new int[14];
    for(int i = 0; i < myCards.length; i++) {
      ranks[myCards[i].getRank().getValue()]++;
    }
    
    for(int i = 0; i < ranks.length; i++){
      if(ranks[i] > 0){
        numDiffRankCards++;
      }
    }
    
    /** Check if there are any cards of Repeating Rank
     *  This is later used to calculate the rankings of 
     *  the hand based on if a Hand contains a Pair, Flush, etc */
    int numSameCards = 1;
    int sameRankCards = 1;
    
    for(int i = 0; i < ranks.length; i++){
      if(ranks[i] > numSameCards){
        numSameCards = ranks[i];
        sameRankCards = i;
      }
    }
    
    numSameRankCards = numSameCards;
    
    /** 
     * Cases for checking Rankings for 
     * High Card (1)
     * Pair      (2)
     * Flush     (3) 
     */
    switch (numSameRankCards) {
      case 1 : 
        myRankCards[Ranking.HIGH_CARD.getValue()] = true;
        break;
      case 2 : 
        myRankCards[Ranking.PAIR.getValue()] = true;
        break;
      case 3 : 
        myRankCards[Ranking.THREE_OF_A_KIND.getValue()] = true;
        break;
    }
    
    /** Populate the Ranking values of
     *  this Hand when it has a 
     *  High Card (Ranking 1) */
    if(myRankCards[Ranking.HIGH_CARD.getValue()]) {
      myRankingValues[0] = Ranking.HIGH_CARD.getValue();
      myRankingValues[1] = myCards[2].getRankAsInt();
      myRankingValues[2] = myCards[1].getRankAsInt();
      myRankingValues[3] = myCards[0].getRankAsInt();
    }
    
    /** Calculate PAIR Ranking */
    if(myRankCards[Ranking.PAIR.getValue()]) {
      myRankingValues[0] = Ranking.PAIR.getValue();
      myRankingValues[1] = sameRankCards;
      myRankingValues[2] = myCards[0].getRankAsInt() == sameRankCards ? myCards[2].getRankAsInt() : myCards[0].getRankAsInt(); 
    }
    
    /** Calculate FLUSH Ranking */
    if(myCards[0].getSuit() == myCards[1].getSuit() &&
       myCards[1].getSuit() == myCards[2].getSuit()) 
    {
      myRankCards[Ranking.FLUSH.getValue()] = true;
      myRankingValues[0] = Ranking.FLUSH.getValue();
    }
      
    /** Calculate STRAIGHT Ranking */
    int straightVal = 0;
    /** Low Straight with Ace at the Bottom
     *  Hand: ACE 1 2 */
    if(ranks[CardRank.ACE.getValue()] == 1 && 
       ranks[CardRank.TWO.getValue()] == 1 && 
       ranks[CardRank.THREE.getValue()] == 1)
    {
      myRankCards[Ranking.STRAIGHT.getValue()] = true;
      /** Store the current Straight Value 
       *  to break ties */
      straightVal = CardRank.THREE.getValue();
    }
    
    /** High Straight with Ace at the Top 
     *  Hand: Queen King Ace */
    if(ranks[CardRank.QUEEN.getValue()] == 1 &&
       ranks[CardRank.KING.getValue()] == 1 &&
       ranks[CardRank.ACE.getValue()] == 1) {
      
      myRankCards[Ranking.STRAIGHT.getValue()] = true;
      /** This Hand has the Hightest Straight Value */
      straightVal = CardRank.KING.getValue() + CardRank.ACE.getValue();
    }
    
    /** If it wasn't a Low or a High Straight, check
     *  to see if there exists a straight in the rest 
     *  of the cards by looping through the ranks array */
    for(int i = 2; i < 12; i++){
      if(ranks[i] == 1 && ranks[i+1] == 1 && ranks[i+2] == 1) {
        myRankCards[Ranking.STRAIGHT.getValue()] = true;
        straightVal = i+2;
      }
    }
    
    
    /** Calculate STRAIGHT Ranking_*/
    if(myRankCards[Ranking.STRAIGHT.getValue()]) {
      myRankingValues[0] = Ranking.STRAIGHT.getValue();
      myRankingValues[1] = straightVal;
      myRankingValues[2] = straightVal-1;
      myRankingValues[3] = straightVal-2;
    }
    
    /** Update if Ranking is THREE_OF_A_KIND */
    if(myRankCards[Ranking.THREE_OF_A_KIND.getValue()]) {
      myRankingValues[0] = Ranking.THREE_OF_A_KIND.getValue();
      myRankingValues[1] = sameRankCards;
    }
    
    
    /** Update if Ranking is STRAIGHT_FLUSH */
    boolean flush = myRankCards[Ranking.FLUSH.getValue()];
    boolean straight = myRankCards[Ranking.STRAIGHT.getValue()];
    if(flush && straight) {
      
      myRankCards[Ranking.STRAIGHT_FLUSH.getValue()] = true;
      myRankingValues[0] = Ranking.STRAIGHT_FLUSH.getValue();
    }
    
    if(DEBUG) {
      System.out.println(Arrays.toString(myRankCards));
      System.out.println(Arrays.toString(myRankingValues));
    }
  }

  /**
   * This methods populates the internal array 
   * of Card Objects for this hand
   * @param cards - an <b>Array</b> of Card Objects
   */
  private void populateCards(Card[] cards) {
    for(int i = 0; i < myHandSize; i++){
      myCards[i] = cards[i];
    }
  }
  
  /** Getters and Setters for Instance Variables, Fields, 
   *  and other important information. */
  
  public List<Integer> getRankingsAsList() {
    List<Integer> rankings = new ArrayList<Integer>();
    for(int i = 0; i < myRankCards.length; i++){
      if(myRankCards[i]){
        rankings.add(i);
      }
    }
    return rankings;
    
  }
  
  public void setPlayerID(int ID){
    this.playerID = ID;
  }
  
  public int getPlayerID(){
    return this.playerID;
  }
  
  public Card[] getMyCards() {
    return myCards;
  }

  public void setMyCards(Card[] myCards) {
    this.myCards = myCards;
  }

  public int[] getMyRankValue() {
    return myRankingValues;
  }

  public void setMyRankValue(int[] myRankValue) {
    this.myRankingValues = myRankValue;
  }

  public boolean[] getMyRankings() {
    return myRankCards;
  }

  public void setMyRankings(boolean[] myRankings) {
    this.myRankCards = myRankings;
  }
  
  public int getNumOfRanks(CardRank rank) {
    int n = 0;
    for(int i = 0; i < this.myCards.length; i++){
      n = (myCards[i].getRank() == rank ? n + 1 : n);
    }
    return n;
  }
  
  public int getNumOfDiffRanks() {
    return this.numDiffRankCards;
  }
  
  public int getNumOfSuits(CardSuit suit) {
    int n = 0;
    for(int i = 0; i < this.myCards.length; i++){
      n = (myCards[i].getSuit() == suit ? n + 1 : n);
    }
    return n;
  }

  public int getMyHandSize() {
    return myHandSize;
  }

  public void setMyHandSize(int myHandSize) {
    this.myHandSize = myHandSize;
  }

  public int getNumDiffRankCards() {
    return numDiffRankCards;
  }

  public void setNumDiffRankCards(int numDiffRankCards) {
    this.numDiffRankCards = numDiffRankCards;
  }

  public int getNumSameRankCards() {
    return numSameRankCards;
  }

  public void setNumSameRankCards(int numSameRankCards) {
    this.numSameRankCards = numSameRankCards;
  }

  public boolean[] getMyRankCards() {
    return myRankCards;
  }

  public void setMyRankCards(boolean[] myRankCards) {
    this.myRankCards = myRankCards;
  }

  public void printHand() {
    System.out.print(" Player " + this.playerID + " Hand: ");
    for(int i = 0; i < myCards.length; i++){
      System.out.print(myCards[i].toString());
    }
    System.out.println();
  }
  
  @Override
  public String toString() {
    return (String.format("%-3s   %14s    Rank: %16s\n", 
        this.getPlayerID(), 
        Arrays.toString(this.getMyCards()), 
        Arrays.toString(this.getMyRankValue())));
  }
  
  /** Overridden <code>equals()</code> method */
  @Override 
  public boolean equals(Object other) {
    if(other == null) return false;
    if(this.getClass() != other.getClass()) return false;
    Hand c = (Hand) other;
    for(int i = 0; i < myHandSize; i++){
      if(!this.getMyCards()[i].equals(c.getMyCards()[i])){
        return false;
      }
    }
    return true;
  }
  
  /** Overridden <code>hashCode()</code> method */
  @Override 
  public int hashCode() {
    int prime = 51;
    int hash = 1;
    hash = (prime * hash) + Arrays.deepHashCode(myCards);
    hash = (prime * hash) + Arrays.stream(myRankingValues).sum();
    return hash; 
  }

  /** Overriden <code>compareTo(Hand o)</code> method */
  @Override
  public int compareTo(Hand o) {
    for(int i = 0; i < myRankingValues.length; i++){
      if(this.myRankingValues[i] < o.myRankingValues[i]) {
        return 1;
      } else if(this.myRankingValues[i] > o.myRankingValues[i]) {
        return -1;
      }
    }
    return 0;
  }

}