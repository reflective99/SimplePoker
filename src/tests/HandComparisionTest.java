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
        new Card(CardRank.JACK, CardSuit.CLUB), 
        new Card(CardRank.NINE, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMOND)});
    
    pair            = 
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUB), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.NINE, CardSuit.DIAMOND) });
    
    flush           =  
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUB), 
        new Card(CardRank.SEVEN, CardSuit.CLUB), 
        new Card(CardRank.NINE, CardSuit.CLUB)});
    
    lowStraight     = 
        new Hand(new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUB), 
        new Card(CardRank.TWO, CardSuit.HEARTS), 
        new Card(CardRank.THREE, CardSuit.DIAMOND)});
    
    highStraight    = 
        new Hand(new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUB), 
        new Card(CardRank.KING, CardSuit.HEARTS), 
        new Card(CardRank.ACE, CardSuit.DIAMOND)});
    
    threeOfAKind    = 
        new Hand(new Card[] { 
        new Card(CardRank.FIVE, CardSuit.CLUB), 
        new Card(CardRank.FIVE, CardSuit.HEARTS), 
        new Card(CardRank.FIVE, CardSuit.DIAMOND) });
    
    lowStraightFlush= 
        new Hand(new Card[] { 
        new Card(CardRank.ACE, CardSuit.CLUB), 
        new Card(CardRank.TWO, CardSuit.CLUB), 
        new Card(CardRank.THREE, CardSuit.CLUB)});
    
    highStraightFlush = 
        new Hand(new Card[] { 
        new Card(CardRank.QUEEN, CardSuit.CLUB), 
        new Card(CardRank.KING, CardSuit.CLUB), 
        new Card(CardRank.ACE, CardSuit.CLUB)}); 
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
  
  @Test
  public void shouldGetTheCorrectHandRanking() {
    assertThat(highCard.getMyRankValue()[0], is(1));
    assertThat(pair.getMyRankValue()[0], is(2));
    assertThat(flush.getMyRankValue()[0], is(3));
    assertThat(lowStraight.getMyRankValue()[0], is(4));
    assertThat(highStraight.getMyRankValue()[0], is(4));
    assertThat(threeOfAKind.getMyRankValue()[0], is(5));
    assertThat(lowStraightFlush.getMyRankValue()[0], is(6));
    assertThat(highStraightFlush.getMyRankValue()[0], is(6));
  }
  
}
