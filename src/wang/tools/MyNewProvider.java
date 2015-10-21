package wang.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyNewProvider extends ContentProvider{
	MyDatabaseHelper dbHelper;
	private static final int XidianData=0;
	private static final int XidianDataItem=1;
	private static UriMatcher uriMatcher;
	static{
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("wang.interfacedemo.provider", "xidiandata", 0);
		uriMatcher.addURI("wang.interfacedemo.provider", "xidiandata/#", 1);
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
		Cursor cursor=null;
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case XidianData:
			cursor=database.query("XidianData", projection, selection, selectionArgs, null, null, sortOrder);
			return cursor;
		case XidianDataItem:
			cursor=database.query("XidianData", projection, "id=?", new String[]{"1"}, null, null, sortOrder);
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
		default:
			return null;
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
