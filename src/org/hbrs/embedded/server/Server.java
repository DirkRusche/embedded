package org.hbrs.embedded.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.hbrs.embedded.Spieler;

public class Server implements Runnable {

  private boolean accept = true;
  private List<Spieler> spielerList;
  private ServerSocket server;

  public Server(List<Spieler> spielerList) {
    this.spielerList = spielerList;
  }


  @Override
  public void run() {
    try {
      server = new ServerSocket(1337);

      while (accept) {

        Socket socket = server.accept();

        if (!accept) {
          socket.close();
          break;
        }

        RemoteSpieler remoteSpieler = new RemoteSpieler(socket);
        new Thread(remoteSpieler).start();

        spielerList.add(remoteSpieler);

        System.out.println("added");
      }
      server.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void stopAccept() {
    accept = false;
  }
}
