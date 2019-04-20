package gui;

import javax.swing.*;

import balance.Balance;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import javafx.application.Application;
import transactions.TransactionLog;

public class MoneySaverMain {
	/**
	 * 
	 * @param args This creates the main frame and contents/containers used.
	 */
    public static void main(String[] args)
    {
    	Balance balance = new Balance();
		TransactionLog tsLog = new TransactionLog();
        JFrame mainFrame = new JFrame("Money Saver"); // create JFrame Object for the main frame
        
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainFrame.getContentPane().setLayout(null); //No layout for the main frame
        mainFrame.setSize(360, 575);
        
		JPanel incomePanel = new JPanel();
		incomePanel.setBounds(0, 0, 341, 61);
		mainFrame.getContentPane().add(incomePanel); //Creating the top panel with a Flow Layout
		incomePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel income = new JLabel("Income:");
		incomePanel.add(income); //add the income label
		JTextField incomeField = new JTextField();
		incomePanel.add(incomeField); //add the income field
		incomeField.setColumns(10);
		
		JLabel budget = new JLabel("Savings:");
		incomePanel.add(budget); //add the savings label. This is the amount of money you would like to save at minimum.
		JTextField budgetField = new JTextField();
		incomePanel.add(budgetField); //add the panel for inputting savings
		budgetField.setColumns(10);
		
		JButton submitIncome = new JButton(" Submit "); //Submit button and the actionListener associated.
		submitIncome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					balance.getBalance(Double.parseDouble(incomeField.getText()), Double.parseDouble(budgetField.getText()));
					
					if(balance.checkErrors() == 1)
					{
						balance.clearBalance(); 
						JOptionPane.showMessageDialog(mainFrame, "Income or Balance contains negative numbers. Please adjust accordingly"); //Show notification if one of income or balance contains negative numbers when submitted
					}
					if(balance.getBalance() < 0)
					{
						balance.clearBalance();
						JOptionPane.showMessageDialog(mainFrame, "Savings budget is less than income. Please adjust accordingly"); //Show notification if the balance between the income and savings is negative (higher savings than income provided)
					}
					
					balLabel.setText("Balance: " + df.format(balance.getBalance()));
					tsLog.clearAllTransactions();
				}
				catch(IllegalArgumentException e1) //Catch if either Income or Savings contains characters. There cannot be any characters in either field
				{
					JOptionPane.showMessageDialog(mainFrame, "Income or Balance contains invalid characters.");
				}
			}
		});
		submitIncome.setVerticalAlignment(SwingConstants.BOTTOM);
		incomePanel.add(submitIncome); //add the submit button
        
		JScrollPane transPane = new JScrollPane(); //Create scroll pane for the table
		transPane.setBounds(0, 59, 341, 278);
		mainFrame.getContentPane().add(transPane); //Add scroll pane to the JFrame
		transPane.setViewportView(tsLog); //Add the Table to the ScrollPane to allow for scrolling within the table.
		
		JPanel transPanel = new JPanel();
		transPanel.setBounds(0, 336, 341, 211);
		mainFrame.getContentPane().add(transPanel); //Add the transaction panel below the scroll pane.
		transPanel.setLayout(null);
		
		JLabel transNameLabel = new JLabel("Transaction Name");
		transNameLabel.setBounds(10, 11, 132, 14);
		transPanel.add(transNameLabel); //Add the transaction name label.
		
		JTextField transNameField = new JTextField();
		transNameField.setBounds(172, 8, 159, 20);
		transPanel.add(transNameField); //Add the transaction name field where the user can submit the name of the transaction.
		transNameField.setColumns(10);
		
		JLabel transAmntLabel = new JLabel("Transaction Amount");
		transAmntLabel.setBounds(10, 42, 132, 14);
		transPanel.add(transAmntLabel); //add transaction amount label.
		
		JTextField transAmntField = new JTextField();
		transAmntField.setBounds(172, 39, 159, 20);
		transPanel.add(transAmntField); //add transaction amount field.
		transAmntField.setColumns(10);
		
		JRadioButton rdbtnLeisure = new JRadioButton("Leisure");
		rdbtnLeisure.setSelected(true);
		rdbtnLeisure.setActionCommand("Leisure");
		buttonGroup.add(rdbtnLeisure); //add the Leisure button to the radio button group
		rdbtnLeisure.setBounds(10, 73, 109, 23);
		transPanel.add(rdbtnLeisure); //add the Leisure button to the transaction panel
		
		JRadioButton rdbtnBills = new JRadioButton("Bills");
		rdbtnBills.setActionCommand("Bills");
		buttonGroup.add(rdbtnBills); //add the Bills button to the radio button group
		rdbtnBills.setBounds(163, 73, 109, 23);
		transPanel.add(rdbtnBills); //add the Bills button to the transaction panel
		
		JRadioButton rdbtnEmergency = new JRadioButton("Emergency");
		rdbtnEmergency.setActionCommand("Emergency");
		buttonGroup.add(rdbtnEmergency); //add the Emergency button to the radio button group
		rdbtnEmergency.setBounds(10, 99, 109, 23);
		transPanel.add(rdbtnEmergency); //add the Emergency button to the transaction panel
		
		JRadioButton rdbtnPets = new JRadioButton("Pets");
		rdbtnPets.setActionCommand("Pets");
		buttonGroup.add(rdbtnPets); //add the Pets button to the radio button group
		rdbtnPets.setBounds(163, 99, 109, 23);
		transPanel.add(rdbtnPets); //add the Pets button to the transaction panel
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					double amountDbl = Double.parseDouble(transAmntField.getText());
					
					tsLog.addTransaction(transNameField.getText(), amountDbl, buttonGroup.getSelection().getActionCommand());
					tsLog.addTransactionType(amountDbl, buttonGroup.getSelection().getActionCommand());
					balLabel.setText("Balance: " + df.format(balance.getBalance()));
					
					if(balance.getBalance() < 0) //if the total balance is less than 0
					{
						JOptionPane.showMessageDialog(mainFrame, "Exceeded balance."); //give warning
						//mMath.removeFromBalance(amountDbl, rdbtnSelected); //remove balance that was just added
						//deleteModel.removeRow(deleteModel.getRowCount() - 1); //remove row that was just added
						balLabel.setText("Balance: " + df.format(balance.getBalance()));
					}
				}
				catch(IllegalArgumentException e1) //If there are characters in the Amount frame, display a notification to the user.
				{
					JOptionPane.showMessageDialog(mainFrame, "Amount contains invalid characters.");
				}
			}
		});
		btnAdd.setBounds(10, 129, 89, 23);
		transPanel.add(btnAdd); //Add the add button to the transaction panel.
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tsLog.deleteTransaction();
				balLabel.setText("Balance: " + df.format(balance.getBalance()));				
			}
		});
		btnDelete.setBounds(163, 129, 89, 23);
		transPanel.add(btnDelete); //add the delete button to the transaction panel
		
		balLabel.setText("Balance: " + df.format(balance.getBalance()));
		balLabel.setBounds(10, 183, 159, 14);
		transPanel.add(balLabel);
		
        mainFrame.setVisible(true); //set the frame to visible
        Application.launch(MoneyChart.class, args);
    }
    //variables available
    static double dbliCheck, dbliBalance = 0;
    private final static ButtonGroup buttonGroup = new ButtonGroup();
    static JLabel balLabel = new JLabel("Balance: ");
    static DecimalFormat df = new DecimalFormat("#.##");
}
