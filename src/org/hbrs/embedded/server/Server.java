package org.hbrs.embedded.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.hbrs.embedded.Spieler;

public class Server implements Runnable {

  List<Spieler> spielerList;

  public Server(List<Spieler> spielerList) {
    this.spielerList = spielerList;
  }


  @Override
  public void run() {
    try {
      ServerSocket server = new ServerSocket(1337);
      List<HandleSocket> sockets = new ArrayList<>();

      while (true) {

        Socket socket = server.accept();

        RemoteSpieler remoteSpieler = new RemoteSpieler(socket);
        new Thread(remoteSpieler).start();

        spielerList.add(remoteSpieler);

        System.out.println("added");
      }
    }
    catch (Exception e) {

    }
  }
}
