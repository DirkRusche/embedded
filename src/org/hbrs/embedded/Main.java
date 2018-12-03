package org.hbrs.embedded;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import org.hbrs.embedded.server.Server;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {

    List<Spieler> spielerList = new ArrayList<>();

    Server server = new Server(spielerList);
    new Thread(server).start();

    Thread.sleep(20 * 1000);


    Spiel spiel = new Spiel(spielerList);

    spiel.start();

  }

}
