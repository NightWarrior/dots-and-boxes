import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Thread;

class ServerThread extends Thread {
  ServerSocket server;
  Socket clientSocket;
  DataOutputStream out;
  int nextPort;

  public ServerThread() {
    out = null;
    nextPort = 27001;
    try {
      server = new ServerSocket(27000); // using 27000 for the basic server connection port
      server.setReuseAddress(true);
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public void run() {
    try {
      while (true) {
        System.out.println("Waiting for client: "+nextPort);
        clientSocket = server.accept();
        System.out.println("Client 1 connected.");

        out = new DataOutputStream(clientSocket.getOutputStream());

        out.write(nextPort);
        nextPort++;

        clientSocket.close();
        server = new ServerSocket(27000);
      }
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }
}