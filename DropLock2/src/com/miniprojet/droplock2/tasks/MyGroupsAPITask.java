package com.miniprojet.droplock2.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.miniprojet.droplock2.R;
import com.miniprojet.droplock2.MyGroupsActivity;
import com.miniprojet.droplock2.data.MyGroupsData;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * AsyncTask for fetching from DropLock My Groups API.
 *@param String (userID), Progress (int), Response (string)
 */
public class MyGroupsAPITask extends AsyncTask<String, Integer, String> 
{
	private ProgressDialog progDialog;
	private Context context;
	private MyGroupsActivity activity;
	private static final String debugTag = "MyGroupsWebAPITask";

	/**
	 * Construct a task
	 * @param activity
	 */
	public MyGroupsAPITask(MyGroupsActivity activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Search", this.context.getResources().getString(R.string.looking_for_groups) , true, false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(debugTag,"Background:" + Thread.currentThread().getName());
			String result = MyGroupsHelper.downloadFromServer(params);
			return result;
		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result)
	{

		ArrayList<MyGroupsData> mygroupsdata = new ArrayList<MyGroupsData>();

		progDialog.dismiss();
		if (result.length() == 0) {
			this.activity.alert ("Unable to find group data. Try again later.");
			return;
		}

		try {
			JSONObject respObj = new JSONObject(result);
			//JSONObject myGroupsObj = respObj.getJSONObject("response");
			JSONArray groups = respObj.getJSONArray("groups");
			for(int i=0; i<groups.length(); i++) {
				JSONObject group = groups.getJSONObject(i);	
				String groupID = group.getString("idGroups");
				String groupOwnerID = group.getString("group_owner_id");
				String groupBoxID = group.getString("group_box_id");
				String groupName = group.getString("group_name");

				mygroupsdata.add(new MyGroupsData(groupID, groupOwnerID, groupBoxID, groupName));	
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.activity.setGroups(mygroupsdata);

	}
}
