package be.bertouttier.expenseapp.Core.BL;

import be.bertouttier.expenseapp.Core.DAL.Employee;

public interface BackendListener {
	void onLoginCompleted(Employee user);
	void onLogoutCompleted();
}
