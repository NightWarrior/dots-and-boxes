import java.io.*;
import java.net.*;
import java.util.*;

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