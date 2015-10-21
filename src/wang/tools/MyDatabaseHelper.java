package wang.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast; 

public class MyDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String Create_XidianData="create table XidianData("+
			"id integer primary key autoincrement,"+
			"status varchar,"+
			"identity varchar,"+
			"account varchar,"+
			"name varchar,"+
			"xuehao varchar,"+
			"class varchar,"+
			"gonghao varchar,"+
			"managerId varchar,"+
			"shenfenzheng varchar(18),"+
			"password varchar)";
	public static final String Create_KedaData="create table KedaData("+
			"id integer primary key autoincrement,"+
			"status varchar,"+
			"identity varchar,"+
			"account varchar,"+
			"name varchar,"+
			"moniterId varchar,"+
			"xuehao varchar,"+
			"class varchar,"+
			"gonghao varchar,"+
			"managerId varchar,"+
			"shenfenzheng varchar(18),"+
			"password varchar)";
	public static final String Create_JiaodaData="create table JiaodaData("+
			"id integer primary key autoincrement,"+
			"status varchar,"+
			"identity varchar,"+
			"account varchar,"+
			"name varchar,"+
			"xuehao varchar,"+
			"class varchar,"+
			"gonghao varchar,"+
			"managerId varchar,"+
			"shenfenzheng varchar(18),"+
			"password varchar)";
	public static final String Create_XidianStudentData="create table XidianStudentData("+
			"xuehao varchar,"+
			"class varchar,"+
			"name varchar,"+
			"shenfenzheng varchar(18),"+
			"done varchar,"+
			"fail varchar,"+
			"certificate varchar)";
	public static final String Create_XidianClassData="create table XidianClassData("+
			"subject varchar,"+
			"time varchar,"+
			"credit varchar)";
	public static final String Create_KedaStudentData="create table KedaStudentData("+
			"xuehao varchar,"+
			"class varchar,"+
			"name varchar,"+
			"shenfenzheng varchar(18),"+
			"done varchar,"+
			"fail varchar,"+
			"certificate varchar)";
	public static final String Create_KedaClassData="create table KedaClassData("+
			"subject varchar,"+
			"time varchar,"+
			"credit varchar)";
	public static final String Create_JiaodaStudentData="create table JiaodaStudentData("+
			"xuehao varchar,"+
			"class varchar,"+
			"name varchar,"+
			"shenfenzheng varchar(18),"+
			"done varchar,"+
			"fail varchar,"+
			"certificate varchar)";
	public static final String Create_JiaodaClassData="create table JiaodaClassData("+
			"subject varchar,"+
			"time varchar,"+
			"credit varchar)";
	Context context;
	public MyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Create_XidianData);
		db.execSQL(Create_KedaData);
		db.execSQL(Create_JiaodaData);
		db.execSQL(Create_XidianClassData);
		db.execSQL(Create_XidianStudentData);
		db.execSQL(Create_KedaClassData);
		db.execSQL(Create_KedaStudentData);
		db.execSQL(Create_JiaodaClassData);
		db.execSQL(Create_JiaodaStudentData);
		loadClassExcel(db);
		loadStudentExcel(db);
		Toast.makeText(context, "成功创建了九张表", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists XidianData");
		db.execSQL("drop table if exists KedaData");
		db.execSQL("drop table if exists JiaodaData");
		db.execSQL("drop table if exists Chengji");
		db.execSQL("drop table if exists XidianClassData");
		db.execSQL("drop table if exists XidianStudentData");
		db.execSQL("drop table if exists JiaodaClassData");
		db.execSQL("drop table if exists JiaodaStudentData");
		db.execSQL("drop table if exists KedaClassData");
		db.execSQL("drop table if exists KedaStudentData");
		onCreate(db);
	}
	private void loadClassExcel(SQLiteDatabase db){
		BufferedReader br = null;
		String str;
		InputStream is;
		try {
			is = context.getResources().getAssets().open("Class_BukaoTimeAndXuefenTest.txt");
			br=new BufferedReader(new InputStreamReader(is));
			while((str=br.readLine())!=null){
				String[] arr=str.split(",");
				ContentValues cv=new ContentValues();
				cv.put("subject",arr[0] );
				cv.put("time", arr[1]);
				cv.put("credit", arr[2]);
				db.insert("XidianClassData", null, cv);
				db.insert("KedaClassData", null, cv);
				db.insert("JiaodaClassData", null, cv);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(br!=null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void loadStudentExcel(SQLiteDatabase db){
		BufferedReader br = null;
		String str;
		InputStream is;
		try {
			is = context.getResources().getAssets().open("Student_ListAndGradeTest.txt");
			br=new BufferedReader(new InputStreamReader(is));
			while((str=br.readLine())!=null){
				String[] arr=str.split(",");
				ContentValues cv=new ContentValues();
				cv.put("xuehao",arr[0] );
				cv.put("class",arr[1] );
				cv.put("name",arr[2] );
				cv.put("shenfenzheng",arr[3] );
				cv.put("done",arr[4] );
				cv.put("fail",arr[5] );
				cv.put("certificate", arr.length==7?arr[6]:"");
				db.insert("XidianStudentData", null, cv);
				db.insert("KedaStudentData", null, cv);
				db.insert("JiaodaStudentData", null, cv);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				if(br!=null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
