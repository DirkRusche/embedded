package org.hbrs.embedded.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class HandleSocket implements Runnable {

  private Socket socket;
  private Scanner scanner;
  private PrintStream outputStream;

  public HandleSocket(Socket socket) throws IOException {
    this.socket = socket;
    this.scanner = new Scanner(socket.getInputStream());
    this.outputStream = new PrintStream(socket.getOutputStream());
  }

  @Override
  public void run() {
    System.out.println("opened");

    while (scanner.hasNextLine()) {
      outputStream.println(scanner.nextLine());
    }

    System.out.println("closed");
  }
}
