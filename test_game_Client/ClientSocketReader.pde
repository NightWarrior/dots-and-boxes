import java.io.*;
import java.net.*;
import java.util.*;

class ClientSocket extends Thread {
  Socket server;
  int gamePort;
  int playerNumber;
  DataInputStream inp;
  DataOutputStream out;
  String localhost;
  Boolean turn;

  ClientSocket() {
    try {
      Scanner s = new Scanner(new File("server.txt"));
      localhost = s.nextLine();
      System.out.println("Setting up Connection...");
      server = new Socket(localhost, 27000); // get unique port from server
      System.out.println("Connection Made...");

      inp = new DataInputStream(server.getInputStream());
      clientWriter.setOutStream(new DataOutputStream(server.getOutputStream()));

      playerNumber = inp.read();
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public void run() {
    turn = (clientPlayer == player);
    System.out.println("Player turn: "+turn);
    if (inp == null)
      System.out.println("Input stream is null");
    try {
      Boolean Say = true;
      while (true) {
        System.out.println("Turn start...");
        if (clientPlayer != player) {
          System.out.println("reading from server");
          int n=-1;
          n = inp.read();
          if (n==-1)
            System.out.println("Invalid read, didnt read anything");
          bg.hit(n);
          System.out.println("read: "+n);
          Say = true;
        } else {
          if (Say) {
            System.out.println("Make a move...");
            Say = false;
          }
        }
      }
    }
    catch(Exception e) {
      System.out.println("Failed to get data, recieved null.");
      System.out.println(e);
    }
  }


  public int gePlayerNumber() {
    return playerNumber;
  }

  public void closeSocket() {
    try {
      server.close();
      System.out.println("Connection Closed");
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public Boolean ready() {
    return true;
  }
}