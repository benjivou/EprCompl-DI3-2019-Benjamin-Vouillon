package fr.superesi.superapp.bar.gui;

import TCP.Client.TCPClientLMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.superesi.superapp.Order;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static Const.Const.*;

/**
 * Main Frame class for "bar" app.
 * 
 * @author esswein
 */
@SuppressWarnings("serial")
public class BarAppMainFrame extends JFrame implements ActionListener{


	private static final String TAG = "BarAppMainFrame :";

	// Serializer
	final GsonBuilder builder = new GsonBuilder();
	final Gson gson = builder.create();

	private JLabel productLabel, quantityLabel, unitPriceLabel;
	private JTextField productTextField, quantityTextField, unitPriceTextField;
	private JButton okButton, cancelButton;
	private JPanel mainPanel, buttonPanel;

	/**
	 * Creates GUI elements and set up the layout ; it then displays the main frame.
	 */
	private void initGUI() {

		// First part : input fields and labels
		productLabel = new JLabel(PRODUCT);
		productTextField = new JTextField(25);
		unitPriceLabel = new JLabel(UNIT_PRICE);
		unitPriceTextField = new JTextField(DEFAULT_UNIT_PRICE);
		quantityLabel = new JLabel(QUANTITY);
		quantityTextField = new JTextField(DEFAULT_QUANTITY);
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(6, 1, 4, 0));
		mainPanel.add(productLabel);
		mainPanel.add(productTextField);
		mainPanel.add(unitPriceLabel);
		mainPanel.add(unitPriceTextField);
		mainPanel.add(quantityLabel);
		mainPanel.add(quantityTextField);
		add(mainPanel, BorderLayout.PAGE_START);
		
		// Second part : buttons
		buttonPanel = new JPanel();

		okButton = new JButton(OK);
		cancelButton = new JButton(CANCEL);

		// event actions
		this.okButton.addActionListener(this);
		this.cancelButton.addActionListener(this);

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		// Final set up
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * One and only constructor of the main frame, called by the main method.
	 */
	public BarAppMainFrame() {
		super(APP_NAME);
		initGUI();
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		String strBtName = arg0.getActionCommand(),msgToSend;
		Order order = null;

		/**
		 * Step1: Block the input
		 */
		this.okButton.setEnabled(false);
		this.cancelButton.setEnabled(false);

		/*
		onClick event on OK
		 */
		if (strBtName.equals(OK) && isValidInputs() ){
			/*
			Fill the object
			 */
			order = new Order(this.productTextField.getText(),
					Integer.parseInt(this.quantityTextField.getText()),
					Float.parseFloat(this.unitPriceTextField.getText()));

			// JSONization
			msgToSend = gson.toJson(order);

			System.out.println(TAG + " the message U will send is : " + msgToSend);

			// send the message
			new Thread(new TCPClientLMessage(msgToSend)).start();
		}

		/*
		onClick event on Cancel
		 */
		if (strBtName.equals((CANCEL))){
			// restart quantity
			this.productTextField.setText("");
			this.quantityTextField.setText(DEFAULT_QUANTITY);
			this.unitPriceTextField.setText(DEFAULT_UNIT_PRICE);
		}

		// wait for restart of the server
		try {
			Thread.sleep(BT_WAIT);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.okButton.setEnabled(true);
		this.cancelButton.setEnabled(true);
		
	}

	/**
	 *
	 * @return true if the inputs are valid
	 */
	private boolean isValidInputs(){
		boolean isValid = true;

		try{
			Integer.parseInt(unitPriceTextField.getText()); // test if this a valid number
			int qte = Integer.parseInt(quantityTextField.getText()); // test if this a valid number
			isValid =!productTextField.getText().equals(""); // test if this a none null name
			if (isValid){
				isValid = (qte > 0); // test if the quantity is note null or negative
			}

		}catch(NumberFormatException e){
			isValid = false;
		}
		return isValid;
	}
}
