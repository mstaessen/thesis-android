package be.bertouttier.expenseapp.Core.BL.Managers;

import be.bertouttier.expenseapp.Core.DAL.Employee;
import be.bertouttier.expenseapp.Core.DL.EmployeeDao;
import be.bertouttier.expenseapp.Core.DL.EmployeeOpenHelper;
import be.bertouttier.expenseapp.Core.DL.EmployeeTableDefinition;
import be.bertouttier.expenseapp.Core.Exceptions.EmployeeException;
import be.bertouttier.expenseapp.Core.SAL.UserService;
import be.bertouttier.expenseapp.Core.SAL.UserServiceListener;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EmployeeManager implements UserServiceListener {
	private Context context;
    private SQLiteDatabase database;
    private EmployeeDao employeeDao;
	private Employee user;
	private UserService svc;
	private EmployeeManagerListener listener;
    
	public EmployeeManager(Context context, EmployeeManagerListener listener){
            setContext(context);
            SQLiteOpenHelper openHelper = new EmployeeOpenHelper(context, "EMPLOYEEDATABASE", null, 2);
            setDatabase(openHelper.getWritableDatabase());

            this.employeeDao = new EmployeeDao(new EmployeeTableDefinition(), database); 
            this.listener = listener;
            this.svc = new UserService(this);
            
            try {
				getEmployeeFromDb ();
			} catch (Exception ex) {
//				throw new EmployeeException("No user in database. Please use the .login() method.", ex);
				Log.d("!!", "No user in database. Please use the .login() method.");
			}
    }
    
	// getters/setters
	public SQLiteDatabase getDatabase() {
		return database;
	}

	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}

	public EmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	// Methods
	public void login (String email, String password)
	{
		if (!isAuthenticated ()) {
//			try {
				svc.login (email, password);
//			} catch (Exception ex) {
//				throw new EmployeeException("Error when logging in.", ex);
//			}
		}
	}

	public void logout () throws EmployeeException
	{
		try {
			svc.logout (user.getToken());
			removeEmployeeFromDb();
			user = null;
		} catch (Exception ex) {
			throw new EmployeeException ("Error when logging out.", ex);
		}
	}

	public String getFirstName () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getFirstName();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Fullname.");
		}
	}

	public String getLastName () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getLastName();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Fullname.");
		}
	}

	public String getFullName () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getFirstName() + " " + user.getLastName();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Fullname.");
		}
	}

	public String getEmail () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getEmail();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Fullname.");
		}
	}

	public int getEmployeeNumber () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getEmployeeNumber();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Fullname.");
		}
	}

	public String getToken () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getToken();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access Token.");
		}
	}

	public int getId () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getId();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access id.");
		}
	}

	public int getUnitId () throws EmployeeException
	{
		if (isAuthenticated ()) {
			return user.getUnitId();
		} else {
			throw new EmployeeException ("User is not authenticated. Cannot access unitId.");
		}
	}

	public boolean isAuthenticated ()
	{
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}
	
	// Database actions
    private long saveEmployee(Employee employee){
        long result = 0;                
        try {
                getDatabase().beginTransaction();
                result = getEmployeeDao().save(employee);
                getDatabase().setTransactionSuccessful();
        } catch (Exception e) {
                e.printStackTrace();
        }               
        getDatabase().endTransaction();
        return result;
    }
    
    private void getEmployeeFromDb () throws EmployeeException
	{
		try {
			user = getEmployeeDao().getByClause(null, null);
		} catch (Exception ex) {
			throw new EmployeeException("Error while fetching employee from database.", ex);
		}
	}
    
    private boolean removeEmployeeFromDb()
    {
    	boolean result = false;
    	getDatabase().beginTransaction();
    	result = getEmployeeDao().delete(user.getUserId());
    	getDatabase().setTransactionSuccessful();
    	getDatabase().endTransaction();
    	return result;
    }

    // Listener methods
	@Override
	public void onLoginCompleted(String token) {
		svc.getEmployee(token);
	}

	@Override
	public void onGetEmployeeCompleted(Employee user) {
		saveEmployee(user);
		listener.onLoginCompleted(user);
	}

	@Override
	public void onLogoutCompleted() {
		listener.onLogoutCompleted();
	}
}
