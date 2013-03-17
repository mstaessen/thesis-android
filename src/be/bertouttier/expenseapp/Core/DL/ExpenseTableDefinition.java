package be.bertouttier.expenseapp.Core.DL;

import org.droidpersistence.dao.TableDefinition;
import be.bertouttier.expenseapp.Core.DAL.Expense;

public class ExpenseTableDefinition extends TableDefinition<Expense> {
	public ExpenseTableDefinition() {
        super(Expense.class);              
	}
}
