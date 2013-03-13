package be.bertouttier.expenseapp;

import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AbroadActivity extends Activity {

	static final int DATE_DIALOG_ID = 0;
	private Date date;
	private TextView pickDate;
	private AutoCompleteTextView txtProjectCode;
	private ImageButton chooseButton;
	private android.net.Uri imageUri;
	private Spinner currencySpinner;

	@Override
	protected void onCreate (Bundle bundle)
	{
		super.onCreate (bundle);
		
		// Create your application here
		setContentView (R.layout.abroad_tab_layout);
		
		// get views
		pickDate = (TextView) findViewById (R.id.pickDate);
		txtProjectCode = (AutoCompleteTextView) findViewById (R.id.txtProjectCode);
		currencySpinner = (Spinner) findViewById (R.id.currencySpinner);
		chooseButton = (ImageButton) findViewById (R.id.chooseImageButton);
		Button addButton = (Button) findViewById (R.id.addButton);
		EditText txtAmount = (EditText) findViewById (R.id.txtAmount);

		// set date button
		pickDate.Click += delegate {
			ShowDialog (DATE_DIALOG_ID);
		};
		date = new Date();
		UpdateDisplay ();

		try {
			// Set autocomplete view
			ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, new String[]{"test","test"});
			txtProjectCode.setAdapter(adapter);

			// set currency spinner
			ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, new String[]{"EUR","USD"});
			// Specify the layout to use when the list of choices appears
			spinnerAdapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
			// Apply the adapter to the spinner
			currencySpinner.setAdapter(spinnerAdapter);
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
		// set choose button event handler
		chooseButton.Click += chooseButtonClicked;
		
		// set add button event handler
		addButton.Click += addButtonClicked;

		txtAmount.TextChanged += OnAmountOrCurrencyChange;
		currencySpinner.ItemSelected += OnAmountOrCurrencyChange;
	}

	private void OnAmountOrCurrencyChange (object sender, EventArgs e)
	{
		TextView txtAmountEuro = findViewById<TextView> (R.id.txtAmountEuro);
		EditText txtAmount = findViewById<EditText> (R.id.txtAmount);
		currencySpinner = findViewById<Spinner> (R.id.currencySpinner);

		string test = (string)currencySpinner.SelectedItem;

		try{
			txtAmountEuro.Text = Backend.convertToEuro(float.Parse(txtAmount.Text), (string)currencySpinner.SelectedItem) + " Û";
		}catch (Exception ex) {
			txtAmountEuro.Text = "";
		}
	}
	
	private void chooseButtonClicked (object s, EventArgs args)
	{
		string[] items = { "Take Photo", "Choose Existing" };
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		AlertDialog alertDialog;
		
		builder.SetTitle("Select");
		builder.SetSingleChoiceItems(items, -1, delegate(object sender, DialogClickEventArgs e) {
			if ((int)e.Which == 0) {
				Intent takePicture = new Intent(MediaStore.ActionImageCapture);
				StartActivityForResult(takePicture, 0);
				alertDialog.Dismiss();
			}
			else if ((int)e.Which == 1) {
				Intent pickPhoto = new Intent(Intent.ActionPick, MediaStore.Images.Media.ExternalContentUri);
				StartActivityForResult(pickPhoto, 1);
				alertDialog.Dismiss();
			} 
		});
		
		alertDialog = builder.Create();
		alertDialog.Show();
	}
	
	private void addButtonClicked (object sender, EventArgs e)
	{
		// implement validation here 
		RadioGroup typeGroup = findViewById<RadioGroup> (R.id.typeGroup);
		EditText txtAmout = findViewById<EditText> (R.id.txtAmount);
		EditText txtRemarks = findViewById<EditText> (R.id.txtRemarks);
		
		if (typeGroup.CheckedRadioButtonId == R.id.otherRadioButton && txtRemarks.Text.Length <= 0) {
			Console.WriteLine("Error, no remarks");
		}

		try {
			Backend.createAbroadExpense(date, txtProjectCode.Text, float.Parse(txtAmout.Text), txtRemarks.Text, BitmapToBase64String(MediaStore.Images.Media.GetBitmap(this.ContentResolver, imageUri)), (string)currencySpinner.SelectedItem, int.Parse(findViewById<RadioButton>(typeGroup.CheckedRadioButtonId).Tag.ToString())); 
			Toast.MakeText(this, "Added expense.", ToastLength.Short).Show(); 
		} catch(Exception ex){
			Toast.MakeText(this, ex.Message, ToastLength.Short).Show();
		}
	}
	
	protected override void OnActivityResult (int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult (requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK) {
			chooseButton.setImageURI (data.getData());
			imageUri = data.getData();
		}
	}
	
	private void UpdateDisplay()
	{
		pickDate.Text = date.ToString("d");
	}
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		switch (id)
		{
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, date.getYear(), date.getMonth() - 1, date.getDate());
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
