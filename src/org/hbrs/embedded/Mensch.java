package org.hbrs.embedded;

import java.util.Arrays;
import java.util.Scanner;
import org.hbrs.embedded.common.Karte;

public class Mensch implements Spieler {

  private final Scanner sc;
  private final String name;

  public Mensch(String name) {
    this.name = name;
    this.sc = new Scanner(System.in);
  }

  @Override
  public Action getAction(
      Karte[] hand, Karte[] tisch
  ) {
    System.out.println("Welche Aktion willst du vornehmen? Hand, Tisch");
    System.out.println(Arrays.toString(hand));
    System.out.println(Arrays.toString(tisch));
    String input = sc.next();

    return Helper.getAction(input);

  }

  @Override
  public Decision getDecision(Karte[] hand) {
    System.out.println("Willst du die Hand behalten?");
    System.out.println(Arrays.toString(hand));

    String input = sc.next();

    if (input.equals("y")) {
      return Decision.YES;
    }
    else {
      return Decision.NO;
    }
  }

  @Override
  public void notify(String text) {

  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return getName();
  }
}
