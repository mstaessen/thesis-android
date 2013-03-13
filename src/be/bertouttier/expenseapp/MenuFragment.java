package be.bertouttier.expenseapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuFragment extends ListFragment {

	static String[] menuItems = new String[] {"Your info","Overview","Add", "Sign and send"};
	private int _currentPlayId;
	private boolean _isDualPane;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		
		View detailsFrame = (View) getActivity().findViewById(R.id.details);
		_isDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_checked, menuItems);
		setListAdapter(adapter);
		
		if (savedInstanceState != null)
		{
			_currentPlayId = savedInstanceState.getInt("current_play_id", 0);
		}
		
		if (_isDualPane)
		{
			getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			ShowDetails(_currentPlayId);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		ShowDetails(position);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("current_play_id", _currentPlayId);
	}
	
	private void ShowDetails(int menuId)
	{
		_currentPlayId = menuId;
		if (_isDualPane)
		{
			// We can display everything in-place with fragments.
			// Have the list highlight this item and show the data.
			getListView().setItemChecked(menuId, true);
			Fragment details = new InfoFragment();

			switch(menuId) {
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

			// Check what fragment is shown, replace if needed.
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.details, details);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		else
		{
			// Otherwise we need to launch a new activity to display
			// the dialog fragment with selected text.
			Intent intent = new Intent();
			
			intent.setClass(getActivity(), DetailsActivity.class);
			intent.putExtra("current_play_id", menuId);
			startActivity(intent);
		}
	}
}
