import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.Thread;

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