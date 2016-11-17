package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * RankCalculator is the Main class where the the input
 * <code>args</code> are passed in. It calculates 
 * the Ranking of each player in the input and 
 * prints it out at the end.
 */
public class RankCalculator {
  
  /** a <m>static final int</m> representing the number of cards in this Poker Game */
  private static final int CARDS_IN_POKER_HAND = 3;
  /** Total Number of Players - @deprecated as we don't really need this info */
  private int numTotalPlayers;
  /** A <code>List<></code> of <b>Player</b> Objects - @deprecated as well, don't need it */
  private List<Player> playerList = new ArrayList<Player>();
  /** A <code>List<></code> of Integers which contains the final winners' IDs */
  private List<Integer> winners = new ArrayList<Integer>();

  /**
   * The <m>main()</m> method of RankCalculator class 
   * retrieves the input from <code>System.in</code>
   * and sends it to the InputReader. 
   * @param args
   */
  public static void main(String[] args) {
    /** Instantiate a new RankCalculator Object */
    RankCalculator ranker = new RankCalculator();
    /** Read in the input from <m>System.in</m> into a scanner */
    Scanner input = new Scanner(System.in);
    /** Create a new input Parser Object and pass the scanner
     * into it's constructor */
    InputReader inputReader = new InputReader(input);
    ranker.setNumTotalPlayers(inputReader.getNumPlayers());
    /** Get the Player Hands passed into the input as a map */
    Map<Integer, List<String>> playerHands = inputReader.getPlayerHands();
    /** Generate a List of Players with their corresponding Hands */
    List<Player> rankedPlayers = ranker
        .generatePlayerListWithHands(playerHands);
    /** Finally generate a List of Winners (their IDs) as a List of Integers 
     *  based on the ranking of their corresponding Hands. */
    ranker.winners = ranker.generateWinnerList(rankedPlayers);
    /** Use a StringBuilder to generate the output string */
    StringBuilder win = new StringBuilder();
    for(Integer i : ranker.getWinners()){
      win.append(i);
      win.append(" ");
    }
    /** Print out the result on a new line */
    System.out.println(win.toString());
  }
  /**
   * A method that takes in a map of player IDs and their Hand 
   * and generates Player Objects for each Entry<K,V> in the map.
   * It then returns a <m>List<></m> of these entries.
   * @param map - A map of players with their hands 
   * @return <m>List<Player>()</m>
   */
  private List<Player> generatePlayerListWithHands(Map<Integer, List<String>> map) {
    List<Player> result = new ArrayList<Player>();
    for(Map.Entry<Integer, List<String>> player : map.entrySet()) {
      List<String> listOfCardStrings = player.getValue();
      if(listOfCardStrings.size() != CARDS_IN_POKER_HAND){
        System.err.println("ERROR: Input contained more or less"
            + "than the specified number of cards for this game!");
        throw new InputMismatchException();
      }
      int playerID = player.getKey();
      Card[] handCards = new Card[CARDS_IN_POKER_HAND];
      for(int i = 0; i < listOfCardStrings.size(); i++){
        String cardString = listOfCardStrings.get(i);
        CardSuit suit = determineCardSuit(cardString);
        CardRank rank = determineCardRank(cardString);
        handCards[i] = new Card(rank, suit);
      }
      /** Sort the handCards by CardRank before adding it to
       *  the Player */
      Arrays.sort(handCards);
      /** Generate a new Hand with the Card[] Array */
      Hand newHand = new Hand(handCards);
      newHand.setPlayerID(playerID);
      /** Generate a new Player with the Hand and playerID */
      Player newPlayer = new Player(playerID, newHand);
      /** Add this new player to the result <m>List<></m> */
      result.add(newPlayer);
    }
    return result;
  }
  
  /**
   * A Method that generates a list of Integer IDs of the Winners.
   * If there is a only one winner, the list returned will contain 
   * only 1 ID. If there are ties, and there are multiple winners 
   * with equally ranked hands, the method returns a list of multiple
   * IDs for those winners. 
   * @param players - <m>List<Player></m> of Players
   * @return <m>List<Integer></m> a list containing the IDs of the Winners
   */
  private List<Integer> generateWinnerList(List<Player> players) {
    /** First sort the list of players using Collections.sort()
     *  The mechanism for sorting is implemented in the compareTo()
     *  methods of the Player and Hand classes.*/
    Collections.sort(players);
    //System.out.println(players);
    List<Integer> result = new ArrayList<Integer>();
    /** Get the first Winner */
    Player winner = players.get(0);
    /** Add the Winner to the List of Results */
    result.add(winner.getID());
    /** Now, for each remaining player in the list, check if 
     *  their ranking is equal to the ranking of the first
     *  winner. If yes, add them to the list too, otherwise break
     *  and return.
     */
    for(int i = 1; i < players.size(); i++) {
      if(players.get(i-1).compareTo(players.get(i)) == 0){
        result.add(players.get(i).getID());
      } else {
        break;
      }
    }    
    return result;
  }
  
  /**
   * A method that takes in a String representation
   * of a card dealt to a Player and determines it's 
   * corresponding CardRank.
   * @param s - string representing a card e.g 2c 2 of Clubs
   * @return CardRank
   */
  private CardRank determineCardRank(String s) {
    CardRank rank = null;
    switch (s.charAt(0)) {
      case '2': rank = CardRank.TWO;    break;
      case '3': rank = CardRank.THREE;  break;
      case '4': rank = CardRank.FOUR;   break;
      case '5': rank = CardRank.FIVE;   break;
      case '6': rank = CardRank.SIX;    break;
      case '7': rank = CardRank.SEVEN;  break;
      case '8': rank = CardRank.EIGHT;  break;
      case '9': rank = CardRank.NINE;   break;
      case 'T': rank = CardRank.TEN;    break;
      case 'J': rank = CardRank.JACK;   break;
      case 'Q': rank = CardRank.QUEEN;  break;
      case 'K': rank = CardRank.KING;   break;
      case 'A': rank = CardRank.ACE;    break;
    }
    return rank;
  }
  
  /**
   * A method that takes in a String representation 
   * of a card and determines it's CardSuit.
   * @param s - string representing a card
   * @return CardSuit
   */
  private CardSuit determineCardSuit(String s) {
    CardSuit suit = null;
    switch (s.charAt(1)) {
      case 's':
        suit = CardSuit.SPADE;
        break;
      case 'd':
        suit = CardSuit.DIAMOND;
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
  
  /** Getters and Setters */
  
  public void submitPlayersForRanking(List<Player> players) {
    this.winners = this.generateWinnerList(players);
  }
  
  public List<Player> submitPlayerMapToGeneratePlayerList(Map<Integer, List<String>> map) {
    return this.generatePlayerListWithHands(map);
  }
  
  public List<Player> getPlayerList() {
    return this.playerList;
  }
  
  public List<Integer> getWinners() {
    return winners;
  }
  
  public void addPlayer(Player p) {
    playerList.add(p);
  }

  public int getNumTotalPlayers() {
    return numTotalPlayers;
  }

  public void setNumTotalPlayers(int numTotalPlayers) {
    this.numTotalPlayers = numTotalPlayers;
  }


}
