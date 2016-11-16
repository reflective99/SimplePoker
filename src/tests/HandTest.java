package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.core.Is.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import main.CardRank;
import main.CardSuit;
import main.Hand;
import main.Card;

@RunWith(Parameterized.class)
public class HandTest {

  Hand hand;
  List<Integer> result;

  @Parameters
  public static List<Object[][]> data() {

    List<Object[][]> parameters = new ArrayList<Object[][]>();

    List<Card[]> handsToTest = new ArrayList<Card[]>();

    Card[] highCard = new Card[] { 
        new Card(CardRank.JACK, CardSuit.CLUBS), 
        new Card(CardRank.NINE, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMOND)};
    
    Card[] pair = new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.NINE, CardSuit.DIAMOND)};
    
    Card[] flush = new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.SEVEN, CardSuit.CLUBS), 
        new Card(CardRank.NINE, CardSuit.CLUBS)};
    
    Card[] lowStraight = new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUBS), 
        new Card(CardRank.TWO, CardSuit.HEARTS), 
        new Card(CardRank.THREE, CardSuit.DIAMOND)};
    
    Card[] highStraight = new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUBS), 
        new Card(CardRank.KING, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMOND)};
    
    Card[] threeOfAKind = new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.FIVE, CardSuit.DIAMOND) };
    
    Card[] lowStraightFlush = new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUBS), 
        new Card(CardRank.TWO, CardSuit.CLUBS), 
        new Card(CardRank.THREE, CardSuit.CLUBS)};
    
    Card[] highStraightFlush = new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUBS), 
        new Card(CardRank.KING, CardSuit.CLUBS), 
        new Card(CardRank.ACE, CardSuit.CLUBS)}; 

    handsToTest.add(highCard); handsToTest.add(pair); handsToTest.add(flush);
    handsToTest.add(lowStraight); handsToTest.add(highStraight); handsToTest.add(threeOfAKind);
    handsToTest.add(lowStraightFlush); handsToTest.add(highStraightFlush);

    List<Boolean[]> rankings = new ArrayList<Boolean[]>();

    rankings.add(new Boolean[] {false, true, false, false, false, false, false});
    rankings.add(new Boolean[] {false, false, true, false, false, false, false});
    rankings.add(new Boolean[] {false, true, false, true, false, false, false});
    rankings.add(new Boolean[] {false, true, false, false, true, false, false});
    rankings.add(new Boolean[] {false, true, false, false, true, false, false});
    rankings.add(new Boolean[] {false, false, false, false, false, true, false});
    rankings.add(new Boolean[] {false, true, false, true, true, false, true});
    rankings.add(new Boolean[] {false, true, false, true, true, false, true});

    for(int i = 0; i < rankings.size(); i++){
      parameters.add(new Object[][] { handsToTest.get(i), rankings.get(i) });
    }

    return parameters;
  }

  public HandTest(Card[] card, Boolean[] bool){
    Arrays.sort(card);
    hand = new Hand(card);
    result = new ArrayList<Integer>();
    for(int i = 0; i < bool.length; i++){
      if(bool[i]){
        result.add(i);
      }
    }
  }

  @Test
  public void shouldGetCorrectRanking() {
    //hand.printHand();
    List<Integer> winners = hand.getRankingsAsList();

    for(int i = 0; i < winners.size(); i++){
      assertThat(winners.get(i), is(result.get(i)));
    }
  }

}
