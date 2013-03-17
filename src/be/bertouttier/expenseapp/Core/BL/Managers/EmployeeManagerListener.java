package be.bertouttier.expenseapp.Core.BL.Managers;

import be.bertouttier.expenseapp.Core.DAL.Employee;

public interface EmployeeManagerListener {
	void onLoginCompleted(Employee user);
	void onLogoutCompleted();
}
