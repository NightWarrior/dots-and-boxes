import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Thread;

class ClientConnection { // TODO: Return streams and ports
  ServerSocket server;
  Socket clientSocket;
  DataInputStream inp;
  DataOutputStream out;
  int port;
  int clientPlayerNumber;
  String localhost = "127.0.0.1";

  public ClientConnection(int port) {
    this.port = port; 
    try {
      server = new ServerSocket(port);
      server.setReuseAddress(true);
      clientSocket = server.accept();
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public void closeConnection() {
    try {
      clientSocket.close();
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }
}