package com.vivo.riseofapes;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.vivo.scoreprovider.Score;

public class RatingReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Cursor cursor=context.getContentResolver().query(ContentUris.withAppendedId(Score.uri, 1), null, null, null, null);
		if(cursor.moveToFirst()){
			float score=cursor.getFloat(0);
			ContentValues values=new ContentValues();
			values.put(Score.SCORE_NAME, "otherscore");
			values.put(Score.SCORE_VALUE, score);
			context.getContentResolver().update(ContentUris.withAppendedId(Score.uri, 2), values, null, null);
		}
	}

}
