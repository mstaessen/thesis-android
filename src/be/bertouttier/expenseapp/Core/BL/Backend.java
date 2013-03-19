package be.bertouttier.expenseapp.Core.BL;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManager;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManagerListener;
import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseManager;
import be.bertouttier.expenseapp.Core.DAL.Expense;
import be.bertouttier.expenseapp.Core.Exceptions.ExpenseException;

public class Backend implements EmployeeManagerListener {
	private static Backend instance = new Backend();
	private Context context;
	private static EmployeeManager um;
//	private static ExpenseFormManager efm;
	private static ExpenseManager em;
//	private static CurrencyAPI curAPI;
	private static Map<Integer, String> _projectCodes;
	
	private Backend() { 
		um = new EmployeeManager(context, this);
//		efm = new ExpenseFormManager();
		em = new ExpenseManager(context);
//		curAPI = new CurrencyAPI();
		_projectCodes = new HashMap<Integer, String>();
	}
	
	public static Backend getInstance(Context context) {
		instance.context = context;
		return instance;
	}
	
	protected static void demoMethod( ) {
	   System.out.println("demoMethod for singleton"); 
	}
	
	// User manipulations
	protected static void login (String email, String password)
	{
		um.login(email, password);
	}

	protected static void logout ()
	{
		um.logout();
		em.deleteAllExpenses();
	}

	protected static String getFirstName ()
	{
		return um.getFirstName();
	}

	protected static String getLastName ()
	{
		return um.getLastName();
	}

	protected static String getEmail ()
	{
		return um.getEmail();
	}

	protected static int getEmployeeNumber ()
	{
		return um.getEmployeeNumber();
	}

	protected static String getFullName ()
	{
		return um.getFullName();
	}

	protected static int getUnitId ()
	{
		return um.getUnitId();
	}

	protected static boolean isAuthenticated ()
	{
		return um.isAuthenticated();
	}


	// Expenses manipulations
	protected static void createDomesticExpense(Date date, String projectCode, float amount, String remarks, String evidence, int expenseTypeId) throws ExpenseException{
		em.createExpense(date, projectCode, amount, remarks, evidence, "EUR", expenseTypeId, 1);
	}

	protected static void createAbroadExpense(Date date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId) throws ExpenseException{
		em.createExpense(date, projectCode, amount, remarks, evidence, currency, expenseTypeId, 2);
	}
	
	protected static List<Expense> getExpenses() {
		return em.getExpenses();
	}
	
	protected static Expense getExpense (long id)
	{
		return em.getExpense(id);
	}
	
	protected static void updateExpense(Expense e) {
		em.updateExpense(e);
	}
	
	protected static void deleteExpense(Expense e) {
		em.deleteExpense(e);
	}

	// Form manipulations
	protected static void send (String signature, String remarks, boolean notification)
	{
		efm = new ExpenseFormManager(um.getId(), signature, remarks, notification, em.getExpenses());
		efm.token = um.getToken();
		efm.send();
	}

	protected static String getFormPDF (int formId, String filename)
	{
		return efm.getFormPDF(formId, filename);
	}

	protected static List<SavedExpenseForm> getExpenseForms ()
	{
		return efm.getExpenseForms(um.getToken());
	}

	// Currency stuff
	protected static float convertToEuro (float amount, string currency)
	{
		return curAPI.convertToEuro(amount, currency);
	}
	
	protected static Dictionary<int, string> currencies {
		get {
			List<string> cl = curAPI.getCurrencies();
			Dictionary<int, string> result = new Dictionary<int, string>();

			for (int i = 0; i < cl.Count; i++)
			{
				result.Add(i+1, cl[i]);
			}

			return result;
		}
	}

	// Projects
	protected static Dictionary<int, string> projectCodes {
		get {
			if(_projectCodes.Count <= 0) {
				List<string> projects = em.getProjectCodeSuggestion(""); // Get all projects
				
				for (int i = 0; i < projects.Count; i++)
				{
					_projectCodes.Add(i+1, projects[i]);
				}
			}

			return _projectCodes;
		}
	}
}