import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Thread;

class ClientModel { // TODO: Return streams and ports
  Socket clientSocket;
  DataInputStream inp;
  DataOutputStream out;

  public ClientModel(Socket s) {
    try {
      clientSocket = s;
      inp = new DataInputStream(s.getInputStream());
      out = new DataOutputStream(s.getOutputStream());
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


  public void write(int s) {
    try {
      out.write(s);
      out.flush();
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public int read() {
    int s = -1;
    try {
      s = inp.readInt();
    }
    catch(Exception e) {
      System.out.println(e);
    }
    return s;
  }

  public Boolean readBoolean() {
    Boolean b = false;
    try {
      return inp.readBoolean();
    }
    catch(Exception e) {
      System.out.println(e);
    }
    return b;
  }
}