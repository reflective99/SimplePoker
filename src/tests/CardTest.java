package tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import org.junit.Test;

import main.CardRank;
import main.CardSuit;
import main.Card;

public class CardTest {

  private static final CardRank[] ALL_RANKS = CardRank.values();
  private static final CardSuit[] ALL_SUITS = CardSuit.values();
  
  @Test
  public void constructorShouldCreateCorrectCards() {
    try {
      for(CardSuit suit : ALL_SUITS) {
        for(CardRank rank : ALL_RANKS) {
          Card card = new Card(rank, suit);
          assertThat(card.getRank(), is(rank));
          assertThat(card.getSuit(), is(suit));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @Test
  public void shouldCorrectlyCompareCardRanks() {
    
    Card ACE = new Card(CardRank.ACE, CardSuit.DIAMOND);
    /** Less than */
    for(int i = 2; i < ALL_RANKS.length; i++) {
      Card lower  = new Card(ALL_RANKS[i-1], ALL_SUITS[1]);
      Card higher = new Card(ALL_RANKS[i], ALL_SUITS[1]);
      assertThat(lower.compareTo(higher), is(-1));
      /** Ace is higher than all cards */
      assertThat(higher.compareTo(ACE), is(-1));
      assertThat(lower.compareTo(ACE), is(-1));
    }
    
    /** Higher than */
    for(int i = 2; i < ALL_RANKS.length; i++) {
      Card lower  = new Card(ALL_RANKS[i-1], ALL_SUITS[1]);
      Card higher = new Card(ALL_RANKS[i], ALL_SUITS[1]);
      assertThat(higher.compareTo(lower), is(1));
      /** Ace is higher than all cards */
      assertThat(ACE.compareTo(lower), is(1));
      assertThat(ACE.compareTo(higher), is(1));
    }
    
    /** Equal to */
    for(int i = 0; i < ALL_RANKS.length; i++) {
      Card c1 = new Card(ALL_RANKS[i], ALL_SUITS[2]);
      Card c2 = new Card(ALL_RANKS[i], ALL_SUITS[2]);
      assertThat(c1.compareTo(c2), is(0));
    }
    
  }

}
