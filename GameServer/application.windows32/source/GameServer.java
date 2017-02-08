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
import java.lang.Thread; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameServer extends PApplet {

LobbyThread lobby;
Display d;

public void setup() {
  
  background(255);
  d = new Display();
  lobby = new LobbyThread();
  lobby.start();
}

public void draw() {
  d.show();
}

public void keyPressed() {
  if (key=='Q' || key=='q')
    exit();
}





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
  ClientModel p1;
  ClientModel p2;
  int totalScore;
  final int maxScore = 36;

  public GameThread(ClientModel p1, ClientModel p2) {
    totalScore = 0;
    this.p1 = p1;
    this.p2 = p2;
  }

  public void run() {
    int p2Read;
    int p1Read;     
    Boolean score1;
    Boolean score2;
    try {
      while (true) {
        do {
          System.out.println("Waiting for P1 to send move");
          d.print("Waiting for P1 to send move");
          p1Read = (int)p1.read();     
          //score1 = p1.readBoolean();
          //System.out.println("Read: "+p1Read);
          //System.out.println("Player 1 sent move: "+p1Read);
          d.print("Player 1 sent move: "+p1Read);
          if (p1Read>=300) {
            p1Read-=200;
            score1 = true;
            totalScore++;
          } else
            score1 = false;

          p2.write(p1Read);
        } while (score1 == true);
        //System.out.println("Sent: "+p1Read);
        do {
          //System.out.println("Waiting for P2 to send move");
          d.print("Waiting for P2 to send move");
          p2Read = (int)p2.read();
          //score2 = p2.readBoolean();
          //System.out.println("Read: "+p2Read);
          //System.out.println("Player 2 sent move: "+p2Read);
          d.print("Player 2 sent move: "+p2Read);
          if (p2Read>=300) {
            p2Read-=200;
            score2 = true;
            totalScore++;
          } else
            score2 = false;
          p1.write(p2Read);
        } while (score2 == true);
        //System.out.println("Sent: "+p2Read);
        if (totalScore>=maxScore || p1Read == -1 || p2Read == -1){ // CLOSE GAME HERE, RE-LOOK, REDO TELLING WINNER!
          //System.out.println("Game over");
          d.print("Game over");
          break;
        }
      }
      p1.closeConnection();
      p2.closeConnection();
    }
    catch(Exception e) {
      p1.closeConnection();
      p2.closeConnection();
      System.out.println(e);
    }
  }
}




class LobbyThread extends Thread {
  ServerSocket server;
  Socket clientSocket1;
  Socket clientSocket2;
  int playerNumber1;
  int playerNumber2;


  LobbyThread() {
    try {
      server = new ServerSocket(27000);
      server.setReuseAddress(true);
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }

  public void run() {
    int clientCounter = 0;
    try {
      while (true) {
        playerNumber1 = new Random().nextInt(2);
        playerNumber2 = 1- playerNumber1;


        //System.out.println("Waiting for client: "+clientCounter);
        d.print("Waiting for client: "+clientCounter);
        clientSocket1 = server.accept();
        //System.out.println("Client "+(clientCounter++)+" connected.");
        d.print("Client "+(clientCounter++)+" connected.");
        (new DataOutputStream(clientSocket1.getOutputStream())).write(playerNumber1);

        //System.out.println("Waiting for client: "+clientCounter);
        d.print("Waiting for client: "+clientCounter);
        clientSocket2 = server.accept();
        //System.out.println("Client "+(clientCounter++)+" connected.");
        d.print("Client "+(clientCounter++)+" connected.");
        (new DataOutputStream(clientSocket2.getOutputStream())).write(playerNumber2);

        if (playerNumber1 == 0)
          new GameThread(new ClientModel(clientSocket1), new ClientModel(clientSocket2)).start();
        else
          new GameThread(new ClientModel(clientSocket2), new ClientModel(clientSocket1)).start();
          //System.out.println("Game started for clients "+(clientCounter-2)+" and "+(clientCounter-1)); 
        d.print("Game started for clients "+(clientCounter-2)+" and "+(clientCounter-1));
      }
    }
    catch(Exception e) {
      System.out.println(e);
      return;
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
