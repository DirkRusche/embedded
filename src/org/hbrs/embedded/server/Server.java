package org.hbrs.embedded.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import org.hbrs.embedded.Spieler;

public class Server implements Runnable, Closeable {

  private boolean accept = true;
  private final List<Spieler> spielerList;
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
        Thread t = new Thread(remoteSpieler);
        t.start();

        Thread t2 = new Thread(() -> {
          try {
            t.join();
            spielerList.add(remoteSpieler);
            System.out.println(String.format("Spieler %s ist gejoint", remoteSpieler.getName()));
          }
          catch (InterruptedException e) {
            e.printStackTrace();
          }
        });
        t2.start();


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

  @Override
  public void close() throws IOException {
    server.close();
  }
}
