package be.bertouttier.expenseapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
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
	private Date date;
	private TextView pickDate;
	private AutoCompleteTextView txtProjectCode;
	private ImageButton chooseButton;
	private android.net.Uri imageUri;
	
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

		// get views
		pickDate = (TextView) findViewById (R.id.pickDate);
		txtProjectCode = (AutoCompleteTextView) findViewById (R.id.txtProjectCode);
		chooseButton = (ImageButton) findViewById (R.id.chooseImageButton);
		Button addButton = (Button) findViewById (R.id.addButton);

		date = new Date();
		UpdateDisplay ();

		try {
			// Set autocomplete view
//			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, Backend.projectCodes.Values.ToArray ());
			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, new String[] {"Your info","Overview","Add"});
			txtProjectCode.setAdapter(adapter);
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}

	private void pickDateClicked()
	{
		showDialog (DATE_DIALOG_ID);
	}
	
	private void chooseButtonClicked ()
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

	private void addButtonClicked ()
	{
		// implement validation here 
		RadioGroup typeGroup = (RadioGroup) findViewById (R.id.typeGroup);
		EditText txtAmout = (EditText) findViewById (R.id.txtAmount);
		EditText txtRemarks = (EditText) findViewById (R.id.txtRemarks);

		if (typeGroup.getCheckedRadioButtonId() == R.id.otherRadioButton && txtRemarks.getText().length() <= 0) {
			Log.d("!!", "Error, no remarks");
		}

		try {
//			Backend.createDomesticExpense(date, txtProjectCode.Text, float.Parse(txtAmout.Text), txtRemarks.Text, BitmapToBase64String(MediaStore.Images.Media.GetBitmap(this.ContentResolver, imageUri)) , int.Parse(findViewById<RadioButton>(typeGroup.CheckedRadioButtonId).Tag.ToString())); 
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
		SimpleDateFormat format = new SimpleDateFormat("d");
		pickDate.setText(format.format(date));
	}
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
			case DATE_DIALOG_ID:
				return new DatePickerDialog(this, mDateSetListener, date.getYear(), date.getMonth() - 1, date.getDay());
		}
		return null;
	}

	private String BitmapToBase64String(Bitmap bmp)
	{
//		using (MemoryStream stream = new MemoryStream())
//		{
//			if (bmp.Compress(Bitmap.CompressFormat.Png, 100, stream))
//			{
//				byte[] image = stream.ToArray();
//				return Convert.ToBase64String(image);
//			}
//			return null;
//		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                	date = new Date(year, monthOfYear, dayOfMonth);
            		UpdateDisplay();
                }
            };
	
}
