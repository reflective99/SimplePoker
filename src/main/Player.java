package main;

import java.util.List;
import main.Ranking;

public class Player implements Comparable<Player>{

  private int myID;
  private Hand myHand;
  private Ranking myRanking;

  public Player(int id, List<Card> hand) {   
    myID = id;
    myHand = new Hand(hand.toArray(new Card[3]));
  }

  public Hand getHand() {
    return myHand;
  }

  @Override 
  public String toString() {
    return "P"+myID+ " " + myHand.toString();
  }

  public String toStringWithRank() {
    return myID + " " + myRanking + " " +myRanking;
  }

  public Ranking getRanking() {
    return myRanking;
  }

  public int getID() {
    return myID;
  }

  @Override
  public int compareTo(Player other) {
    return this.myHand.compareTo(other.myHand);
  }

  //    /** Comparisons for player hands are made here */
  //    
  //    /** If myRanking is greater than other player's ranking, I win */
  //    if(this.myRanking.ordinal() > other.getRanking().ordinal()) {
  //      return -1;
  //      
  //    /** If myRanking is lower than other player's ranking, then he wins */
  //    } else if (this.myRanking.ordinal() < other.getRanking().ordinal()) {
  //      return 1;
  //      
  //    /** This means that both the player hands 
  //     * have the same ranking. So we break ties here
  //     * and increment the total score based on how ties 
  //     * are broken.
  //     */
  //      
  //    } else {
  //      
  //      /** We break ties according to both player's hand ranking */
  //      Ranking ranking = this.getRanking();
  //      
  //      /** Special Case when we have a pair */
  //      if(ranking == Ranking.PAIR) {
  //        
  //        /** Get my pair cards */
  //        int myPairCardRank = 
  //            this.getCard(0).getRank() == this.getCard(1).getRank() ? 
  //            this.getCard(0).getRankAsInt() : 
  //            this.getCard(2).getRankAsInt();
  //        /** Get other players pair cards */ 
  //        int otherPairCardRank = 
  //            other.getCard(0).getRank() == other.getCard(1).getRank() ? 
  //            other.getCard(0).getRankAsInt() : 
  //            other.getCard(2).getRankAsInt();
  //        
  //        if(myPairCardRank > otherPairCardRank) {
  //          return -1;
  //        } else if(myPairCardRank < otherPairCardRank) {
  //          return 1;
  //        } else {
  //          
  //          int myRemainingCardRank = 
  //            this.getCard(0).getRank() == this.getCard(1).getRank() ? 
  //            this.getCard(2).getRankAsInt() : 
  //            this.getCard(0).getRankAsInt();
  //          int otherRemainingCardRank = 
  //              other.getCard(0).getRank() == other.getCard(1).getRank() ? 
  //              other.getCard(2).getRankAsInt() : 
  //              other.getCard(0).getRankAsInt();
  //              
  //          if(myRemainingCardRank > otherRemainingCardRank) {
  //            return -1;
  //          } else if (myRemainingCardRank < otherRemainingCardRank) {
  //            return 1;
  //          }
  //      }
  //      /** Otherwise, we break rankings according to HighCards */
  //      } else {
  //        /** Compare Cards from Highest To Lowest */
  //        for(int i = this.getHand().size()-1; i >= 0; i--){
  //          /** If my high card is greater, then I win */
  //          if(this.getCard(i).getRankAsInt() > other.getCard(i).getRankAsInt()){
  //            return -1;
  //          /** If other player's high card is greater, then I lose */
  //          } else if (this.getHand().get(i).getRankAsInt() < other.getHand().get(i).getRankAsInt()) {
  //            return 1;
  //          }
  //        }
  //      }
  //    }
  //    /** We have now tied */ 
  //    return 0;
  //      if(this.getRanking() == Ranking.HIGH_CARD) {
  //        for(int i = this.getHand().size()-1; i >= 0; i--){
  //          if(this.getHand().get(i).getRankAsInt() > other.getHand().get(i).getRankAsInt()){
  //            return -1;
  //          } else if (this.getHand().get(i).getRankAsInt() < other.getHand().get(i).getRankAsInt()) {
  //            return 1;
  //          }
  //        }
  //      } else if(this.getRanking() == Ranking.PAIR) {
  //        int myPairCardRank = this.getHand().get(0) == this.getHand().get(1) ? 
  //            this.getHand().get(0).getRankAsInt() : this.getHand().get(2).getRankAsInt();
  //        int otherPairCardRank = other.getHand().get(0) == other.getHand().get(1) ? 
  //            other.getHand().get(0).getRankAsInt() : other.getHand().get(2).getRankAsInt();
  //        if(myPairCardRank > otherPairCardRank) {
  //          return -1;
  //        } else if(myPairCardRank < otherPairCardRank) {
  //          return 1;
  //        } else {
  //          int myRemainingCardRank = this.getHand().get(0) == this.getHand().get(1) ? 
  //              this.getHand().get(2).getRankAsInt() : (this.getHand().get(1) == this.getHand().get(2)) ? 
  //                  this.getHand().get(0).getRankAsInt() : this.getHand().get(1).getRankAsInt();
  //          int otherRemainingCardRank = other.getHand().get(0) == other.getHand().get(1) ? 
  //              other.getHand().get(2).getRankAsInt() : (other.getHand().get(1) == other.getHand().get(2)) ? 
  //                  other.getHand().get(0).getRankAsInt() : other.getHand().get(1).getRankAsInt();  
  //          if(myRemainingCardRank > otherRemainingCardRank) {
  //            return -1;
  //          } else if (myRemainingCardRank < otherRemainingCardRank) {
  //            return 1;
  //          }
  //        }
  //      } else if (this.getRanking() == Ranking.FLUSH) {
  //        return this.myCumulativeRank > other.myCumulativeRank ? -1 : 1;
  //      } else if (this.getRanking() == Ranking.STRAIGHT) {
  //        return this.getHand().get(0).getRankAsInt() > other.getHand().get(0).getRankAsInt() ? -1 : 1;
  //      } else if (this.getRanking() == Ranking.THREE_OF_A_KIND) {
  //        return this.myCumulativeRank > other.myCumulativeRank ? -1 : 1;
  //      } else if (this.getRanking() == Ranking.STRAIGHT_FLUSH) {
  //        return this.getHand().get(0).getRankAsInt() > other.getHand().get(0).getRankAsInt() ? -1 : 1;




}
