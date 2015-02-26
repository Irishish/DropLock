package com.miniprojet.droplock2.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.miniprojet.droplock2.MyBoxesActivity;
import com.miniprojet.droplock2.R;
import com.miniprojet.droplock2.data.MyBoxesData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;


/**
 * AsyncTask for fetching from DropLock My Boxes API.
 *@param String (userID), Progress (int), Response (string)
 */
public class MyBoxesAPITask extends AsyncTask<String, Integer, String> 
{
	private ProgressDialog progDialog;
	private Context context;
	private MyBoxesActivity activity;
	private static final String debugTag = "MyBoxesWebAPITask";

	/**
	 * Construct a task
	 * @param activity
	 */
	public MyBoxesAPITask(MyBoxesActivity activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Search", this.context.getResources().getString(R.string.looking_for_boxes) , true, false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(debugTag,"Background:" + Thread.currentThread().getName());
			String result = MyBoxesHelper.downloadFromServer(params);
			return result;
		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result)
	{

		ArrayList<MyBoxesData> myboxesdata = new ArrayList<MyBoxesData>();

		progDialog.dismiss();
		if (result.length() == 0) {
			this.activity.alert ("Unable to find box data. Try again later.");
			return;
		}

		try {
			JSONObject respObj = new JSONObject(result);
			//JSONObject myGroupsObj = respObj.getJSONObject("response");
			JSONArray boxes = respObj.getJSONArray("boxes");
			for(int i=0; i<boxes.length(); i++) {
				JSONObject box = boxes.getJSONObject(i);	
				String boxID = box.getString("idBoxes");
				String ownerID = box.getString("owner_id");
				String status = box.getString("Status");
				String boxKey = box.getString("box_key");

				myboxesdata.add(new MyBoxesData(boxID, ownerID, status, boxKey));	
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.activity.setBoxes(myboxesdata);

	}
}
