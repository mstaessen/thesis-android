package be.bertouttier.expenseapp.Core.DAL;

public class SavedExpenseForm extends ExpenseForm {
	
	// private fields
	private int id;
	private int statusId;
	
	// Constructor
	public SavedExpenseForm()  {
		// TODO Auto-generated constructor stub
	}
	
	// public getters/ setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
}
