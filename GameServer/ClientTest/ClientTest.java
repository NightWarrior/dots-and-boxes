import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTest{
	public static void main(String args[]){
		try{
			System.out.println("Setting up Connection...");
			Socket s1 = new Socket("127.0.0.1", 23456);
			System.out.println("Connection Made...");
			//
			// DataInputStream inp = new DataInputStream(s1.getInputStream());
			// DataOutputStream out = new DataOutputStream(s1.getOutputStream());
			//
			// Scanner reader = new Scanner(System.in);
			// String str;
			//
			// str = inp.readUTF();
			// System.out.println(str);
			//
			// str = reader.nextLine();
			// out.writeUTF(str);
			//
			//
			// out.close();
			// inp.close();
			s1.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
