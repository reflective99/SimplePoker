package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class RankCalculator {

  private int numTotalPlayers;
  private List<Player> playerList = new ArrayList<Player>();
  private List<Integer> winners = new ArrayList<Integer>();

  public void addPlayer(Player p) {
    playerList.add(p);
  }

  public int getNumTotalPlayers() {
    return numTotalPlayers;
  }

  public void setNumTotalPlayers(int numTotalPlayers) {
    this.numTotalPlayers = numTotalPlayers;
  }

  private void generatePlayersWithCards(Map<Integer, List<String>> map) {

    for(Map.Entry<Integer, List<String>> player : map.entrySet()) {
      List<String> cards = player.getValue();
      int playerID = player.getKey();
      List<Card> hand = new ArrayList<Card>(3);
      for(String s : cards){
        CardSuit suit = determineCardSuit(s);
        //System.out.println(suit.toString());
        CardRank rank = determineCardRank(s);
        Card card = new Card(rank, suit);
        hand.add(card);
      }
      /** Sort the hand by Card Rank before adding it to the Player */
      Collections.sort(hand);
      Player newPlayer = new Player(playerID, hand);
      //System.out.println(newPlayer);
      playerList.add(newPlayer);
    }
  }

  private CardRank determineCardRank(String s) {
    CardRank rank = null;
    switch (s.charAt(0)) {
      case '2': rank = CardRank.CARD2; break;
      case '3': rank = CardRank.CARD3; break;
      case '4': rank = CardRank.CARD4; break;
      case '5': rank = CardRank.CARD5; break;
      case '6': rank = CardRank.CARD6; break;
      case '7': rank = CardRank.CARD7; break;
      case '8': rank = CardRank.CARD8; break;
      case '9': rank = CardRank.CARD9; break;
      case 'T': rank = CardRank.CARD10; break;
      case 'J': rank = CardRank.JACK; break;
      case 'Q': rank = CardRank.QUEEN; break;
      case 'K': rank = CardRank.KING; break;
      case 'A': rank = CardRank.ACE; break;
    }
    return rank;
  }

  private CardSuit determineCardSuit(String s) {
    CardSuit suit = null;
    switch (s.charAt(1)) {
      case 's':
        suit = CardSuit.SPADES;
        break;
      case 'd':
        suit = CardSuit.DIAMONDS;
        break;
      case 'c': 
        suit = CardSuit.CLUBS;
        break;
      case 'h':
        suit = CardSuit.HEARTS;
        break;
    }
    return suit;
  }

  public List<Player> getPlayerList() {
    return this.playerList;
  }

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    InputReader inputReader = new InputReader(input);

    RankCalculator ranker = new RankCalculator();

    List<Integer> winners = ranker.rankWinners(ranker, inputReader);

  }

  public List<Integer> rankWinners(RankCalculator ranker, InputReader inputReader) {
    ranker.setNumTotalPlayers(inputReader.getNumPlayers());

    Map<Integer, List<String>> map = inputReader.getPlayerHands();

    ranker.generatePlayersWithCards(map);

    List<Player> list = ranker.getPlayerList();

    for(Player p : list){
      Ranking rank = ranker.evaluateRanking(p);
      System.out.println(p.toString());
      System.out.println(p.toStringWithRank());
    }

    Collections.sort(list);
    for(Player p : list){
      System.out.println(p.getID()+":ranking: " + p.getRanking() + " cr: " + p.getCR());
    }
    System.out.println("============= WINNERS ================");
    
    Player winner = list.get(0);
    
    ranker.winners.add(winner.getID());
    
    for(int i = 1; i < list.size(); i++) {
      if(list.get(i-1).getCR() == list.get(i).getCR()){
        ranker.winners.add(list.get(i).getID());
      } else {
        break;
      }
    }
    if(winners.size() > 1) {
      winners = breakTies(winners);
    }
    System.out.println(ranker.winners);
    return winners;
  }

  //  HIGH_CARD,        // Ranking 0
  //  PAIR,             // Ranking 1
  //  FLUSH,            // Ranking 2
  //  STRAIGHT,         // Ranking 3
  //  THREE_OF_A_KIND,  // Ranking 4
  //  STRAIGHT_FLUSH,   // Ranking 5

  private List<Integer> breakTies(List<Integer> winners) {
    
    return winners;
  }

  public List<Integer> getWinners() {
    return winners;
  }

  private Ranking evaluateRanking(Player p) {

    List<Card> hand = p.getHand();

    Card first = hand.get(0);
    Card second = hand.get(1);
    Card third = hand.get(hand.size()-1);

    /** Cards are already sorted so we check if the first
     * and the last card are from the same suit and then
     * we check the difference between their rank. If they're
     * the same suit and the difference in their rank is 2, 
     * then it's a Straight Flush!
     */
    if(first.getSuit() == third.getSuit() && third.getRankAsInt() - first.getRankAsInt() == 2){
      p.setRanking(Ranking.STRAIGHT_FLUSH);
      return Ranking.STRAIGHT_FLUSH;
    }

    /**
     * Next we check if all the cards are from the same suit.
     * If so, we set the ranking to Three of a Kind
     */

    if(first.getSuit() == second.getSuit() && second.getSuit() == third.getSuit()) {
      p.setRanking(Ranking.THREE_OF_A_KIND);
      return Ranking.THREE_OF_A_KIND;
    }
    /**
     * If the difference between the rank of two cards is 2, 
     * then they're in an increasing ranking sequence. 
     * That's a Straight!
     */
    if(third.getRankAsInt() - first.getRankAsInt() == 2){
      p.setRanking(Ranking.STRAIGHT);
      return Ranking.STRAIGHT;
    }

    /**
     * Check if all three cards have the same suit
     * Then its a Flush!
     */
    if(first.getSuit() == second.getSuit() && second.getSuit() == third.getSuit()){
      p.setRanking(Ranking.FLUSH);
      return Ranking.FLUSH;
    }

    /**
     * If either two cards have the same rank, 
     * then it's a Pair!
     */
    if(
        (first.getRankAsInt() == second.getRankAsInt()) ||
        (first.getRankAsInt() == third.getRankAsInt()) ||
        (second.getRankAsInt() == third.getRankAsInt())
        ){
      p.setRanking(Ranking.PAIR);
      return Ranking.PAIR;

    }

    /**
     * If all else fails, we select the highest card!
     */
    p.setRanking(Ranking.HIGH_CARD);
    return Ranking.HIGH_CARD;

  }


}
