package be.bertouttier.expenseapp;

import java.util.Calendar;
import java.util.Date;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

public class DatePickerDialogFragment extends DialogFragment {

	private Context _context;
	private Calendar _date;
	private OnDateSetListener _listener;
	
	public DatePickerDialogFragment()
	{
	}
	
	public void setDatePickerDialogFragment(Context context, Calendar date, OnDateSetListener listener)
	{
		_context = context;
		_date = date;
		_listener = listener;
	}
	
	
	
	@Override
	public Dialog onCreateDialog(Bundle savedState)
	{
		DatePickerDialog dialog = new DatePickerDialog(_context, _listener, _date.get(Calendar.YEAR), _date.get(Calendar.MONTH), _date.get(Calendar.DATE));
		return dialog;
	}

}
