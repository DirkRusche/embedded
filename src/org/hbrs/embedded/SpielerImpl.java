package org.hbrs.embedded;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import org.hbrs.embedded.common.Karte;

public abstract class SpielerImpl implements Spieler {

  protected Scanner scanner;
  protected PrintStream outputStream;

  public SpielerImpl(InputStream inputStream, OutputStream outputStream) {
    this.scanner = new Scanner(inputStream);
    this.outputStream = new PrintStream(outputStream);
  }

  @Override
  public Action getAction(
      Karte[] hand, Karte[] tisch
  ) {
    outputStream.println("Deine Karten sind:");
    outputStream.println(Arrays.toString(hand));
    outputStream.println("Karten auf dem Tisch:");
    outputStream.println(Arrays.toString(tisch));
    outputStream.println("Eine Entscheidung ist notwendig (check, close, t11, t12, t13, ...)");


    String input = scanner.next();

    return Helper.getAction(input);
  }

  @Override
  public Decision getDecision(Karte[] hand) {
    outputStream.println("Willst du die Hand behalten?");
    outputStream.println(Arrays.toString(hand));

    String input = scanner.next();

    if (input.equals("y")) {
      return Decision.YES;
    }
    else {
      return Decision.NO;
    }
  }

  @Override
  public void notify(String text) {
    outputStream.println(text);
  }

  @Override
  public String toString() {
    return getName();
  }

}
