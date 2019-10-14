package fr.superesi.superapp.monitoring;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.superesi.superapp.Order;
import fr.superesi.superapp.monitoring.OrdersTableModel;
import handlerSender.HandlerReceiver;


import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Const.Const.BT_WAIT;

public class MonitoringAppMainFrame extends JFrame implements Runnable, ActionListener {

    private static final String  TAG = "FrameOutputOrder :";
    // Serializer
    final GsonBuilder builder = new GsonBuilder();
    final Gson gson = builder.create();
    private final ArrayList<Order> liste;

    private TableRowSorter<TableModel> sorter;
    private OrdersTableModel modele;

    // Attributs
    // front attributes
    private JPanel mainPanel;
    private JTable outTable;
    private JButton btReset;
    // back attribute
    private HandlerReceiver handler;


    public MonitoringAppMainFrame() {
        super();

        setTitle("Bar Les Flots bleus : monitoring app");

        //Nous demandons maintenant à notre objet de se positionner au centre
        setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // panel
        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BorderLayout());
        this.liste = new ArrayList<>();

        modele =new OrdersTableModel(this.liste);
        this.outTable = new JTable(modele);


        // text field

        this.mainPanel.add(this.outTable,BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(this.outTable), BorderLayout.CENTER);
        getContentPane().add(this.btReset = new JButton("Reset"),BorderLayout.SOUTH);
        pack();
        setVisible(true);
        this.sorter = new TableRowSorter<TableModel>(this.outTable.getModel());
        this.outTable.setAutoCreateRowSorter(true);// enable sorting
        this.sorter.setSortsOnUpdates(true);

        this.btReset.addActionListener(this);


        this.handler = new HandlerReceiver();

        new Thread(this).start();


    }


    @Override
    public void run() {
        Order msg;
        boolean on = false;
        while (!on) {
            /**
             * Step 1: Check if we have some messages
             */
            if (this.handler.getSizeBuffer() > 0) {
                msg = this.handler.getOrder();

                /**
                 * Step 2 : if there is something to do
                 */
                if (msg != null) {

                    System.out.println(TAG + " I read this ");
                    this.modele.addOrder(msg);

                }
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String strBtName = arg0.getActionCommand(),msgToSend;
        Order order = null;

        /**
         * Step1: Block the input
         */
        this.btReset.setEnabled(false);
		/*
		onClick event on OK
		 */
        if (strBtName.equals("Reset") ){
            System.out.println(TAG + " U will reset the Table");
			/*
			remove all fields
			 */


            for(int i = this.modele.getRowCount() - 1; i >= 0; i--){
                modele.removeOrder(i);
            }

        }


        // wait
        try {
            Thread.sleep(BT_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // restart the button
        this.btReset.setEnabled(true);
    }
}
