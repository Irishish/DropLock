package com.miniprojet.droplock2;

import java.util.ArrayList;

import com.miniprojet.droplock2.adapters.MyBoxesDataAdapter;
import com.miniprojet.droplock2.data.MyBoxesData;
import com.miniprojet.droplock2.tasks.MyBoxesAPITask;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
//import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyBoxesActivity extends Activity {
	
	private ArrayList<MyBoxesData> boxes;
	private ListView boxList;
	private LayoutInflater layoutInflator;
	//private InputMethodManager inMgr;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_boxes_layout);
		this.boxList = (ListView) findViewById(R.id.box_list);
		this.layoutInflator = LayoutInflater.from(this);
		
		MyBoxesAPITask myBoxesTask = new MyBoxesAPITask (MyBoxesActivity.this);
		try {

			String userID = "";
			myBoxesTask.execute(userID);
		}
		catch (Exception e)
		{
			myBoxesTask.cancel(true);
			alert (getResources().getString(R.string.no_boxes));
		}


	
		
	/**Restore any already fetched data on orientation change.
    	    final Object[] data = (Object[]) getLastNonConfigurationInstance();
    	        if(data != null) {
    	         this.boxes = (ArrayList<MyBoxessData>) data[0];

    	         boxList.setAdapter(new MyBoxesDataAdapter(this, this.boxes));
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
		myStuff[0] = this.boxes;
		return myStuff;
	}


	/**
	 * Bundle to hold refs to row items views.
	 *
	 */
	public static class MyBoxesViewHolder {
		public TextView boxID, status;
		public MyBoxesData box;
	}


	public void setBoxes(ArrayList<MyBoxesData> boxes) {
		this.boxes = boxes;
		this.boxList.setAdapter(new MyBoxesDataAdapter(this, this.layoutInflator, this.boxes));
	}

}