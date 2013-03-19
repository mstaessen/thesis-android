package be.bertouttier.expenseapp.Core.BL;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManager;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManagerListener;
import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseFormManager;
import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseManager;
import be.bertouttier.expenseapp.Core.DAL.Employee;
import be.bertouttier.expenseapp.Core.DAL.Expense;
import be.bertouttier.expenseapp.Core.DAL.SavedExpenseForm;
import be.bertouttier.expenseapp.Core.Exceptions.EmployeeException;
import be.bertouttier.expenseapp.Core.Exceptions.ExpenseException;
import be.bertouttier.expenseapp.Core.SAL.CurrencyService;

public class Backend implements EmployeeManagerListener {
	private static Backend instance = new Backend();
	private static Context context;
	private static BackendListener listener;
	private static EmployeeManager um;
	private static ExpenseFormManager efm;
	private static ExpenseManager em;
	private static CurrencyService curAPI;
	private static Map<Integer, String> _projectCodes;
	
	private Backend() {
		um = new EmployeeManager(context, this);
		efm = new ExpenseFormManager();
		em = new ExpenseManager(context);
		curAPI = new CurrencyService();
		_projectCodes = new HashMap<Integer, String>();
	}
	
	public static Backend getInstance(Context context, BackendListener listener) {
		instance.listener = listener;
		instance.context = context;
		um.setContext(context);
		em.setContext(context);
		return instance;
	}
	
	// User manipulations
	public static void login (String email, String password)
	{
		um.login(email, password);
	}

	public static void logout () throws EmployeeException
	{
		um.logout();
		em.deleteAllExpenses();
	}

	public static String getFirstName () throws EmployeeException
	{
		return um.getFirstName();
	}

	public static String getLastName () throws EmployeeException
	{
		return um.getLastName();
	}

	public static String getEmail () throws EmployeeException
	{
		return um.getEmail();
	}

	public static int getEmployeeNumber () throws EmployeeException
	{
		return um.getEmployeeNumber();
	}

	public static String getFullName () throws EmployeeException
	{
		return um.getFullName();
	}

	public static int getUnitId () throws EmployeeException
	{
		return um.getUnitId();
	}

	public static boolean isAuthenticated ()
	{
		return um.isAuthenticated();
	}


	// Expenses manipulations
	public static void createDomesticExpense(Date date, String projectCode, float amount, String remarks, String evidence, int expenseTypeId) throws ExpenseException{
		em.createExpense(date, projectCode, amount, remarks, evidence, "EUR", expenseTypeId, 1);
	}

	public static void createAbroadExpense(Date date, String projectCode, float amount, String remarks, String evidence, String currency, int expenseTypeId) throws ExpenseException{
		em.createExpense(date, projectCode, amount, remarks, evidence, currency, expenseTypeId, 2);
	}
	
	public static List<Expense> getExpenses() {
		return em.getExpenses();
	}
	
	public static Expense getExpense (long id)
	{
		return em.getExpense(id);
	}
	
	public static void updateExpense(Expense e) {
		em.updateExpense(e);
	}
	
	public static void deleteExpense(Expense e) {
		em.deleteExpense(e);
	}

	// Form manipulations
	public static void send (String signature, String remarks, boolean notification) throws EmployeeException, ExpenseException
	{
		efm = new ExpenseFormManager(um.getId(), signature, remarks, notification, em.getExpenses());
		efm.setToken(um.getToken());
		efm.send();
	}

	public static String getFormPDF (int formId, String filename) throws ExpenseException
	{
		return efm.getFormPDF(formId, filename);
	}

	public static List<SavedExpenseForm> getExpenseForms () throws ExpenseException, EmployeeException
	{
		return efm.getExpenseForms(um.getToken());
	}

	// Currency stuff
	public static float convertToEuro (float amount, String currency)
	{
		return curAPI.convertToEuro(amount, currency);
	}
	
	public static Map<Integer, String> getCurrencies() {
		List<String> cl = new ArrayList<String>(curAPI.getCurrencies());
		Map<Integer, String> result = new HashMap<Integer, String>();

		for (int i = 0; i < cl.size(); i++)
		{
			result.put(i+1, cl.get(i));
		}

		return result;
	}

	// Projects
	public static Map<Integer, String> getProjectCodes() {
		if(_projectCodes.size() <= 0) {
			List<String> projects = em.getProjectCodeSuggestion(""); // Get all projects
			
			for (int i = 0; i < projects.size(); i++)
			{
				_projectCodes.put(i+1, projects.get(i));
			}
		}

		return _projectCodes;
	}

	@Override
	public void onLoginCompleted(Employee user) {
		// TODO Auto-generated method stub
		listener.onLoginCompleted(user);
	}

	@Override
	public void onLogoutCompleted() {
		// TODO Auto-generated method stub
		listener.onLogoutCompleted();
	}
}