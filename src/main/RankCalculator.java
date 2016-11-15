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
      case '2': rank = CardRank.TWO; break;
      case '3': rank = CardRank.THREE; break;
      case '4': rank = CardRank.FOUR; break;
      case '5': rank = CardRank.FIVE; break;
      case '6': rank = CardRank.SIX; break;
      case '7': rank = CardRank.SEVEN; break;
      case '8': rank = CardRank.EIGHT; break;
      case '9': rank = CardRank.NINE; break;
      case 'T': rank = CardRank.TEN; break;
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

  public List<Player> rankWinners(Map<Integer, List<String>> map) {

    generatePlayersWithCards(map);
    List<Player> players = getPlayerList();
    Collections.sort(players);
    return players;
    
  }
  
  private List<Integer> generateWinnerList(List<Player> players) {
    List<Integer> result = new ArrayList<Integer>();
    Player winner = players.get(0);
    result.add(winner.getID());
    for(int i = 1; i < players.size(); i++) {
      if(players.get(i-1).compareTo(players.get(i)) == 0){
        result.add(players.get(i).getID());
      } else {
        break;
      }
    }    
    //System.out.println(result);
    return result;
  }
  
  public void submitPlayers(List<Player> players) {
    this.winners = this.generateWinnerList(players);
  }

  public List<Integer> getWinners() {
    return winners;
  }

  public static void main(String[] args) {

    Scanner input = new Scanner(System.in);

    InputReader inputReader = new InputReader(input);

    RankCalculator ranker = new RankCalculator();

    ranker.setNumTotalPlayers(inputReader.getNumPlayers());

    List<Player> rankedPlayers = ranker.rankWinners(inputReader.getPlayerHands());
    
    ranker.winners = ranker.generateWinnerList(rankedPlayers);

    StringBuilder win = new StringBuilder();

    for(Integer i : ranker.getWinners()){
      win.append(i);
      win.append(" ");
    }

    System.out.println(win.toString());

  }

}
