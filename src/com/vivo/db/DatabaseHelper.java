package com.vivo.db;

import com.vivo.scoreprovider.Score;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private final String CREATE_TABLE="create table "+Score.SCORE_TABLE
										+"("+Score.SCORE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
										+Score.SCORE_NAME+" VARCHAR(30),"
										+Score.SCORE_VALUE+" FLOAT);";
	private final String INIT_TABLE="insert into "+Score.SCORE_TABLE
										+" values(1,'myscore',0),"
										+ "(2,'otherscore',0);";
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i("ScoreProvider", CREATE_TABLE);
		db.execSQL(CREATE_TABLE);
		db.execSQL(INIT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+Score.DATABASE_NAME);
		onCreate(db);
	}

}
