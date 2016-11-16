package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.hamcrest.core.Is.*;

import org.junit.Before;
import org.junit.Test;

import main.InputReader;

public class InputReaderTest {

  InputReader reader;
  int[] players = {0, 1, 2};
  List<List<String>> hands = new ArrayList<List<String>>();
 

  @Before
  public void initialize() {
    List<String> p1 = Arrays.asList(new String[] {"2c", "As", "4d"});
    List<String> p2 = Arrays.asList(new String[] {"Kd", "5h", "6c"});
    List<String> p3 = Arrays.asList(new String[] {"Jc", "Jd", "9s"});
    hands.add(p1); hands.add(p2); hands.add(p3); 

    String inputString = "3\n0 2c As 4d\n1 Kd 5h 6c\n2 Jc Jd 9s\n";
    InputStream in = new ByteArrayInputStream(inputString.getBytes());
    System.setIn(in);
    Scanner sc = new Scanner(System.in);
    reader = new InputReader(sc);
  }

  @Test (expected = IllegalArgumentException.class)
  public void shouldThrowExceptionForNullInput() {
    new InputReader(null);
  }

  @Test 
  public void shouldReadInTheCurrentNumPlayers() {
    int numPlayers = reader.getNumPlayers();
    assertThat(numPlayers, is(3));
  }

  @Test
  public void shouldProduceTheCorrectSizedPlayerMap() {
    int mapSize = reader.getPlayerHands().size();    
    assertThat(mapSize, is(3));
  }

  @Test
  public void mapEntriesShouldHaveTheCorrectHands() {
    Map<Integer, List<String>> playerMap = reader.getPlayerHands();
    for(int i = 0; i < players.length; i++){
      assertThat(playerMap.containsKey(players[i]), is(true));
      assertThat(playerMap.get(players[i]), is(hands.get(i)));
    }
  }

}
