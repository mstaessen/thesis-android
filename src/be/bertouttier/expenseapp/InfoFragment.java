package be.bertouttier.expenseapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class InfoFragment extends Fragment implements OnDateSetListener {

	static String[] units_array = new String[]
	{
		"G20",
		"G21",
		"G22",
		"G23",
		"G30",
		"G31",
		"G32",
		"G33",
		"G34",
		"G35"
	};
	
	int DATE_DIALOG_ID = 0;
	private Date date;
	private TextView pickDate;

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate (R.layout.info_layout, container, false);

		Spinner unitSpinner = (Spinner) view.findViewById (R.id.UnitSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this.getActivity(), android.R.layout.simple_list_item_1, units_array);
		adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(adapter);
		try {
//			unitSpinner.SetSelection (Backend.getUnitId () - 1, false);
		} catch (Exception ex) {
			Toast.makeText (this.getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show (); 
		}

		// Mont picker
		pickDate = (TextView) view.findViewById (R.id.pickDate);
		date = new Date();
		UpdateDisplay ();
		
		//firstname, lastname, number, email
		TextView lblFirstName = (TextView) view.findViewById (R.id.txtFirstName);
		TextView lblLastName = (TextView) view.findViewById (R.id.txtLastName);
		EditText lblEmployeeNumber = (EditText) view.findViewById (R.id.txtEmployeeNumber);
		EditText lblEmail = (EditText) view.findViewById (R.id.txtEmail);

		try {
//			lblFirstName.setText(Backend.getFirstName ());
//			lblLastName.Text = Backend.getLastName ();
//			lblEmployeeNumber.Text = Backend.getEmployeeNumber ().ToString ();
//			lblEmail.Text = Backend.getEmail ();
		} catch (Exception ex) {
			Toast.makeText (this.getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show (); 
		}

		return view;
	}

	public void pickDateClicked(View view) {
	    // Do something in response to button
		Log.d("!!", "test");
		DatePickerDialogFragment dialog = new DatePickerDialogFragment ();
		dialog.setDatePickerDialogFragment(this.getActivity(), date, this);
		dialog.show (this.getFragmentManager(), null);
	}
	
	private void UpdateDisplay()
	{
		SimpleDateFormat format = new SimpleDateFormat("y");
		pickDate.setText(format.format(date));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		this.date = new Date(year, monthOfYear + 1, dayOfMonth);
		UpdateDisplay();
	}
}
