package be.bertouttier.expenseapp;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;

public class DetailsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int index = getIntent().getIntExtra("current_play_id", 0);

		Fragment details = new InfoFragment();

		switch(index) {
		case 0:
			details = new InfoFragment();
			break;
		case 1:
			details = new AddOverviewFragment();
			break;
		case 2:
			details = new AddFragment();
			break;
		case 3:
			details = new SignFragment();
			break;
		}

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, details);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}
}
