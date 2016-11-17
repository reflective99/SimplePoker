package utils;

import main.CardSuit;
import main.Hand;
import main.CardRank;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import main.Card;

public class RandomHandGenerator{

  private Hand[] myHands;   /** Array of Hands generated */
  private int numHands;     /** Number of hands to generate */
  private int numCards;     /** Number of cards in this hand */
  
  private static final boolean WRITE_TO_FILE = true;
  
  private static final boolean DEBUG_ENABLED = false;

  public RandomHandGenerator(int numHands, int numCards){
    this.numHands = numHands;
    this.numCards = numCards;
  }
  
  private <T> void printArrayWithMessage(String message, T[] array) {
    if(DEBUG_ENABLED) {
      System.out.println(message + " " + Arrays.toString(array));
    }
  }
  
  private <T> void printDebugWrapper(String message, T type) {
    if(DEBUG_ENABLED) {
      System.out.println(message + " " + type);
    }
  }
  
  private <T> void printMessage(T type) {
    if(DEBUG_ENABLED) {
      System.out.println(type);
    }
  }
  
  private void printCollectionWithMessage(String message, Collection<?> collection) {
    if(DEBUG_ENABLED) {
      System.out.println(message + " " + collection);
    }
  }
  
  @SafeVarargs
  @SuppressWarnings({ "static-access" })
  private final <T> void printFormattedStringMessage(String message, T... types) {
    if(DEBUG_ENABLED) {
      String formattedString = message.format(message, types);
      System.out.println(formattedString);
    }
  }
  
