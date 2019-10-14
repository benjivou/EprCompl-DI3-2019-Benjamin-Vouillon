package TCP.Serveur;

import java.io.IOException;

public class TCPServerTimeout extends TCPServerBuilder implements Runnable {
    public void run() {
        try {

            setSocket(); ss.setSoTimeout(3000);
            while(true) {
                s = ss.accept();
                new Thread(new ServerTimeout(s)).start();
            }
        }
        catch(IOException e)
        { System.out.println("IOException TCPServerTimeout"); }
    }

}