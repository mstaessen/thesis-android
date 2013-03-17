package be.bertouttier.expenseapp.Core.BL.Managers;

import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import be.bertouttier.expenseapp.Core.DAL.Expense;
import be.bertouttier.expenseapp.Core.DL.ExpenseDao;
import be.bertouttier.expenseapp.Core.DL.ExpenseOpenHelper;
import be.bertouttier.expenseapp.Core.DL.ExpenseTableDefinition;
import be.bertouttier.expenseapp.Core.Exceptions.ExpenseException;

public class ExpenseManager {

	private Context context;
    private SQLiteDatabase database;
    private ExpenseDao expenseDao;
//	private ExpenseService svc;
//	private ExpenseManagerListener listener;
	
	public ExpenseManager(Context context){
        setContext(context);
        SQLiteOpenHelper openHelper = new ExpenseOpenHelper(context, "EXPENSEDATABASE", null, 2);
        setDatabase(openHelper.getWritableDatabase());

        this.expenseDao = new ExpenseDao(new ExpenseTableDefinition(), database); 
//        this.listener = listener;
//        this.svc = new UserService(this);
	}
	
	//Getters and setters
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	
	public ExpenseDao getExpenseDao() {
		return expenseDao;
	}

	public void setExpenseDao(ExpenseDao expenseDao) {
		this.expenseDao = expenseDao;
	}

	// Methods
//	public List<String> getProjectCodeSuggestion (String searchTerm)
//	{
//		try {
////			return svc.getProjectCodeSuggestion (searchTerm);
//		} catch (Exception ex) {
////			throw new ExpenseException ("Error while getting project codes for searchterm : " + searchTerm, ex);
//		}
//	}

	

	public void createDomesticExpense(Calendar date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId) throws ExpenseException{
		createExpense(date, projectCode, amount, remarks, evidence, currency, expenseTypeId, 1);
	}

	public void createAbroadExpense(Calendar date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId) throws ExpenseException{
		createExpense(date, projectCode, amount, remarks, evidence, currency, expenseTypeId, 2);
	}

	private void createExpense(Calendar date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId, int expenseLocationId) throws ExpenseException
	{
		Expense e = new Expense(date, projectCode, amount, remarks, evidence, currency, expenseTypeId, expenseLocationId);
		saveExpense (e);
	}

	// Database actions
	public List<Expense> getExpenses ()
	{
		//Get all expenses. Sort here?
		return getExpenseDao().getAll();
	}

	public Expense getExpense (long id)
	{
		return getExpenseDao().get(id);
	}

	public boolean updateExpense(Expense expense)
	{
		boolean result = false;
		try {
		getDatabase().beginTransaction();
		getExpenseDao().update(expense, expense.getId());
		getDatabase().setTransactionSuccessful();
		result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		getDatabase().endTransaction();
		return result;
	}

	public boolean deleteExpense(Expense expense)
	{
		boolean result = false;
		getDatabase().beginTransaction();
		result = getExpenseDao().delete(expense.getId());
		getDatabase().setTransactionSuccessful();
		getDatabase().endTransaction();
		return result;
	}

	public boolean deleteAllExpenses ()
	{
		boolean result = false;
		List<Expense> expenses = this.getExpenses();
		
		getDatabase().beginTransaction();
		for(Expense expense:expenses)
		{
			result = getExpenseDao().delete(expense.getId());	
			if(!result) break;
		}
		getDatabase().setTransactionSuccessful();
		getDatabase().endTransaction();
		return result;
	}
	
    public long saveExpense(Expense expense) throws ExpenseException{
        long result = 0;                
        try {
            getDatabase().beginTransaction();
            result = getExpenseDao().save(expense);
            getDatabase().setTransactionSuccessful();
        } catch (Exception ex) {
        	throw new ExpenseException("Error while saving expense "+ ex.toString() +" to database.", ex);
        }               
        getDatabase().endTransaction();
        return result;
    }
}
