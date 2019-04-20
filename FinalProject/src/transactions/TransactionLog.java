package transactions;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import balance.Balance;

@SuppressWarnings("serial")
public class TransactionLog extends JTable{
	/**
	 * Creates the table model that is being used in the application.
	 */
	public TransactionLog()
	{
		//creating a JTable with three columns: Name, amount, type with classes associated
		setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Amount", "Type"
			}
		) {
			@SuppressWarnings("rawtypes")
			Class[] columnTypes = new Class[] {
				String.class, Double.class, String.class
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	}
	/**
	 * 
	 * @param transName Passes the transaction name to be added to table column 0.
	 * @param transAmount Passes the transaction amount to be added to table column 1.
	 * @param transType Passes the transaction type to be added to table column 2.
	 */
	public void addTransaction(String transName, double transAmount, String transType)
	{
		Object[] row = { transName, transAmount, transType}; //Create an object named row and associate it the 3 values that were passed
		DefaultTableModel addModel = (DefaultTableModel) getModel(); //gets the table model adder
		
		addModel.addRow(row); //adds the row Object to the table
	}
	
	/**
	 * 
	 * @param transAmount Gets the transaction amount to add it to the specified transaction type, and subtract it from the balance.
	 * @param transType Gets the transaction type to add to the total of the specified transaction type.
	 */
	public void addTransactionType(double transAmount, String transType)
	{
		if(transType == "Pets") //If the type is equal to pets, then add the amount to the total pet transactions.
		{
			totPets = totPets + transAmount;
		}
		else if(transType == "Emergency")
		{
			totEmerg = totEmerg + transAmount;
		}
		else if(transType == "Bills")
		{
			totBills = totBills + transAmount;
		}
		else
		{
			totLeisure = totLeisure + transAmount;
		}
		balance.subtractFromBalance(transAmount);
		
	}
	
	/**
	 * Deletes a transaction from the table.
	 */
	public void deleteTransaction()
	{
		DefaultTableModel deleteModel = (DefaultTableModel) getModel(); //Creating a delete table model to handle the deletion of rows
		if(getSelectedRow() >=0)
		{
			deleteTransType = getValueAt(getSelectedRow(), 2).toString();
			deleteTransAmnt = (Double) getValueAt(getSelectedRow(), 1);
			
			deleteModel.removeRow(getSelectedRow());
			deleteTransactionType(deleteTransAmnt, deleteTransType);
		}
		
		
	}
	
	/**
	 * 
	 * @param transAmount Gets the transaction amount to delete it from the transaction type and add it back to the balance.
	 * @param transType Gets the transaction type to determine where the amount needs to be deleted from.
	 */
	public void deleteTransactionType(double transAmount, String transType)
	{
		if(transType == "Pets") //If the type is equal to pets, remove the transactio from the pets total transactions
		{
			totPets = totPets - transAmount;
		}
		else if(transType == "Emergency")
		{
			totEmerg = totEmerg - transAmount;
		}
		else if(transType == "Bills")
		{
			totBills = totBills - transAmount;
		}
		else
		{
			totLeisure = totLeisure - transAmount;
		}
		balance.addToBalance(transAmount);
	}
	
	/**
	 * Sets all transaction types to 0.
	 * Removes all rows within the table.
	 */
	public void clearAllTransactions()
	{
		DefaultTableModel deleteModel = (DefaultTableModel) getModel(); //Creating a delete table model to handle the deletion of rows
		while(getRowCount() > 0)
		{
			//Clear all transactions within the table
			balance.clearBalance();
			deleteModel.removeRow(0);
			totPets = 0;
			totBills = 0;
			totEmerg = 0;
			totLeisure = 0;
		}
	}

	//Variables
	Balance balance = new Balance();
	String deleteTransType;
	double deleteTransAmnt;
	public static double totPets, totBills, totEmerg, totLeisure;
}
