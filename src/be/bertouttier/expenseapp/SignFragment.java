package be.bertouttier.expenseapp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

public class SignFragment extends Fragment {
	
	private SignView signView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.sign_layout, container, false);
		
		Button clearButton = (Button)view.findViewById (R.id.clearButton);
		Button sendButton = (Button)view.findViewById (R.id.sendButton);
		
		EditText remarks = (EditText)view.findViewById (R.id.txtRemarks);
		ToggleButton notification = (ToggleButton)view.findViewById (R.id.toggleNotifications);

		signView = (SignView) view.findViewById (R.id.signView);
		
		return view;
	}
	
	public void clearButtonClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "Clearing canvas");
		signView.clearCanvas();
	}

	public void sendButtonClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "Sending");
	}
	
}