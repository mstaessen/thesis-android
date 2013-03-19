package be.bertouttier.expenseapp.Core.SAL;

import java.io.File;
import java.util.Collection;
import java.util.List;

import be.bertouttier.expenseapp.Core.DAL.SavedExpenseForm;

public interface ExpenseServiceListener {
	void onGetProjectCodeSuggestionCompleted(List<String> projects);
	void onSaveExpenseCompleted();
	void onGetExpenseFormsCompleted(Collection<SavedExpenseForm> expenseForm);
	void onGetExpenseFormPDFCompleted(File pdf);
}
