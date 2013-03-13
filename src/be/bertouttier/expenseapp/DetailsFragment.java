package be.bertouttier.expenseapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailsFragment extends Fragment {
	
	public static DetailsFragment newInstance(int menuId) {
		DetailsFragment myFragment = new DetailsFragment();

	    Bundle args = new Bundle();
	    args.putInt("current_play_id", menuId);
	    myFragment.setArguments(args);

	    return myFragment;
	}
	
	public int shownPlayId()
	{
		return getArguments().getInt("current_play_id", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		if (container == null)
		{
			// Currently in a layout without a container, so no reason to create our view.
			return null;
		}

		switch(shownPlayId()) {
			case 0:
				return(inflater.inflate(R.layout.info_layout, container, false));
			case 1:
				return(inflater.inflate(R.layout.add_overview_layout, container, false));
			case 2:
				return(inflater.inflate(R.layout.add_layout, container, false));
			case 3:
				return(inflater.inflate(R.layout.sign_layout, container, false));
		}
		return null;
	}

}
