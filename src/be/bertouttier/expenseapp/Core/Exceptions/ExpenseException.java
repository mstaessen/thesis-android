package be.bertouttier.expenseapp.Core.Exceptions;

public class ExpenseException extends Exception {

	public ExpenseException() {
		// TODO Auto-generated constructor stub
	}

	public ExpenseException(String detailMessage) {
		super(detailMessage);
		// TODO Auto-generated constructor stub
	}

	public ExpenseException(Throwable throwable) {
		super(throwable);
		// TODO Auto-generated constructor stub
	}

	public ExpenseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		// TODO Auto-generated constructor stub
	}

}
