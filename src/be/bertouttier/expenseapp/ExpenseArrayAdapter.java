package be.bertouttier.expenseapp;

import java.text.SimpleDateFormat;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import be.bertouttier.expenseapp.Core.BL.Managers.ExpenseManager;
import be.bertouttier.expenseapp.Core.DAL.Expense;

public class ExpenseArrayAdapter extends BaseAdapter {

	private Activity context;
	private List<Expense> items;
	
	public ExpenseArrayAdapter (Activity context){
		super();
		this.context = context;
		ExpenseManager em = new ExpenseManager(context);
		this.items = em.getExpenses();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Get our object for this position
		Expense item = items.get(position);   

		//Try to reuse convertView if it's not  null, otherwise inflate it from our item layout
		// This gives us some performance gains by not always inflating a new view
		// This will sound familiar to MonoTouch developers with UITableViewCell.DequeueReusableCell()
//		View view = (convertView ?? context.LayoutInflater.Inflate(Resource.Layout.RowLayout, parent, false)) as View;

		View view;
		
		if(convertView != null) {
			view = convertView;
		} else {
			view = context.getLayoutInflater().inflate(R.layout.row_layout, parent, false);
		}
		
		//Find references to each subview in the list item's view
		ImageView evidenceImage = (ImageView) view.findViewById (R.id.evidence);
		TextView txtDate = (TextView) view.findViewById (R.id.date);
		TextView txtAmount = (TextView) view.findViewById (R.id.amount);
		TextView txtType = (TextView) view.findViewById (R.id.remarks);

		
		//Assign this item's values to the various subviews
		evidenceImage.setImageBitmap(getImageFromString(item.getEvidence()));
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM y");
		txtDate.setText(sdf.format(item.getDate().getTime()), TextView.BufferType.NORMAL);
		txtAmount.setText(item.getAmount() + " " + item.getCurrency(), TextView.BufferType.NORMAL);
		if (item.getCurrency() != "EUR") {
//			try{
//				txtAmount.setText(item.getAmount() + " " + item.getCurrency() + " (" + Backend.convertToEuro(item.getAmount(), item.getCurrency()) + " Û)", TextView.BufferType.NORMAL);
//			}catch (Exception ex) {
				txtAmount.setText(item.getAmount() + " " + item.getCurrency(), TextView.BufferType.NORMAL);
//			}
		} else {
			txtAmount.setText(item.getAmount() + " Û", TextView.BufferType.NORMAL);
		}
		txtType.setText(item.getExpenseType(), TextView.BufferType.NORMAL);

		//Finally return the view
		return view;
	}
	
	private Bitmap getImageFromString (String base64string)
	{
		byte[] imageAsBytes = Base64.decode(base64string, Base64.DEFAULT);
	    return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
	}
}
