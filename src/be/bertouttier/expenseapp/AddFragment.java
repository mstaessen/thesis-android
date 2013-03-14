package be.bertouttier.expenseapp;

import android.app.Fragment;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class AddFragment extends Fragment {

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate (R.layout.add_layout, container, false);

		TabHost tabhost = (TabHost) view.findViewById (android.R.id.tabhost);
		LocalActivityManager mLocalActivityManager = new LocalActivityManager(this.getActivity(), false);
		mLocalActivityManager.dispatchCreate(savedInstanceState);
		tabhost.setup(mLocalActivityManager);
		
		TabHost.TabSpec spec;     // Resusable TabSpec for each tab
		Intent intent;            // Reusable Intent for each tab
		
		// Create an Intent to launch an Activity for the tab (to be reused)
		intent = new Intent(this.getActivity(), DomesticActivity.class);
		intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Initialize a TabSpec for each tab and add it to the TabHost
		spec = tabhost.newTabSpec ("domestic");
		spec.setIndicator ("Domestic", getResources().getDrawable(android.R.drawable.ic_menu_mylocation));
		spec.setContent (intent);
		tabhost.addTab (spec);

		// Do the same for the other tabs
		intent = new Intent(this.getActivity(), AbroadActivity.class);
		intent.addFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
		
		spec = tabhost.newTabSpec ("abroad");
		spec.setIndicator ("Abroad", getResources().getDrawable(android.R.drawable.ic_menu_mylocation));
		spec.setContent (intent);
		tabhost.addTab (spec);
		
		tabhost.setCurrentTab(0);

		return view;
	}

}
