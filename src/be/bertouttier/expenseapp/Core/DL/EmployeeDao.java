package be.bertouttier.expenseapp.Core.DL;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;
import android.database.sqlite.SQLiteDatabase;
import be.bertouttier.expenseapp.Core.DAL.Employee;

public class EmployeeDao extends DroidDao<Employee, Long>{
	public EmployeeDao(TableDefinition<Employee> tableDefinition, SQLiteDatabase database) {
        super(Employee.class, tableDefinition, database);
	}
}