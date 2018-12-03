package org.hbrs.embedded;

import java.io.IOException;
import java.net.ServerSocket;
import org.hbrs.embedded.server.Server;

public class Main {

  public static void main(String[] args) throws IOException {

    new Server().start();

  }

  void nothing() {


    Spieler spieler1 = new Mensch("Kevin");
    Spieler spieler2 = new Mensch("Dirk");

    Spiel spiel = new Spiel(spieler1, spieler2);

    spiel.start();

  }

}
