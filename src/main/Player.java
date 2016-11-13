package main;

import java.util.ArrayList;
import java.util.List;
import main.Ranking;

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
    System.out.println();
    System.out.println("calculating Rank for " + this.getID());
    System.out.println("myRanking " + myRanking.ordinal());
    int result = myRanking.ordinal();
    System.out.println(this.getID() + "s hand " + this.myHand);
    for(Card c : myHand){
      System.out.println("adding " + c.getRankAsInt() + " to " + result);
      result += c.getRankAsInt();
    }
    System.out.println("totalRank: " + result);
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
      if(this.getRanking() == Ranking.HIGH_CARD) {
        for(int i = this.getHand().size()-1; i >= 0; i--){
          if(this.getHand().get(i).getRankAsInt() > other.getHand().get(i).getRankAsInt()){
            return -1;
          } else if (this.getHand().get(i).getRankAsInt() < other.getHand().get(i).getRankAsInt()) {
            return 1;
          }
        }
      } else if(this.getRanking() == Ranking.PAIR) {
        int myPairCardRank = this.getHand().get(0) == this.getHand().get(1) ? 
            this.getHand().get(0).getRankAsInt() : this.getHand().get(2).getRankAsInt();
        int otherPairCardRank = other.getHand().get(0) == other.getHand().get(1) ? 
            other.getHand().get(0).getRankAsInt() : other.getHand().get(2).getRankAsInt();
        if(myPairCardRank > otherPairCardRank) {
          return -1;
        } else if(myPairCardRank < otherPairCardRank) {
          return 1;
        } else {
          int myRemainingCardRank = this.getHand().get(0) == this.getHand().get(1) ? 
              this.getHand().get(2).getRankAsInt() : (this.getHand().get(1) == this.getHand().get(2)) ? 
                  this.getHand().get(0).getRankAsInt() : this.getHand().get(1).getRankAsInt();
          int otherRemainingCardRank = other.getHand().get(0) == other.getHand().get(1) ? 
              other.getHand().get(2).getRankAsInt() : (other.getHand().get(1) == other.getHand().get(2)) ? 
                  other.getHand().get(0).getRankAsInt() : other.getHand().get(1).getRankAsInt();  
          if(myRemainingCardRank > otherRemainingCardRank) {
            return -1;
          } else if (myRemainingCardRank < otherRemainingCardRank) {
            return 1;
          }
        }
      } else if (this.getRanking() == Ranking.FLUSH) {
        return this.myTotalRank > other.myTotalRank ? -1 : 1;
      } else if (this.getRanking() == Ranking.STRAIGHT) {
        return this.getHand().get(0).getRankAsInt() > other.getHand().get(0).getRankAsInt() ? -1 : 1;
      } else if (this.getRanking() == Ranking.THREE_OF_A_KIND) {
        return this.myTotalRank > other.myTotalRank ? -1 : 1;
      } else if (this.getRanking() == Ranking.STRAIGHT_FLUSH) {
        return this.getHand().get(0).getRankAsInt() > other.getHand().get(0).getRankAsInt() ? -1 : 1;
      }
      return 0;
    }
  }

}
