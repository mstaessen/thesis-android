package be.bertouttier.expenseapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManager;
import be.bertouttier.expenseapp.Core.BL.Managers.EmployeeManagerListener;
import be.bertouttier.expenseapp.Core.DAL.Employee;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
import android.widget.Button;

public class InfoFragment extends Fragment implements OnDateSetListener, EmployeeManagerListener {

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
	private Calendar date;
	private TextView pickDate;
	private EmployeeManager em;
	
	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		em = new EmployeeManager(getActivity().getApplicationContext(), this);
		
		View view = inflater.inflate (R.layout.info_layout, container, false);
		Spinner unitSpinner = (Spinner) view.findViewById (R.id.UnitSpinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (this.getActivity(), android.R.layout.simple_list_item_1, units_array);
		adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(adapter);
		
		try {
			unitSpinner.setSelection (em.getUnitId () - 1, false);
		} catch (Exception ex) {
			Toast.makeText (this.getActivity(), ex.getMessage(), Toast.LENGTH_SHORT).show (); 
		}

		final InfoFragment me = this;
		
		// Mont picker
		pickDate = (TextView) view.findViewById (R.id.pickDate);
		pickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d("!!", "test");
        		DatePickerDialogFragment dialog = new DatePickerDialogFragment ();
        		dialog.setDatePickerDialogFragment(getActivity(), date, me);
        		dialog.show (getFragmentManager(), null);
            }
        });
		
		date = GregorianCalendar.getInstance();
		UpdateDisplay ();
		
		//firstname, lastname, number, email
		TextView lblFirstName = (TextView) view.findViewById (R.id.txtFirstName);
		TextView lblLastName = (TextView) view.findViewById (R.id.txtLastName);
		EditText lblEmployeeNumber = (EditText) view.findViewById (R.id.txtEmployeeNumber);
		EditText lblEmail = (EditText) view.findViewById (R.id.txtEmail);

		try {
			lblFirstName.setText(em.getFirstName ());
			lblLastName.setText(em.getLastName ());
			lblEmployeeNumber.setText(String.valueOf(em.getEmployeeNumber()));
			lblEmail.setText(em.getEmail ());
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
		SimpleDateFormat format = new SimpleDateFormat("MMMM y", Locale.US);
		pickDate.setText(format.format(date.getTime()));
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		this.date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
		UpdateDisplay();
	}

	@Override
	public void onLoginCompleted(Employee user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLogoutCompleted() {
		// TODO Auto-generated method stub
		
	}
}
