package be.bertouttier.expenseapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		
		// Get buttons
		Button addButton = (Button) findViewById(R.id.addButton);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	/** Called when the user clicks the Send button */
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
		startActivity(new Intent(this, LoginActivity.class));
	}
}
