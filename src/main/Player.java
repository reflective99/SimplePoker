package main;

import java.util.List;

public class Player implements Comparable<Player>{
  
  private int myID;
  private List<Card> myHand;
  private Ranking myRanking;
  private int myTotalRank;

  public Player(int id, List<Card> hand) {   
    myID = id;
    myHand = hand;
  }
  
  public List<Card> getHand() {
    return myHand;
  }
  
  @Override 
  public String toString() {
    return "P"+myID+ " " + myHand.toString();
  }
  
  public String toStringWithRank() {
    return myID + " " + myRanking + " " + myTotalRank;
  }

  public void setRanking(Ranking ranking) {
    myRanking = ranking;
    myTotalRank = calculateTotalRank();
  }
  
  public int calculateTotalRank() {
    int result = myRanking.ordinal();
    for(Card c : myHand){
      result += c.getRankAsInt();
    }
    return result;
  }
  
  public Ranking getRanking() {
    return myRanking;
  }
  
  public int getID() {
    return myID;
  }
  
  public int getCR() {
    return myTotalRank;
  }
  
  @Override
  public int compareTo(Player other) {
    if(this.myRanking.ordinal() > other.getRanking().ordinal()) {
      return -1;
    } else if (this.myRanking.ordinal() < other.getRanking().ordinal()) {
      return 1;
    } else {
      return 0;
    }
  }

}
