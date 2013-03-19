package be.bertouttier.expenseapp.Core.BL.Managers;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import be.bertouttier.expenseapp.Core.DAL.Expense;
import be.bertouttier.expenseapp.Core.DAL.ExpenseForm;
import be.bertouttier.expenseapp.Core.DAL.SavedExpenseForm;
import be.bertouttier.expenseapp.Core.Exceptions.ExpenseException;
import be.bertouttier.expenseapp.Core.SAL.ExpenseService;
import be.bertouttier.expenseapp.Core.SAL.ExpenseServiceListener;

public class ExpenseFormManager implements ExpenseServiceListener {
	private ExpenseForm form;
	private String token;
	private ExpenseService svc;

	public ExpenseFormManager ()
	{
		svc = new ExpenseService(this);
		form = new ExpenseForm();
	}

	public ExpenseFormManager (int employeeId, List<Expense> expenses)
	{
		svc = new ExpenseService(this);
		form = new ExpenseForm();
		form.setEmployeeId(employeeId);
		form.setExpenses(expenses);
	}

	public ExpenseFormManager (int employeeId, String signature, String remarks, boolean notification, List<Expense> expenses)
	{
		svc = new ExpenseService(this);
		form = new ExpenseForm();
		form.setEmployeeId(employeeId);
		form.setSignature(signature);
		form.setRemarks(remarks);
		form.setNotification(notification);
		form.setExpenses(expenses);
	}

	public void setToken (String token)
	{
		this.token = token;
	}

	public void send () throws ExpenseException
	{
		form.setDate(new Date());
		sendForm();
	}

	public void send (String signature, String remarks, boolean notifications, List<Expense> expenses) throws ExpenseException
	{
		form.setDate(new Date());
		form.setSignature(signature);
		form.setRemarks(remarks);
		form.setExpenses(expenses);
		sendForm();
	}

	public void send (int employeeId, String signature, String remarks, boolean notification, List<Expense> expenses) throws ExpenseException
	{
		form.setDate(new Date());
		form.setEmployeeId(employeeId);
		form.setSignature(signature);
		form.setRemarks(remarks);
		form.setNotification(notification);
		form.setExpenses(expenses);
		sendForm();
	}

	private void sendForm () throws ExpenseException
	{
		try {
			svc.saveExpense (token, form);
		} catch (Exception ex) {
			throw new ExpenseException("Error while save expense form.", ex);
		}
	}

	public List<SavedExpenseForm> getExpenseForms (String token) throws ExpenseException
	{
		try {
			return svc.getExpenseForms (token);
		} catch (Exception ex) {
			throw new ExpenseException("Error while getting expense forms from API.", ex);
		}
	}

	public String getFormPDF (int formId, String filename) throws ExpenseException
	{
		try {
			return svc.getExpenseFormPDF (token, formId);
		} catch (Exception ex) {
			throw new ExpenseException("Error while getting expense form PDF", ex);
		}
	}

	@Override
	public void onGetProjectCodeSuggestionCompleted(List<String> projects) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSaveExpenseCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetExpenseFormsCompleted(
			Collection<SavedExpenseForm> expenseForm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetExpenseFormPDFCompleted(File pdf) {
		// TODO Auto-generated method stub
		
	}
}
