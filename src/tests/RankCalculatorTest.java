package tests;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import main.InputReader;
import main.Player;
import main.RankCalculator;

@RunWith(Parameterized.class)
public class RankCalculatorTest {

  @SuppressWarnings("unused")
  private File testFile;
  private RankCalculator ranker;
  private InputReader inputReader;
  private List<Integer> winners;
  private List<Integer> expectedWinners;

  @Parameters
  public static Collection<Object[]> data() {
    File folder = new File(System.getProperty("user.dir")+"/src/tests/files");
    File[] files = folder.listFiles();
    List<Object[]> parameters = new ArrayList<Object[]>(files.length);
    for(File f : files){
      parameters.add(new Object[] { f });
    }
    return parameters;
  }

  public RankCalculatorTest(File testFile) throws FileNotFoundException {
    this.testFile = testFile;
    System.out.println("=============================================================");
    System.out.println("Testing for file " + testFile.getName());
    ranker = new RankCalculator();
    System.setIn(new FileInputStream(testFile));
    Scanner sc = new Scanner(System.in);
    inputReader = new InputReader(sc);
    List<Player> sortedPlayers = ranker.rankWinners(inputReader.getPlayerHands());
    ranker.submitPlayers(sortedPlayers);
    winners = ranker.getWinners();
    System.setIn(new FileInputStream(testFile));
    sc = new Scanner(System.in);
    String expected = null;
    while(sc.hasNextLine()){
      expected = sc.nextLine();
    }
    String[] splitWinners = expected.split(" ");
    expectedWinners = new ArrayList<Integer>(splitWinners.length);
    for(String s : splitWinners) {
      expectedWinners.add(Integer.parseInt(s));
    }
    sc.close();
    System.out.println("Expected Winners: " + expectedWinners);
    System.out.println("Winners Ranked: " + winners);
  }

  @Test 
  public void shouldReturnAtLeastOneWinner() {
    assertTrue(ranker.getWinners().size()>0);
  }
  
  @Test
  public void shouldReturnCorrectWinners() {
    for(int i = 0; i < winners.size(); i++){
      assertThat(winners.get(i), is(expectedWinners.get(i)));
    }
    System.out.println("=============================================================");
    System.out.println();
  }
  

}
