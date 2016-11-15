package tests;

import static org.junit.Assert.*;

import static org.hamcrest.core.Is.*;

import org.junit.Before;
import org.junit.Test;

import main.Card;
import main.CardRank;
import main.CardSuit;
import main.Hand;

public class HandComparisionTest {
  
  
  private Hand highCard;
  private Hand pair;
  private Hand flush;
  private Hand lowStraight;
  private Hand highStraight;
  private Hand threeOfAKind;
  private Hand lowStraightFlush;
  private Hand highStraightFlush;
  
  @Before
  public void initialize() {
    
    highCard        =          
        new Hand(new Card[] { 
        new Card(CardRank.JACK, CardSuit.CLUBS), 
        new Card(CardRank.NINE, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMONDS)});
    
    pair            = 
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.NINE, CardSuit.DIAMONDS) });
    
    flush           =  
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.SEVEN, CardSuit.CLUBS), 
        new Card(CardRank.NINE, CardSuit.CLUBS)});
    
    lowStraight     = 
        new Hand(new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUBS), 
        new Card(CardRank.TWO, CardSuit.HEARTS), 
        new Card(CardRank.THREE, CardSuit.DIAMONDS)});
    
    highStraight    = 
        new Hand(new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUBS), 
        new Card(CardRank.KING, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMONDS)});
    
    threeOfAKind    = 
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUBS), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.FIVE, CardSuit.DIAMONDS) });
    
    lowStraightFlush= 
        new Hand(new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUBS), 
        new Card(CardRank.TWO, CardSuit.CLUBS), 
        new Card(CardRank.THREE, CardSuit.CLUBS)});
    
    highStraightFlush = 
        new Hand(new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUBS), 
        new Card(CardRank.KING, CardSuit.CLUBS), 
        new Card(CardRank.ACE, CardSuit.CLUBS)}); 
  }

  @Test
  public void pairShouldBeatHighCard() {
    assertThat(pair.compareTo(highCard), is(-1));
  }
  
  @Test 
  public void flushShouldBeatHighCard() {
    assertThat(flush.compareTo(pair), is(-1));
  }
  
  @Test
  public void lowStraighShouldBeatFlush() {
    assertThat(lowStraight.compareTo(flush), is(-1));
  }
  
  @Test
  public void highStraightShouldBeatLowStraight() {
    assertThat(highStraight.compareTo(lowStraight), is(-1));
  }
  
  @Test
  public void threeOfAKindShouldBeatHighStraight() {
    assertThat(threeOfAKind.compareTo(highStraight), is(-1));
  }
  
  @Test
  public void lowStraightFlushShouldBeatThreeOfAKind() {
    assertThat(lowStraightFlush.compareTo(threeOfAKind), is(-1));
  }
  
  @Test
  public void highStraightFlushShouldBeatLowStraightFlush() {
    assertThat(highStraightFlush.compareTo(lowStraightFlush), is(-1));
  }

}
