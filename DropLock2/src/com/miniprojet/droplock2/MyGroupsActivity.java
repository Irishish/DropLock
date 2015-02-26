package com.miniprojet.droplock2;

/**
 * Activity for displaying the list of groups the user is a member of.
 * 
 */



import java.util.ArrayList;


import com.miniprojet.droplock2.adapters.MyGroupsDataAdapter;
import com.miniprojet.droplock2.data.MyGroupsData;
import com.miniprojet.droplock2.tasks.MyGroupsAPITask;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
//import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyGroupsActivity extends Activity {

	private ArrayList<MyGroupsData> groups;
	private ListView groupList;
	private LayoutInflater layoutInflator;
	//private InputMethodManager inMgr;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_groups_layout);
		this.groupList = (ListView) findViewById(R.id.group_list);
		this.layoutInflator = LayoutInflater.from(this);
		
		MyGroupsAPITask myGroupsTask = new MyGroupsAPITask (MyGroupsActivity.this);
		try {

			String userID = "";
			myGroupsTask.execute(userID);
		}
		catch (Exception e)
		{
			myGroupsTask.cancel(true);
			alert (getResources().getString(R.string.no_groups));
		}


	
		
	/**Restore any already fetched data on orientation change.
    	    final Object[] data = (Object[]) getLastNonConfigurationInstance();
    	        if(data != null) {
    	         this.groups = (ArrayList<MyGroupsData>) data[0];

    	         groupList.setAdapter(new MyGroupsDataAdapter(this, this.groups));
    	        }*/
	}

	 


	/**
	 * Handy dandy alerter.
	 * @param msg the message to toast.
	 */
	public void alert (String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Save any fetched track data for orientation changes.
	 */
	@Override
	public Object onRetainNonConfigurationInstance() {
		Object[] myStuff = new Object[1];
		myStuff[0] = this.groups;
		return myStuff;
	}


	/**
	 * Bundle to hold refs to row items views.
	 *
	 */
	public static class MyGroupsViewHolder {
		public TextView groupName, boxName;
		public MyGroupsData group;
	}


	public void setGroups(ArrayList<MyGroupsData> groups) {
		this.groups = groups;
		this.groupList.setAdapter(new MyGroupsDataAdapter(this, this.layoutInflator, this.groups));
	}

}