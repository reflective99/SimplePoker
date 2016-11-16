package main;

import main.Ranking;

/**
 * Player class holds the player information
 * such as the Player ID, Hand dealt to the Player,
 * and the Ranking of the Player.
 */
public class Player implements Comparable<Player>{

  private int myID;             
  private Hand myHand;
  private Ranking myRanking;
 
  public Player(int id, Hand hand) {   
    myID = id;
    myHand = hand;
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

}
