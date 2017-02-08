import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.io.*; 
import java.net.*; 
import java.util.*; 
import java.io.*; 
import java.net.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class test_game_Client extends PApplet {

DotGrid dg;
BoxGrid bg;
ScoreGrid sg;
Score score;

int player;
int clientPlayer;
int gridSize;

ClientSocket client;
ClientWriter clientWriter;

LineRect l1, l2;
public void setup() {      // TODO: Display dialogues for player to wait his turn and do his turn
  
  background(255);
  textSize(26);

  textSize(25);
  fill(0);
  text("Connecting to Server...", 0, 0);

  clientWriter = new ClientWriter();
  while (clientWriter.ready() == null);
  client = new ClientSocket();
  client.start();
  while (client.ready() == null); // halting until the client thread has initialized.
  clientPlayer = client.gePlayerNumber();
  System.out.println("You got Player Number: "+clientPlayer);
  player = 0;

  gridSize = 7;

  dg  = new DotGrid(gridSize);
  bg = new BoxGrid(gridSize);
  sg = new ScoreGrid(gridSize);
  score = new Score();
}


public void draw() {
  background(255);
  bg.display();
  dg.display();
  sg.display();
  score.display();
}


public void mousePressed() {
  if (player == clientPlayer) {
    bg.checkHit();
  }
}

public void keyPressed() {
  if (key == 'Q' || key == 'q') {
    client.closeSocket();
    exit();
  }
}
class BoxGrid {
  LineRect[] linesh;
  LineRect[] linesv;
  int distanceFactor;
  int row;
  int col;

  BoxGrid(int size) {
    int count = 0;
    row = col = size;
    distanceFactor = (width/(row+1));

    linesh = new LineRect[(row-1)*(col)];
    linesv = new LineRect[(row)*(col-1)];

    for (int i =1; i<=row-1; i++)
      for (int j =1; j<=col; j++) {
        linesh[count++] = new LineRect(i*distanceFactor, j*distanceFactor, distanceFactor, 0);
      }

    count = 0;
    for (int i =1; i<=row; i++)
      for (int j =1; j<=col-1; j++) {
        linesv[count++] = new LineRect(i*distanceFactor, j*distanceFactor, distanceFactor, 1);
      }
  }

  public void display() {
    for (int i =0; i<linesh.length; i++) {
      linesh[i].display();
      linesv[i].display();
    }
  }

  public void checkHit() {
    for (int i =0; i<linesh.length; i++) {
      if (linesh[i].checkHit()) {
        //clientWriter.sendHit(100+i); // supposing 100<=x<200 number are horizontal lines
        linesh[i].mark(100+i);
      }
      if (linesv[i].checkHit()) {
        //clientWriter.sendHit(200+i); // supposing 200<=x<300 number are vertical lines
        linesv[i].mark(200+i);
      }
    }
  }

  public void hit(int pos) {
    System.out.println("Received: "+pos);
    if (pos>=100 && pos<200) { // meaning if position is 100s  
      linesh[pos-100].mark(-1);
    } else if (pos>=200 && pos<300) {  
      linesv[pos-200].mark(-1);
    }
  }

  public boolean checkBox(int num) {
    int hnum = num;
    if (num!=0 && num!=1)
      hnum+=num/(row-1);

    if (linesh[hnum].isMarked() && linesh[hnum+1].isMarked()
      && linesv[num].isMarked() && linesv[num+col-1].isMarked())
      return true;

    return false;
  }
}
class Circle{
  int x;
  int y;
  int radius;
  Circle(int _x, int _y){
    x=_x;
    y=_y;
    radius=5;
  }
  
  public void display(){
    fill(90);
    ellipse(x, y, radius*2, radius*2);
    noFill();
  }
}




class ClientSocket extends Thread {
  Socket server;
  int gamePort;
  int playerNumber;
  DataInputStream inp;
  DataOutputStream out;
  String localhost = "192.168.168.61";
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





class ClientWriter extends Thread {
  DataOutputStream out;

  ClientWriter() {
  }

  public void run() {
  }
  public void setOutStream(DataOutputStream out){
    this.out = out;
  }

  public void sendHit(int pos) {
    if (out == null)
      System.out.println("Output stream is null");
    try {
      System.out.println("Sending: "+pos);
      //out.write(pos);
      out.writeInt(pos);
      out.flush();
      System.out.println("Sent: "+pos);
    }
    catch(Exception e) {
      System.out.println(e);
    }
  }


  public Boolean ready() {
    return true;
  }
}
class DotGrid{
  Circle[] dots;
  int distanceFactor;
  int row;
  int col;
  
  DotGrid(int size){
    int count = 0;
    row = col = size;
    distanceFactor = (width/(row+1));
    
    dots = new Circle[row*col];
    for(int i =1; i<=row; i++)
      for(int j =1; j<=col; j++){
        dots[count++] = new Circle(i*distanceFactor, j*distanceFactor);
      }
  }
  
  public void display(){
   for(int i =0; i<dots.length; i++)
     dots[i].display();
  }
}
class LineRect {
  int x;
  int y;
  int w;
  int h;
  float angle;
  int clr;
  boolean marked;

  LineRect(int _x, int _y, int len, int rot) {
    x = _x;
    y = _y-3;
    h=5;
    w=len;
    clr = color(210);
    marked= false;
    if (rot == 0)
      angle = 0;
    else if (rot==1) {
      angle = PI/2;
      x+=3;
      y+=3;
    }
  }


  public void display() {
    noStroke();
    if (angle==PI/2) {
      pushMatrix();
      fill(clr);
      translate(x, y);
      rotate(angle);
      rect(0, 0, w, h);
      noFill();
      popMatrix();
    } else {
      fill(clr);
      rect(x, y, w, h);
      noFill();
    }
    stroke(1);
  }

  public void mark(int pos) {
    marked = true;
    if (player == 1)
      clr = color(200, 10, 10);
    else
      clr = color(10, 10, 200);
    if (!sg.checkScore()) {
      try {
        if (pos != -1)
          clientWriter.sendHit(pos);
      }
      catch(Exception e) {
        System.out.println(e);
      }
      player = (player+1)%2;
      System.out.println("Player changed to: "+player);
      System.out.println("ClientPlayer is:   "+clientPlayer);
    } else {
      try {
        if (pos != -1)
          clientWriter.sendHit(pos+200);
      }
      catch(Exception e) {
        System.out.println(e);
      }
    }
  }

  public boolean checkHit() {
    if (marked==true)
      return false;
    if (angle==0) {
      if (mouseX>=x+6 && mouseX<=x+w-6 && mouseY>=y-1 && mouseY<=y+h+1)
        return true;
    } else {
      boolean rotCheck = false;
      if (mouseX>=x-h-1 && mouseX<=x+1 && mouseY>=y+6 && mouseY<=y+w-6)
        rotCheck = true;
      return rotCheck;
    }
    return false;
  }

  public boolean isMarked() {
    return marked;
  }
}
class Score {
  int p1, p2;
  Score() {
    p1 = 0;
    p2 = 0;
  }

  public void display() {
    fill(color(10, 10, 200));
    textAlign(LEFT);
    text(p1, width/2-width/10, height/18);
    fill(color(200, 10, 10));
    textAlign(RIGHT);
    text(p2, width/2+width/10, height/18);
    noFill();


    textAlign(LEFT);
    if (player == clientPlayer) {
      fill(color(10, 10, 200));
      text("Your turn", 20, height/18);
    } else {
      fill(color(200, 10, 10));
      text("Opponent's turn", 20, height/18);
    }
    noFill();
  }

  public void increaseScore(int n) {
    if (n==0)
      p1++;
    else
      p2++;
  }
}
class ScoreBall {
  int x, y, r;
  int clr;
  boolean marked;
  ScoreBall(int _x, int _y, int _r) {
    x = _x;
    y = _y;
    r = _r;
    clr = color(240);
    marked = false;
  }

  public void display() {
    fill(clr);
    noStroke();
    ellipse(x, y, r*2, r*2);
    noFill();
    stroke(0);
  }

  public void mark() {
    if (marked)
      return;
    marked = true;
    score.increaseScore(player);
    if (player == 1)
      clr = color(200, 10, 10);
    else
      clr = color(10, 10, 200);
  }
  
  public boolean isMarked(){
    return marked;
  }
}
class ScoreGrid {
  ScoreBall [] balls;
  //boolean [][] marks; 
  int row;
  int col;
  ScoreGrid(int size) {
    row = col = size-1;
    balls = new ScoreBall[row*col];
    //marks = new boolean[row][col];
    int count=0;
    int distanceFactor = width/(size+1);


    for (int i =1; i<=row; i++)
      for (int j = 1; j<=col; j++) {
        balls[count++] = new ScoreBall(distanceFactor/2+i*distanceFactor, distanceFactor/2+j*distanceFactor, (int)(distanceFactor/4));
        //marks[i-1][j-1] = false;
      }
  }

  public void display() {
    for (int i =0; i<balls.length; i++)
      balls[i].display();
  }

  public boolean checkScore() {
    int count = 0;
    boolean scored = false;
    for (int i =0; i<row; i++) {
      for (int j =0; j<col; j++) {
        if (!balls[count].isMarked()) {
          if (bg.checkBox(count)) {
            scored = true;
            balls[count].mark();
          }
        }

        count++;
      }
    }
    return scored;
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "test_game_Client" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
