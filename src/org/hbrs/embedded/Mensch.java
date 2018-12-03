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

    if (input.equalsIgnoreCase("close")) {
      return Action.CLOSE;
    }
    else if (input.equalsIgnoreCase("check")){
      return Action.CHECK;
    }
    else if (input.equalsIgnoreCase("t11")){
      return Action.T11;
    }
    else if (input.equalsIgnoreCase("t12")){
      return Action.T12;
    }
    else if (input.equalsIgnoreCase("t13")){
      return Action.T13;
    }
    else if (input.equalsIgnoreCase("t21")){
      return Action.T21;
    }
    else if (input.equalsIgnoreCase("t22")){
      return Action.T22;
    }
    else if (input.equalsIgnoreCase("t23")){
      return Action.T23;
    }
    else if (input.equalsIgnoreCase("t31")){
      return Action.T31;
    }
    else if (input.equalsIgnoreCase("t32")){
      return Action.T32;
    }
    else {
      return Action.T33;
    }

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
