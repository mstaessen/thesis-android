package be.bertouttier.expenseapp;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import be.bertouttier.expenseapp.Core.BL.Backend;
import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseManager;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DomesticActivity extends Activity {

	static final int DATE_DIALOG_ID = 0;
	private Calendar date;
	private TextView pickDate;
	private AutoCompleteTextView txtProjectCode;
	private ImageButton chooseButton;
	private android.net.Uri imageUri;
	private Backend backend;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.domestic, menu);
		return true;
	}

	@Override
	protected void onCreate (Bundle bundle)
	{
		super.onCreate(bundle);
		setContentView(R.layout.domestic_tab_layout);
		
		backend = Backend.getInstance(getApplicationContext(), null);
		
		// get views
		pickDate = (TextView) findViewById (R.id.pickDate);
		txtProjectCode = (AutoCompleteTextView) findViewById (R.id.txtProjectCode);
		chooseButton = (ImageButton) findViewById (R.id.chooseImageButton);

		date = new GregorianCalendar();
		UpdateDisplay ();

		try {
			// Set autocomplete view
			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, (String[])backend.getProjectCodes().values().toArray());
			txtProjectCode.setAdapter(adapter);
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	public void pickDateClicked(View view)
	{
		showDialog (DATE_DIALOG_ID);
	}
	
	public void chooseButtonClicked(View view)
	{
		String[] items = { "Take Photo", "Choose Existing" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog;
		
		builder.setTitle("Select");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            	if (which == 0) {
    				Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    				startActivityForResult(takePicture, 0);
    				dialog.dismiss();
    			}
    			else if (which == 1) {
    				Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    				startActivityForResult(pickPhoto, 1);
    				dialog.dismiss();
    			}
            }
        });
		
		alertDialog = builder.create();
		alertDialog.show();
	}

	public void addButtonClicked(View view)
	{
		// implement validation here 
		RadioGroup typeGroup = (RadioGroup) findViewById (R.id.typeGroup);
		EditText txtAmout = (EditText) findViewById (R.id.txtAmount);
		EditText txtRemarks = (EditText) findViewById (R.id.txtRemarks);

		if (typeGroup.getCheckedRadioButtonId() == R.id.otherRadioButton && txtRemarks.getText().length() <= 0) {
			Log.d("!!", "Error, no remarks");
		}

		try {
//			em.createDomesticExpense(date, txtProjectCode.getText().toString(), Float.parseFloat(txtAmout.getText().toString()), txtRemarks.getText().toString(), BitmapToBase64String(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri)) , Integer.parseInt(findViewById(typeGroup.getCheckedRadioButtonId()).getTag().toString())); 
			backend.createDomesticExpense(date.getTime(), txtProjectCode.getText().toString(), Float.parseFloat(txtAmout.getText().toString()), txtRemarks.getText().toString(), "TestImg" , Integer.parseInt(findViewById(typeGroup.getCheckedRadioButtonId()).getTag().toString())); 
			Toast.makeText(this, "Added expense.", Toast.LENGTH_SHORT).show(); 
		} catch(Exception ex){
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult (requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			chooseButton.setImageURI (data.getData());
			imageUri = data.getData();
		}
	}

	private void UpdateDisplay()
	{
		SimpleDateFormat format = new SimpleDateFormat("d MMMM y");
		pickDate.setText(format.format(date.getTime()));
	}
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
		}
		return null;
	}

	private String BitmapToBase64String(Bitmap bmp)
	{	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos); //bmp is the bitmap object   
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                	date = new GregorianCalendar(year, monthOfYear, dayOfMonth);
            		UpdateDisplay();
                }
            };
	
}
