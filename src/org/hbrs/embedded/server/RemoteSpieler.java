package org.hbrs.embedded.server;


import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import org.hbrs.embedded.SpielerImpl;

public class RemoteSpieler extends SpielerImpl implements Runnable, Closeable {

  private Socket socket;

  private String name;

  public RemoteSpieler(Socket socket) throws IOException {
    super(socket.getInputStream(), socket.getOutputStream());
    this.socket = socket;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void run() {
    outputStream.println("Verbindung erfolgreich hergestellt");

    while (this.name == null) {
      outputStream.println("Bitte gib deinen Namen ein (ein einzelner String)");
      String name = scanner.next();

      if (name.length() < 3 || name.length() > 20) {
        outputStream.println("Wähle einen Namen zwischen 3 und 20 Zeichen");
      }
      else {
        this.name = name;
      }
    }
    outputStream.println("Hallo " + this.name);
    outputStream.println("Bitte warte, bis das Spiel beginnt");
  }

  @Override
  public void close() throws IOException {
    socket.close();
  }
}
