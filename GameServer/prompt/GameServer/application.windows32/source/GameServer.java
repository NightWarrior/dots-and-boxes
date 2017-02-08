import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.net.*; 
import java.util.*; 
import java.io.*; 
import java.lang.Thread; 
import java.util.*; 
import java.net.*; 
import java.util.*; 
import java.io.*; 
import java.lang.Thread; 
import java.util.ArrayList; 
import java.net.*; 
import java.util.*; 
import java.io.*; 
import java.lang.Thread; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameServer extends PApplet {

ServerThread server;
   
Display d ;
  public void setup(){
  
  background(255);
  d = new Display();
  for(int i=0;i<20;i++){
  d.print("server"+i);
  }
  
 
}
public void draw(){
  d.show();
  
}
public void keyPressed(){
  d.print("Hello");
 if(key=='Q' || key=='q')
   exit();
}





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


class Display {
  Queue<String> qq;
  //PriorityQueue<String> qq;
  int i=0;
  public Display() {
    qq = new LinkedList<String>();
  }
  public void print(String s) {
    qq.add(s);
    i++;
    if(i>32){
      qq.remove();
    }
  }
  public void show() {
    fill(255);
    rect(50, 50, 700, 500);
    fill(0);
    int i = 0;
    for (String ss : qq) {
      text(ss, 60, 60+i, 720, 520);
      i+=15;
    }
  }
}





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


/*class LobbyThread {
  ArrayList<ClientConnection> clients;
  ArrayList<ClientConnection> pair;
  ArrayList<GameThread> games;
  

  LobbyThread() {
    clients = new ArrayList<ClientConnection>();
    games = new ArrayList<GameThread>();
  }

  public void connectPlayer(int nextPort) {
    ClientConnection temp = new ClientConnection(nextPort);
    clients.add(temp);
  }
  
 // private void checkNewGame(ClientConnection){
    
  //}
//}*/





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
  public void settings() {  size(800, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameServer" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
