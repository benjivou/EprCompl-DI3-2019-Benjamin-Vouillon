package handlerSender;

import TCP.Serveur.TCPServerLOrderAlwaysOn;
import fr.superesi.superapp.Order;


import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static Const.Const.*;

/**
 * @brief the Goal of this handler is to control the serveur
 * thread and restart the server after each message receive
 */
public class HandlerReceiver extends Thread implements Runnable{

    public static final String TAG = "HandlerReceiver";



    private ConcurrentLinkedQueue<Order> buffer;
    private long lastCreation;
    private AtomicInteger lastElementsSeen; // the position of the last eleemnt seen
    private int numTry;
    private AtomicBoolean shouldIWork; // true if the handler work, false to kill the handler
    private Thread th;
    private AtomicBoolean messageRemove;
    /**
     *
     *
     */
    public HandlerReceiver(){
        this.buffer = new ConcurrentLinkedQueue<Order>();
        this.lastCreation = System.currentTimeMillis();
        this.numTry = 0;
        this.lastElementsSeen = new AtomicInteger(0);
        this.shouldIWork = new AtomicBoolean(true);
        this.th = new Thread(new TCPServerLOrderAlwaysOn(this.buffer));
        this.messageRemove = new AtomicBoolean(false);
        th.start();

        System.out.println(TAG + " The handler is set! ");
        this.start();
    }
    @Override
    public void run() {
        while (this.shouldIWork.get()){
            // When u receive something

            // if the thread is kill
            if (System.currentTimeMillis()-this.lastCreation > WAIT ){
                System.out.println(TAG + "U make me wait so long");
                this.numTry ++;

                // if we try more than 3 times we kill the handler
                if (numTry >TRY ) {
                    this.shouldIWork.set(false);
                    break;
                }

                reStartServer();


            }

            // there is something new in the queue so restart a new server
            if (this.lastElementsSeen.get() > this.buffer.size() ||
                    (this.messageRemove.get() && this.lastElementsSeen.get() < this.buffer.size())){
                this.messageRemove.set(false);
                this.lastElementsSeen.addAndGet(this.buffer.size());
                System.out.println(TAG + this.toString());
                // re-open the connexion

                reStartServer();

                this.numTry = 0 ;


            }

            try {
                Thread.sleep(BT_WAIT/2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ConcurrentLinkedQueue<Order> getBuffer() {
        return buffer;
    }

    public void killHandler(){
        this.shouldIWork.set(false);
    }

    /**
     *
     * @return null if nothing inside else the Order
     */
    public Order getOrder(){
        // if there is none view messages
        if (this.buffer.size() > 0 ){
            this.lastElementsSeen.addAndGet(-1);
            this.messageRemove.set(true);
        }

        Order msg = this.buffer.poll();
        System.out.println(TAG + " getOrder : " + "the buffer length is now :" + this.buffer.size());
        return msg;
    }

    @Override
    public String toString() {
        return "HandlerReceiver{" +
                "buffer=" + buffer.toString() +
                ", lastCreation=" + lastCreation +
                ", lastElementsSeen=" + lastElementsSeen.get() +
                ", numTry=" + numTry +
                ", shouldIWork=" + shouldIWork.get() +
                '}';
    }

        //The restart server process
    private void reStartServer(){
        // kill the thread
        this.th.interrupt();

        // wait to realesa the port
        try {
            Thread.sleep(BT_WAIT/2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // retart the server
        this.th = new Thread(new TCPServerLOrderAlwaysOn(buffer));
        this.th.start();

        // get the time
        this.lastCreation = System.currentTimeMillis();
    }
    public int getSizeBuffer(){
        return this.buffer.size();
    }
}
