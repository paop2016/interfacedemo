package wang.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyProvider extends ContentProvider{
	MyDatabaseHelper dbHelper;
	private static final int XidianData=0;
	private static final int XidianStudentData=1;
	private static final int XidianDataItem=2;
	private static UriMatcher uriMatcher;
	static{
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH); 
		uriMatcher.addURI("wang.interfacedemo.provider", "xidiandata", 0);
		uriMatcher.addURI("wang.interfacedemo.provider", "xidianstudentdata", 1);
		uriMatcher.addURI("wang.interfacedemo.provider", "xidiandata/#", 2);
	}
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper=new MyDatabaseHelper(getContext(), "Data", null, 33);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		Cursor cursor=null;
		switch (uriMatcher.match(uri)) {
		case XidianData:
			cursor=database.query("XidianData", projection, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		case XidianDataItem:
			String dataId=uri.getPathSegments().get(1);
			cursor=database.query("XidianData",  projection, "id=?",new String[]{dataId}, null, null, sortOrder);
			return cursor;
		case XidianStudentData:
			cursor=database.query("XidianStudentData", projection, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		default:
			return null;
		}
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch (uriMatcher.match(uri)) {
		case XidianData:
			return "vnd.android.cursor.dir/vnd.wang.interfacedemo.provider.xidiandata";
		case XidianDataItem:
			return "vnd.android.cursor.item/vnd.wang.interfacedemo.provider.xidiandata";
		case XidianStudentData:
			return "vnd.android.cursor.dir/vnd.wang.interfacedemo.provider.xidianstudentdata";
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		Uri uriReturn=null;
		switch (uriMatcher.match(uri)) {
		case XidianData:
		case XidianDataItem:
			Long dataId=database.insert("XidianData",null, values);
			uriReturn=Uri.parse("content://wang.interfacedemo.provider/xidiandata/"+dataId);
			return uriReturn;
		case XidianStudentData:
			Long stuId=database.insert("XidianStudentData", null, values);
			uriReturn=Uri.parse("content://wang.interfacedemo.provider/xidianstudentdata/stuId"+stuId);
			return uriReturn;
		default:
			return uriReturn;
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		int delateRows=0;
		switch(uriMatcher.match(uri)){
		case XidianData:
			delateRows=database.delete("XidianData", selection, selectionArgs);
			return delateRows;
		case XidianDataItem:
			String dataId=uri.getPathSegments().get(1);
			delateRows=database.delete("XidianData", "id=?", new String[]{dataId});
			return delateRows;
		case XidianStudentData:
			delateRows=database.delete("XidianStudentData",  selection, selectionArgs);
			return delateRows;
		default:
			return 0;
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int updateRows=0;
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case XidianData:
			updateRows=database.update("XidianData", values, selection, selectionArgs);
			return updateRows;
		case XidianDataItem:
			String dataId=uri.getPathSegments().get(1);
			updateRows=database.update("XidianData", values, "id=?",new String[]{dataId} );
			return updateRows;
		case XidianStudentData:
			updateRows=database.update("XidianStudentData", values, selection, selectionArgs);
			return updateRows;
		default:
			return updateRows;
		}
	}
	
}
