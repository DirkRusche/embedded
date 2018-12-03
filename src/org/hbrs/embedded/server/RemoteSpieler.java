package org.hbrs.embedded.server;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import org.hbrs.embedded.Helper;
import org.hbrs.embedded.Spieler;
import org.hbrs.embedded.common.Karte;

public class RemoteSpieler implements Spieler, Runnable {

  private Socket socket;
  private Scanner scanner;
  private PrintStream outputStream;
  private InputStream inputStream;

  private String name;

  public RemoteSpieler(Socket socket) throws IOException {
    this.socket = socket;
    this.inputStream = socket.getInputStream();
    this.scanner = new Scanner(inputStream);
    this.outputStream = new PrintStream(socket.getOutputStream());
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
  public String getName() {
    return name;
  }

  public String toString() {
    return getName();
  }

  @Override
  public void run() {
    outputStream.println("Verbindung erfolgreich hergestellt");
    outputStream.println("Bitte gib deinen Namen ein (ein einzelner String)");

    this.name = scanner.next();

    outputStream.println("Hallo " + this.name);
  }
}
