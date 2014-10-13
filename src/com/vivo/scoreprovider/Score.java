package com.vivo.scoreprovider;

import android.net.Uri;

public class Score {

	public static final String AUTHORITY="com.vivo.scoreprovider.scoreprovider";
	
	public static final String DATABASE_NAME="Score.db";
	public static final int DATABASE_VERSION=1;
	public static final String SCORE_TABLE="score";
	
	public static final int ITEM_MY=1;
	public static final int ITEM_OTHER=2;
	public static final int ITEM=3;
	
	public static final Uri uri=Uri.parse("content://"+AUTHORITY+"/"+SCORE_TABLE);
	public static final String CONTENT_TYPE="vnd.android.cursor.dir/vnd.scoreprovider.user";
	public static final String CONTENT_TYPE_ITEM="vnd.android.cursor.item/vnd.scoreprovider.user";
	public static final String SCORE_ID="_id";
	public static final String SCORE_NAME="name";
	public static final String SCORE_VALUE="value";
	public static final String DEFAULT_SORT_ORDER="_id asc";
}
