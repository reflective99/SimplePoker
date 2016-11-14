package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;

import static org.hamcrest.core.Is.*;

import org.junit.Test;
import main.CardRank;
import main.CardSuit;
import main.Hand;
import main.Ranking;
import main.Card;

public class HandTest {
  
  Card[] highCard = new Card[] { new Card(CardRank.JACK, CardSuit.CLUBS), new Card(CardRank.NINE, CardSuit.HEARTS), new Card(CardRank.ACE, CardSuit.DIAMONDS) };
  Card[] pair = new Card[] { new Card(CardRank.FIVE, CardSuit.CLUBS), new Card(CardRank.FIVE, CardSuit.HEARTS), new Card(CardRank.NINE, CardSuit.DIAMONDS) };
  Card[] flush = new Card[] { new Card(CardRank.FIVE, CardSuit.CLUBS), new Card(CardRank.SEVEN, CardSuit.CLUBS), new Card(CardRank.NINE, CardSuit.CLUBS) };
  Card[] lowStraight = new Card[] { new Card(CardRank.ACE, CardSuit.CLUBS), new Card(CardRank.TWO, CardSuit.HEARTS), new Card(CardRank.THREE, CardSuit.DIAMONDS)};
  Card[] highStraight = new Card[] { new Card(CardRank.QUEEN, CardSuit.CLUBS), new Card(CardRank.KING, CardSuit.HEARTS), new Card(CardRank.ACE, CardSuit.DIAMONDS)};
  Card[] threeOfAKind = new Card[] { new Card(CardRank.FIVE, CardSuit.CLUBS), new Card(CardRank.FIVE, CardSuit.HEARTS), new Card(CardRank.FIVE, CardSuit.DIAMONDS) };
  Card[] lowStraightFlush = new Card[] { new Card(CardRank.ACE, CardSuit.CLUBS), new Card(CardRank.TWO, CardSuit.CLUBS), new Card(CardRank.THREE, CardSuit.CLUBS)};
  Card[] highStraightFlush = new Card[] { new Card(CardRank.QUEEN, CardSuit.CLUBS), new Card(CardRank.KING, CardSuit.CLUBS), new Card(CardRank.ACE, CardSuit.CLUBS)};



  Hand hand;
  
  @Before
  public void initialize() {
    Arrays.sort(highStraightFlush);
    hand = new Hand(highStraightFlush);
  }
  
  @Test
  public void shouldGetCorrectRanking() {
    hand.printHand();
    List<Integer> result = hand.getRankingsAsList();
    
    assertThat(result.get(0), is(Ranking.HIGH_CARD.getValue()));
    assertThat(result.get(1), is(Ranking.FLUSH.getValue()));
    assertThat(result.get(2), is(Ranking.STRAIGHT.getValue()));
    assertThat(result.get(3), is(Ranking.STRAIGHT_FLUSH.getValue()));

  }
  
}
