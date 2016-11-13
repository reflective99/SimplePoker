package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InputReader {
  
  private Map<Integer, List<String>> playerHand;
  private int numPlayers;
  private Scanner scanner;
  
  public InputReader(Scanner sc){
    this.scanner = sc;
    readInput(this.scanner);
  }
  
  private void readInput(Scanner scanner) {
    
    numPlayers = scanner.nextInt(); 
    scanner.nextLine();
    
    playerHand = new HashMap<Integer, List<String>>();
    
    for(int i = 0; i < numPlayers; i++){
      String s = scanner.nextLine();
      String[] spl = s.split(" ");
      List<String> hand = new ArrayList<String>();
      for(int j = 1; j < spl.length; j++){
        hand.add(spl[j]);
      }
      playerHand.put(Integer.parseInt(spl[0]), hand);
    }
      
    scanner.close();
    System.out.println();
    System.out.println("numPlayers: " + numPlayers);
    System.out.println("input: " + playerHand);
    
  }

}
