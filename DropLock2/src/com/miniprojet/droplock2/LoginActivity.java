package com.miniprojet.droplock2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.miniprojet.droplock2.data.UserData;
import com.miniprojet.droplock2.tasks.LoginAPITask;

public class LoginActivity extends Activity {

	private ArrayList<UserData> user;
	EditText inputEmail;
	EditText inputPassword;
	Button btn_login;
	//private ListView groupList;
	//private LayoutInflater layoutInflator;
	//private InputMethodManager inMgr;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);

		// User Login button
		btn_login = (Button) findViewById(R.id.btnLogin);

				/**
				 * Handling all button click events
				 * */

				// Listening to My Groups button click
		btn_login.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View view) {
						
						LoginAPITask loginTask = new LoginAPITask (LoginActivity.this);
						
						try {
							//Getting User input from text
							String email = inputEmail.getText().toString();
							String password = inputPassword.getText().toString();
							
							
							loginTask.execute(email, password);
							
							//Adding user to MySQLite DB
							//---------
							
							//Entering DropLock
							Intent i = new Intent(getApplicationContext(),
									DashboardActivity.class);
							startActivity(i);
							
						}
						catch (Exception e)
						{
							loginTask.cancel(true);
							alert (getResources().getString(R.string.no_groups));
						}
					}
				});
		
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

	@Override
	public Object onRetainNonConfigurationInstance() {
		Object[] myStuff = new Object[1];
		myStuff[0] = this.groups;
		return myStuff;
	}


	/**
	 * Bundle to hold refs to row items views.
	 *
	 
	public static class MyGroupsViewHolder {
		public TextView groupName, boxName;
		public MyGroupsData group;
	}
*/

	public void setUser(ArrayList<UserData> user) {
		this.user = user;
	}
	 
}