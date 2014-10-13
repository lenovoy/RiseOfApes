package com.vivo.scoreprovider;

import java.util.HashMap;

import com.vivo.db.DatabaseHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class ScoreProvider extends ContentProvider {

	public static final String LOG_TAG="ScoreProvider";
	
	private static final UriMatcher uriMatcher;
	static{
		uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(Score.AUTHORITY, Score.SCORE_TABLE+"/1", Score.ITEM_MY);
		uriMatcher.addURI(Score.AUTHORITY, Score.SCORE_TABLE+"/2", Score.ITEM_OTHER);
		uriMatcher.addURI(Score.AUTHORITY, Score.SCORE_TABLE, Score.ITEM);
	}
	
	private static final HashMap<String, String> scoreProjectionMap;
	static{
		scoreProjectionMap=new HashMap<String, String>();
		scoreProjectionMap.put(Score.SCORE_ID, Score.SCORE_ID);
		scoreProjectionMap.put(Score.SCORE_NAME, Score.SCORE_NAME);
		scoreProjectionMap.put(Score.SCORE_VALUE, Score.SCORE_VALUE);
	}
	
	private DatabaseHelper dbHelper;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		dbHelper=new DatabaseHelper(getContext(), Score.DATABASE_NAME, null, Score.DATABASE_VERSION);
		Log.i(LOG_TAG, "onCreate>>>>>>>");
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "ScoreProvider.query: "+uri);
		SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
		switch(uriMatcher.match(uri)){
		case Score.ITEM:
			qb.setTables(Score.SCORE_TABLE);
			qb.setProjectionMap(scoreProjectionMap);
			break;
		case Score.ITEM_MY:
		case Score.ITEM_OTHER:
			qb.setTables(Score.SCORE_TABLE);
			qb.setProjectionMap(scoreProjectionMap);
			qb.appendWhere(Score.SCORE_ID+"="+uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Error Uri: "+uri);
		}
		String orderBy;
		if(TextUtils.isEmpty(sortOrder))
			orderBy=Score.DEFAULT_SORT_ORDER;
		else
			orderBy=sortOrder;
		SQLiteDatabase db=dbHelper.getReadableDatabase();
		Cursor cursor=qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch(uriMatcher.match(uri)){
		case Score.ITEM:
			return Score.CONTENT_TYPE;
		case Score.ITEM_MY:
		case Score.ITEM_OTHER:
			return Score.CONTENT_TYPE_ITEM;
		default:
			throw new IllegalArgumentException("Error Uri: "+uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		Log.i(LOG_TAG, "ScoreProvider.insert: "+uri);
		if(uriMatcher.match(uri)!=Score.ITEM)
			throw new IllegalArgumentException("Error Uri: "+uri);
		
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		long id=db.insert(Score.SCORE_TABLE, Score.SCORE_ID, values);
		if(id<0)
			throw new SQLiteException("Unable to insert "+values+" for "+uri);
		Uri newUri=ContentUris.withAppendedId(uri, id);
		getContext().getContentResolver().notifyChange(newUri, null);
		return newUri;
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
		Log.i(LOG_TAG, "ScoreProvider.update: "+uri);
		SQLiteDatabase db=dbHelper.getWritableDatabase();
		int count=0;
		switch(uriMatcher.match(uri)){
		case Score.ITEM:
			count=db.update(Score.SCORE_TABLE, values, selection, selectionArgs);
			break;
		case Score.ITEM_MY:
		case Score.ITEM_OTHER:
			String id=uri.getPathSegments().get(1);
			count=db.update(Score.SCORE_TABLE, values, Score.SCORE_ID+"="+id, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Error Uri: "+uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
