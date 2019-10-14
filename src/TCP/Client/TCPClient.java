package TCP.Client;

import TCP.Base.TCPInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient extends TCPInfo implements Runnable {
	 private Socket s; // the client socket
	 private InetSocketAddress isA; // the remote address
	 /** The builder. */
	 public TCPClient() {
			 s = null;
			 isA = new InetSocketAddress("localhost",8085);
		 }
		 /** The main method for threading. */
		 public void run() {
		 try {
			 System.out.println("TCPClient launched ...");
			 s = new Socket(isA.getHostName(), isA.getPort());
			 System.out.println("Hello, the client is connected");
			 s.close();
		 }
		 catch(IOException e)
		 { 
			 System.out.println("IOException TCPClient"); 
		 }
	 }
 } 