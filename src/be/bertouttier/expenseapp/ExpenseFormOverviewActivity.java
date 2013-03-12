package be.bertouttier.expenseapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExpenseFormOverviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expense_form_overview_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expense_form_overview, menu);
		return true;
	}

}
