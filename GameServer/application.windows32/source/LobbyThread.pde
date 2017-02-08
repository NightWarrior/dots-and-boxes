import java.util.ArrayList;
import java.lang.Thread;
import java.util.*;

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