package balance;

public class Balance {
	
	/**
	 * 
	 * @param baseIncome Gets the baseIncome variable from the income text field.
	 * @param savingsMin Gets the Savings variable from the savings text field.
	 */
	public void getBalance(double baseIncome, double savingsMin)
	{
		this.baseIncome = baseIncome;
		this.savingsMin = savingsMin;
		if (baseIncome < 0 || savingsMin < 0)
		{
			errors = 1;
		}
		totBalance = baseIncome - savingsMin; //Math to get available balance
		
	}
	 
	/**
	 * 
	 * @return Returns the total balance at the point of execution.
	 */
	public double getBalance()
	{
		return totBalance;
	}
	
	/**
	 * 
	 * @return Returns a 1 if the application experiences an invalid income/savings combination, where either the income or savings are negative numbers.
	 */
	public int checkErrors()
	{
		return errors;
	}
	
	/**
	 * Resets all doubles back to 0 in the program.
	 */
	public void clearBalance()
	{
		baseIncome = 0;
		savingsMin = 0;
		totBalance = 0;
	}
	
	/**
	 * 
	 * @param transAmount Gets the current transaction amount and subtracts it from the total balance.
	 */
	public void subtractFromBalance(double transAmount)
	{
		totBalance = totBalance - transAmount;
	}
	
	/**
	 * 
	 * @param transAmount Gets the current transaction amount and adds it to the total balance.
	 */
	public void addToBalance(double transAmount)
	{
		totBalance = totBalance + transAmount;
	}
	
	//Variables
	double balCheck;
	int errors = 0;
	static double totBalance;
	double baseIncome, savingsMin;
}
