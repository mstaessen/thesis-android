package be.bertouttier.expenseapp.Core.DAL;

import java.util.Calendar;
import java.util.List;

import com.google.gson.Gson;

public class ExpenseForm {

	// Private fields
	private Calendar date;
	private int employeeId;
	private String signature;
	private boolean notification;
	private String remarks;
	private List<Expense> expenses;
		
	public ExpenseForm() {
		// TODO Auto-generated constructor stub
	}

	// Public getters and setters
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}



	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isNotification() {
		return notification;
	}

	public void setNotification(boolean notification) {
		this.notification = notification;
	}

	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}
	
	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	
	// Methods:
	public String toJson()
	{
		return new Gson().toJson(this);
	}
	
}
