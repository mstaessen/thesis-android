package be.bertouttier.expenseapp.Core.DL;

import org.droidpersistence.dao.TableDefinition;
import be.bertouttier.expenseapp.Core.DAL.Employee;

public class EmployeeTableDefinition extends TableDefinition<Employee> {
	public EmployeeTableDefinition() {
        super(Employee.class);              
	}
}