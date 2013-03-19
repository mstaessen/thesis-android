package be.bertouttier.expenseapp;

import be.bertouttier.expenseapp.Core.BL.Backend;
import be.bertouttier.expenseapp.Core.BL.BackendListener;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManager;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManagerListener;
import be.bertouttier.expenseapp.Core.DAL.Employee;
import be.bertouttier.expenseapp.Core.Exceptions.EmployeeException;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends Activity implements BackendListener {
	private Backend backend = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		
		backend = Backend.getInstance(getApplicationContext(), this);
		
		TextView lblWelcome = (TextView) findViewById (R.id.lblWelcome);
		
		
		try { 
			if (!backend.isAuthenticated ()) {
				startActivity(new Intent(this, LoginActivity.class));
			}
			lblWelcome.setText("Welcome " + backend.getFullName () + "!");
		} catch (Exception ex) {
			// Show error dialog
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void addButtonClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "test");
		startActivity(new Intent(this, MenuActivity.class));
	}
	
	public void viewButtonClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "test");
		startActivity(new Intent(this, ExpenseFormOverviewActivity.class));
	}

	public void logoutButtonClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "test");
//		Backend.logout();
		try {
			backend.logout();
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onLoginCompleted(Employee user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogoutCompleted() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, LoginActivity.class));
	}
}
