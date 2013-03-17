package be.bertouttier.expenseapp.Core.DL;

import org.droidpersistence.dao.DroidDao;
import org.droidpersistence.dao.TableDefinition;
import android.database.sqlite.SQLiteDatabase;
import be.bertouttier.expenseapp.Core.DAL.Expense;

public class ExpenseDao extends DroidDao<Expense, Long>{
	public ExpenseDao(TableDefinition<Expense> tableDefinition, SQLiteDatabase database) {
        super(Expense.class, tableDefinition, database);
	}
}
