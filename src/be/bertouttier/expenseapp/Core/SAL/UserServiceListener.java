package be.bertouttier.expenseapp.Core.SAL;

import be.bertouttier.expenseapp.Core.DAL.Employee;

public interface UserServiceListener {
	void onLoginCompleted(String token);
	void onGetEmployeeCompleted(Employee user);
	void onLogoutCompleted();
}