  public void generateHands() {

    CardSuit[] suits = CardSuit.values();
    CardRank[] ranks = CardRank.values();

    /** Initialize cards in a deck */
    Card[] deck = new Card[suits.length * ranks.length];
    int deckSize = deck.length;

    for(int i = 0; i < ranks.length; i++){
      for(int j = 0; j < suits.length; j++){
        Card newCard = new Card(ranks[i], suits[j]);
        deck[suits.length*i + j] = newCard;
      }
    }
    
    printArrayWithMessage("Deck before Shuffle:", deck);
    
    List<Card> deckList = Arrays.asList(deck);
    Collections.shuffle(deckList);
    Collections.shuffle(deckList);
    Collections.shuffle(deckList);
    deckList.toArray(deck);
    
    printArrayWithMessage("Deck After Shuffle:", deck);
    
    printDebugWrapper("Deck Size:", deck.length);

    int[] totalSuits = new int[4];
    for(int i = 0; i < deck.length; i++) {
      switch (deck[i].getSuit().ordinal()){
        case 0:
          totalSuits[0]++;
          break;
        case 1:
          totalSuits[1]++;
          break;
        case 2:
          totalSuits[2]++;
          break;
        case 3:
          totalSuits[3]++;
          break;
      }
    }
    
    printCollectionWithMessage("Each Suit in Deck:", Arrays.asList(totalSuits));
    
    Random generator = new Random();

    myHands = new Hand[numHands];
    for(int i = 0; i < numHands; i++){
      Card[] hand = new Card[numCards];
      Set<Card> cardsPickedSoFar = new HashSet<Card>();
      int j = 0;
      while(j != numCards){
        int rand = generator.nextInt(deckSize-1);
        int rand2 = generator.nextInt(deckSize-1);
        Card newCard = new Card(deck[rand].getRank(), deck[rand2].getSuit());
        if(cardsPickedSoFar.contains(newCard)) {
          printMessage("Was picked the Same Card for this hand!");
          printCollectionWithMessage("cardsPickedSoFar", cardsPickedSoFar);
          continue;
        } else {
          hand[j] = newCard;
          cardsPickedSoFar.add(newCard);
          j++;
        }
      }
      myHands[i] = new Hand(hand);
    }

    for(int i = 0; i < myHands.length; i++){
      myHands[i].setPlayerID(i);
    }
    
    Hand[] myHandsDuplicate = new Hand[numHands];
    myHandsDuplicate = myHands.clone();
    Arrays.sort(myHandsDuplicate);
    
    for(int i = 0; i < myHands.length; i++) {
      String formattedString = "   %3s  %12s => R: %-16s      %-3s  %13s => R: %-16s";
      printFormattedStringMessage(formattedString, 
          myHands[i].getPlayerID(),   
          Arrays.toString(myHands[i].getMyCards()), 
          Arrays.toString(myHands[i].getMyRankValue()),
          myHandsDuplicate[i].getPlayerID(), 
          Arrays.toString(myHandsDuplicate[i].getMyCards()),
          Arrays.toString(myHandsDuplicate[i].getMyRankValue())
      );
    }
    
    List<Integer> winners = new ArrayList<Integer>();
    winners.add(myHandsDuplicate[0].getPlayerID());
    for(int i = 1; i < numHands; i++){
      if(myHandsDuplicate[i-1].compareTo(myHandsDuplicate[i]) == 0) {
        winners.add(myHandsDuplicate[i].getPlayerID());
      } else {
        break;
      }
    }
    
    String[] lines = new String[1+myHands.length+2];
    lines[0] = Integer.toString(myHands.length);
    for(int i = 1, j = 0; i < myHands.length+1; i++, j++){
      StringBuilder string = new StringBuilder();
      Hand currHand = myHands[j];
      int ID = currHand.getPlayerID();
      Card[] cards = myHands[j].getMyCards();
      string.append(ID+" ");
      for(int k = 0; k < cards.length; k++){
        if(k == cards.length-1){
          string.append(cards[k].toString());
        } else {
          string.append(cards[k].toString()+" ");
        }
      }
      lines[i] = string.toString();
      StringBuilder win = new StringBuilder();
      for(int k = 0; k < winners.size(); k++){
        if(winners.size() > 0 && k == winners.size()-1) {
          win.append(Integer.toString(winners.get(k)));
        } else {
          win.append(Integer.toString(winners.get(k))+" ");
        }
      }
      lines[lines.length-1] = win.toString();
    }
    
    StringBuilder fileStringSuffix = new StringBuilder();
    fileStringSuffix.append(new BigInteger(40, new Random()).toString().toUpperCase());
    fileStringSuffix.append("-winners");
    for(Integer i : winners) {
      fileStringSuffix.append("-"+Integer.toString(i));
    }
    
   printMessage(Arrays.toString(lines));
    
    if(WRITE_TO_FILE){
      try {
        String path = "/home/asim/projects/Poker/src/tests/files/auto-generated-tests/"+"test-"+fileStringSuffix.toString();
        PrintWriter writer = new PrintWriter(path, "UTF-8");
        for(int i = 0; i < lines.length; i++){
          if(lines[i] == null || lines[i].isEmpty()){
            writer.println();
          } else {
            writer.println(lines[i]);
          }
        }
        writer.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void calculateStats() {
    //int hearts   = 0;   Double heartsPercent   = 0.0;
    //int spades   = 0;   Double spadesPercent   = 0.0;
    //int clubs    = 0;   Double clubsPercent    = 0.0;
    //int diamonds = 0;   Double diamondsPercent = 0.0;

    //int totalHands = myHands.length;
    //int totalCards = myHands.length*numHands;

    //for(int i = 0; i < myHands.length; i++) {
      //hearts   += myHands[i].getNumOfSuits(CardSuit.HEARTS);
      //spades   += myHands[i].getNumOfSuits(CardSuit.SPADE);
      //clubs    += myHands[i].getNumOfSuits(CardSuit.CLUBS);
      //diamonds += myHands[i].getNumOfSuits(CardSuit.DIAMOND);
    //}

    //heartsPercent   = ( ((double) hearts)   / ((double) totalCards)  ) * 100;
    //spadesPercent   = ( ((double) spades)   / ((double) totalCards)  ) * 100;
    //clubsPercent    = ( ((double) clubs)    / ((double) totalCards)  ) * 100;
    //diamondsPercent = ( ((double) diamonds) / ((double) totalCards)  ) * 100;        
  }

  public void printMyStats() {

  }

  public Hand[] getHands(){
    return this.myHands;
  }
  
  public void setNewNumHands(int i) {
    this.numHands = i;
  }

  public static void main(String[] args) throws InterruptedException {
    RandomHandGenerator rh = new RandomHandGenerator(14, 3);
    rh.generateHands();
  }

}
