package be.bertouttier.expenseapp;

import android.app.Fragment;
import android.app.ListFragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class AddOverviewFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
    
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ExpenseArrayAdapter adapter = new ExpenseArrayAdapter(getActivity());
		setListAdapter(adapter);
  }

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// Do something with the data

	}

}
