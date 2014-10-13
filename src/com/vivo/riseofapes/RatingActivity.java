package com.vivo.riseofapes;

import com.vivo.scoreprovider.Score;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class RatingActivity extends Activity {
	RatingBar myBar;
	RatingBar otherBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.rating_bar);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.rating);
		
		myBar=(RatingBar)findViewById(R.id.my_rating);
		otherBar=(RatingBar)findViewById(R.id.other_rating);
		
//		@SuppressWarnings("unchecked")
//		ArrayList<Float> scores=(ArrayList<Float>)getIntent().getSerializableExtra("scores");
		float[] scores=getIntent().getFloatArrayExtra("scores");
		myBar.setRating(scores[0]);
		otherBar.setRating(scores[1]);
		
		myBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				// TODO Auto-generated method stub
				ContentValues values=new ContentValues();
				values.put(Score.SCORE_NAME, "myscore");
				values.put(Score.SCORE_VALUE, rating);
				getContentResolver().update(ContentUris.withAppendedId(Score.uri, 1), values, null, null);
				Toast.makeText(getApplicationContext(), "Rating success!", Toast.LENGTH_LONG).show();
				
				Intent intent=new Intent();
				intent.setAction("com.vivo.rating");
				intent.putExtra("msg", "ÒÑÆÀ·Ö");
				sendBroadcast(intent);
			}
		});
	}
	
}
