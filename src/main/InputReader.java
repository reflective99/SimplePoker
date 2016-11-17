package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * InputReader reads in the input string 
 * from a Scanner and parses it accordingly.
 * The input is given in the following format
 * 1               : first line represents num of players
 * 0 2d Ts 4d      : second line represents the player id and the hand
 */
public class InputReader {

  /** A Map of <pre>Player</pre> ids to their corresponding hand as specified in the input */
  private Map<Integer, List<String>> playerHands;
  /** Number of total players */
  private int numPlayers;
  /** Scanner with the input from <code>System.in</code> */
  private Scanner scanner;

  /**
   * Constructor that instantiates the scanner field
   * and throws an <code>IllegalArgumentException</code>
   * in case of null input.
   * @param sc - scanner containing the player ids and hands
   */
  public InputReader(Scanner sc){
    if(sc == null)
      throw new IllegalArgumentException();
    scanner = sc;
    parseInput(this.scanner);
  }

  /**
   * This method parses the input in the scanner
   * and populates the instance variables above
   * @param scanner
   */
  private void parseInput(Scanner scanner) {

    numPlayers = scanner.nextInt(); 
    scanner.nextLine();
    playerHands = new HashMap<Integer, List<String>>();
    for(int i = 0; i < numPlayers; i++){
      String s = scanner.nextLine();
      String[] spl = s.split(" ");
      List<String> hand = new ArrayList<String>();
      for(int j = 1; j < spl.length; j++){
        hand.add(spl[j]);
      }
      //System.out.println("Player: " + spl[0] + " hand " + hand);
      playerHands.put(Integer.parseInt(spl[0]), hand);
    }
    scanner.close();

  }
  
  /** Getters and Setters for retrieving InputReader fields */
  public Map<Integer, List<String>> getPlayerHands() {
    return playerHands;
  }

  public void setplayerHands(Map<Integer, List<String>> playerHands) {
    this.playerHands = playerHands;
  }

  public int getNumPlayers() {
    return numPlayers;
  }

  public void setNumPlayers(int numPlayers) {
    this.numPlayers = numPlayers;
  }

  public Scanner getScanner() {
    return scanner;
  }

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }

}
