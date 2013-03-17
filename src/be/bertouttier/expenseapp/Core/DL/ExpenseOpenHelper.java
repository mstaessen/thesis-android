package be.bertouttier.expenseapp.Core.DL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class ExpenseOpenHelper extends SQLiteOpenHelper {
	public ExpenseOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	      if (!db.isReadOnly()) {
	    	  db.execSQL("PRAGMA foreign_keys=ON;");
	      }
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try{
			ExpenseTableDefinition expenseTableDefinition = new ExpenseTableDefinition();
			expenseTableDefinition.onCreate(db);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
			ExpenseTableDefinition expenseTableDefinition = new ExpenseTableDefinition();
			expenseTableDefinition.onUpgrade(db, oldVersion, newVersion);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
