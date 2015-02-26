package com.miniprojet.droplock2.tasks;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.miniprojet.droplock2.LoginActivity;
import com.miniprojet.droplock2.R;
import com.miniprojet.droplock2.data.UserData;


/**
 * AsyncTask for fetching from DropLock My Groups API.
 *@param Params (email and passowrd), Progress (int), Response (string)
 */
public class LoginAPITask extends AsyncTask<String, Integer, String> 
{
	private ProgressDialog progDialog;
	private Context context;
	private LoginActivity activity;
	private static final String debugTag = "LoginAPITask";
	private static String inputPassword;
	private static String inputEmail;

	/**
	 * Construct a task
	 * @param activity
	 */
	public LoginAPITask(LoginActivity activity) {
		super();
		this.activity = activity;
		this.context = this.activity.getApplicationContext();
		
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progDialog = ProgressDialog.show(this.activity, "Logging In", this.context.getResources().getString(R.string.logging) , true, false);
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			Log.d(debugTag,"Background:" + Thread.currentThread().getName());
			inputEmail = params[0];
			inputPassword = params[1];
			String result = LoginHelper.downloadFromServer(params);
			return result;
		} catch (Exception e) {
			return new String();
		}
	}

	@Override
	protected void onPostExecute(String result)
	{

		ArrayList<UserData> userdata = new ArrayList<UserData>();

		progDialog.dismiss();
		if (result.length() == 0) {
			this.activity.alert ("Unable to find user data. Try again later.");
			return;
		}

		try {
			JSONObject respObj = new JSONObject(result);
			//JSONObject myGroupsObj = respObj.getJSONObject("response");
			JSONArray users = respObj.getJSONArray("user");
			for(int i=0; i<users.length(); i++) {
				JSONObject user = users.getJSONObject(i);	
				String userID = user.getString("idUsers");
				String firstName = user.getString("first_name");
				String lastName = user.getString("last_name");
				String email = inputEmail;
				String phone_num = user.getString("phone_nume");
				String passowrd = inputPassword;
				String api_key = user.getString("api_key");

				userdata.add(new UserData(userID, firstName, lastName, email, phone_num, passowrd, api_key));
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//this.activity.setGroups(userdata);

	}
}
