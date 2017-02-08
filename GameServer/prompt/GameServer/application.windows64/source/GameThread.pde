import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Thread;

class GameThread extends Thread { // TODO: implement run()
  int port1;
  int port2;
  DataInputStream inp1;
  DataInputStream inp2;
  DataOutputStream out1;
  DataOutputStream out2;
  int playerNumber1;
  int playerNumber2;
  public GameThread(int p1, int p2, DataInputStream inp1, DataInputStream inp2, DataOutputStream out1, DataOutputStream out2) {
    port1 = p1;
    port2 = p2;
    this.inp1 = inp1;
    this.inp2 = inp2;
    this.out1 = out1;
    this.out2 = out2;
    playerNumber1 = new Random().nextInt(2);
    playerNumber2 = 1- playerNumber1;

    try {
      out1.write(playerNumber1);
      out2.write(playerNumber2);
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }
}

/*
  ServerSocket server;
 Socket clientSocket;
 int client1PlayerNumber;
 int client2PlayerNumber;
 
 public GameThread() {
 client1PlayerNumber = new Random().nextInt(2);
 client2PlayerNumber = 1- client1PlayerNumber;
 inp1 = inp2 = null;
 out1 = out2 = null;
 try {
 server = new ServerSocket(23456);
 }
 catch(Exception e) {
 System.out.println(e);
 }
 }
 
 public void run() {
 try {
 System.out.println("Waiting for client");
 clientSocket = server.accept();
 System.out.println("Client 1 connected.");
 
 inp1 = new DataInputStream(clientSocket.getInputStream());
 out1 = new DataOutputStream(clientSocket.getOutputStream());
 
 out1.write(client1PlayerNumber);
 
 clientSocket.close();
 exit();
 }
 catch(Exception e) {
 System.out.println(e);
 }
 }
 } */