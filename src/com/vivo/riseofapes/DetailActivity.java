package com.vivo.riseofapes;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

public class DetailActivity extends Activity {

	private ExpandableListView expandView;
	private List<String> group;
	private List<List<String>> child;
	private BaseExpandableListAdapter adapter;
	
	
	public void initialData(){
		group=new ArrayList<String>();
		child=new ArrayList<List<String>>();
		adapter = new InfoAdapter(DetailActivity.this);
		for(int i=0;i<8;i++)
			child.add(new ArrayList<String>());
		group.add("导演");
		group.add("编剧");
		group.add("主演");
		group.add("类型");
		group.add("国家");
		group.add("语言");
		group.add("上映时间");
		group.add("片长");
	}
	
	public class InfoAdapter extends BaseExpandableListAdapter{

		Activity activity;
		public InfoAdapter(Activity a) {
			// TODO Auto-generated constructor stub
			activity=a;
		}
		
		
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return group.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			return child.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return group.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return child.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView view=getGenericView(group.get(groupPosition));
			view.setTextSize(25);
			view.getPaint().setFakeBoldText(true);
			return view;
		}

		@SuppressLint("HandlerLeak")
		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			TextView view=getGenericView(child.get(groupPosition).get(childPosition));
			view.setTextSize(15);
			return view;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onGroupExpanded(int groupPosition) {
			if(child.get(groupPosition).size()!=0)
				return;
			final int groupPos=groupPosition;
			final String groupName=String.valueOf(groupPosition);
			Runnable runnable=new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					String url="http://172.20.200.148:8080/Servlet/apes";
					HttpPost httpPost=new HttpPost(url);
					ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
					params.add(new BasicNameValuePair("itemName",groupName));
					HttpResponse httpResponse=null;
					
					try {
						httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
						httpResponse=new DefaultHttpClient().execute(httpPost);
						if(httpResponse.getStatusLine().getStatusCode()==200){
							String result=EntityUtils.toString(httpResponse.getEntity());
							
//							mHandler.obtainMessage(groupPos,URLDecoder.decode(result, "utf-8")).sendToTarget();;
							Message message=mHandler.obtainMessage(groupPos,URLDecoder.decode(result, "utf-8") );
							mHandler.sendMessageDelayed(message, 5000);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			};
			Log.i("qqqqqqqqqqqqqqqqqqqq", "waht");
			new Thread(runnable).start();
	    }
		
		Handler mHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(child.get(msg.what).size()==0)
					child.get(msg.what).add((String)msg.obj);
				
				adapter.notifyDataSetChanged();
			}
			
		};
		
		public TextView getGenericView(String s){
			AbsListView.LayoutParams lp=new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,80);
			TextView view=new TextView(activity);
			view.setLayoutParams(lp);
			view.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
			view.setPadding(80, 5, 0, 5);
			view.setText(s);
			return view;
		}
		
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_expand);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlemain);
		
		initialData();
		expandView=(ExpandableListView)findViewById(R.id.expandList);
		expandView.setAdapter(adapter);
//		expandView.setOnGroupClickListener(new OnGroupClickListener() {
//			
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//				// TODO Auto-generated method stub
//				final int groupPos=groupPosition;
//				final String groupName=String.valueOf(groupPosition);
//				Runnable runnable=new Runnable(){
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						String url="http://172.20.200.148:8080/Servlet/apes";
//						HttpPost httpPost=new HttpPost(url);
//						ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
//						params.add(new BasicNameValuePair("itemName",groupName));
//						HttpResponse httpResponse=null;
//						
//						try {
//							httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
//							httpResponse=new DefaultHttpClient().execute(httpPost);
//							if(httpResponse.getStatusLine().getStatusCode()==200){
//								String result=EntityUtils.toString(httpResponse.getEntity());
//								mHandler.obtainMessage(groupPos,URLDecoder.decode(result, "utf-8")).sendToTarget();
//							}
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					
//				};
//				Log.i("qqqqqqqqqqqqqqqqqqqq", "waht");
//				new Thread(runnable).start();
//				return false;
//			}
//		});
	}

	
}
