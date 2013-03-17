package be.bertouttier.expenseapp.Core.DL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class EmployeeOpenHelper extends SQLiteOpenHelper {

	

	public EmployeeOpenHelper(Context context, String name, CursorFactory factory,
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
			EmployeeTableDefinition employeeTableDefinition = new EmployeeTableDefinition();
			employeeTableDefinition.onCreate(db);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		try{
			EmployeeTableDefinition employeeTableDefinition = new EmployeeTableDefinition();
			employeeTableDefinition.onUpgrade(db, oldVersion, newVersion);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}

