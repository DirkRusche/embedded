package org.hbrs.embedded.server;


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import org.hbrs.embedded.Helper;
import org.hbrs.embedded.Spieler;
import org.hbrs.embedded.SpielerImpl;
import org.hbrs.embedded.common.Karte;

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
    outputStream.println("Bitte gib deinen Namen ein (ein einzelner String)");

    this.name = scanner.next();

    outputStream.println("Hallo " + this.name);
    outputStream.println("Bitte warte, bis das Spiel beginnt");
  }

  @Override
  public void close() throws IOException {
    socket.close();
  }
}
