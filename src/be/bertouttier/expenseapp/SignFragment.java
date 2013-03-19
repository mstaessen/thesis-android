package be.bertouttier.expenseapp;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseManager;
import be.bertouttier.expenseapp.Core.DAL.ExpenseForm;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class SignFragment extends Fragment {
	
	private SignView signView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final ExpenseManager em = new ExpenseManager(getActivity().getApplicationContext());
		
		View view = inflater.inflate(R.layout.sign_layout, container, false);
		final EditText txtRemarks = (EditText)view.findViewById (R.id.txtRemarks);
		final ToggleButton blnNotification = (ToggleButton)view.findViewById (R.id.toggleNotifications);
		signView = (SignView) view.findViewById (R.id.signView);
		
		Button clearButton = (Button)view.findViewById (R.id.clearButton);
		clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d("!!", "Clearing canvas");
        		signView.clearCanvas();
            }
        });
		
		
		Button sendButton = (Button)view.findViewById (R.id.sendButton);
		sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d("!!", "Sending");
            	
            	String signature = BitmapToBase64String(signView.canvasBitmap());
            	String remarks = txtRemarks.getText().toString();
            	boolean notification = blnNotification.isActivated();
            	
            	ExpenseForm form = new ExpenseForm(new Date(), 5, signature, notification, remarks, em.getExpenses());
            }
        });
		
		
		
		return view;
	}
	
	private String BitmapToBase64String(Bitmap bmp)
	{	
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		bmp.compress(Bitmap.CompressFormat.PNG, 100, baos); //bmp is the bitmap object   
		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}
	
}
