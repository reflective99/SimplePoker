package utils;

import main.CardSuit;
import main.Hand;
import utils.RandGeneratorStats.Stats;
import main.CardRank;

import java.util.Arrays;
import java.util.Random;

import main.Card;

public class RandomHandGenerator{

  private Hand[] myHands;
  private int numHands;

  public RandomHandGenerator(int numHands){
    this.numHands = numHands;
  }
  
  public void generateHands() {
    try {
      CardSuit[] suits = CardSuit.values();
      CardRank[] ranks = CardRank.values();

      /** Inititialize cards in a deck */
      Card[] deck = new Card[suits.length * ranks.length];
      int deckSize = deck.length;

      for(int i = 0; i < ranks.length; i++){
        for(int j = 0; j < suits.length; j++){
          Card newCard = new Card(ranks[i], suits[j]);
          deck[suits.length*i + j] = newCard;
          //System.out.println(newCard);
        }
      }

      for(int i = 0; i < deckSize; i++){
        int rand = i + (int) (Math.random() * (deckSize - i));
        Card temp = deck[rand];
        deck[rand] = deck[i];
        deck[i] = temp;
      }
      Random generator = new Random();
      
      myHands = new Hand[numHands];
      // Generate 50 hands 
      for(int i = 0; i < numHands; i++){
        Card[] hand = new Card[3];
        for(int j = 0; j < 3; j++){
          int rand = generator.nextInt(deckSize-1);
          int rand2 = generator.nextInt(deckSize-1);
          hand[j] = new Card(deck[rand].getRank(), deck[rand2].getSuit());
        }
        myHands[i] = new Hand(hand);
      }
      //System.out.println("Deck Size: " + deckSize);
      //System.out.println(Arrays.toString(deck));

      for(int i = 0; i < myHands.length; i++){
        Card[] hand = myHands[i].getMyCards();
        //System.out.println(Arrays.toString(hand));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }

  public void calculateStats() {
    int hearts   = 0;   Double heartsPercent   = 0.0;
    int spades   = 0;   Double spadesPercent   = 0.0;
    int clubs    = 0;   Double clubsPercent    = 0.0;
    int diamonds = 0;   Double diamondsPercent = 0.0;

    int totalHands = myHands.length;
    int totalCards = myHands.length*3;

    int numEqualHands = 0;

    for(int i = 0; i < myHands.length; i++) {
      hearts   += myHands[i].getNumOfSuits(CardSuit.HEARTS);
      spades   += myHands[i].getNumOfSuits(CardSuit.SPADE);
      clubs    += myHands[i].getNumOfSuits(CardSuit.CLUBS);
      diamonds += myHands[i].getNumOfSuits(CardSuit.DIAMOND);
    }

    for(int i = 1; i < myHands.length; i++) {
      numEqualHands = (myHands[i].equals(myHands[i-1]) == true ? numEqualHands + 1 : numEqualHands);
    }

    heartsPercent   = ( ((double) hearts) / ((double) totalCards)  ) * 100;
    spadesPercent   = ( ((double) spades) / ((double) totalCards)  ) * 100;
    clubsPercent    = ( ((double) clubs) / ((double) totalCards)  ) * 100;
    diamondsPercent = ( ((double) diamonds) / ((double) totalCards)  ) * 100;        
  }
  
  public void printMyStats() {
    
  }

  public Hand[] getHands(){
    return this.myHands;
  }

  public static void main(String[] args) throws InterruptedException {
    /*RandomHandGenerator rh = new RandomHandGenerator(99999999);
    rh.generateHands();
    Stats.receiveStats(rh.getHands());
    Stats.requestPrintJob(); */
  }

}
