package org.hbrs.embedded;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.hbrs.embedded.server.Server;

public class Main {

  public static void main(String[] args) throws IOException {

    List<Spieler> spielerList = new ArrayList<>();

    Server server = new Server(spielerList);
    new Thread(server).start();

    Scanner sc = new Scanner(System.in);
    while (sc.hasNext()) {
      if (sc.next().equals("s")) { break; }
    }

    server.stopAccept();
    Spiel spiel = new Spiel(spielerList);

    spiel.start();

    for(Spieler spieler : spielerList) {
      if (spieler instanceof Closeable) {
        Closeable c = (Closeable) spieler;
        c.close();
      }
    }
    server.close();

  }
}
