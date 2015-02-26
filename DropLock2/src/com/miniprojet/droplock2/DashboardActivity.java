package com.miniprojet.droplock2;





import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashboardActivity extends Activity {
	
	Button btn_myGroups;
	Button btn_myBoxes;
	Button btn_unlockBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		
		/**
		 * Creating all buttons instances
		 * */
		// Dashboard My Groups button
		btn_myGroups = (Button) findViewById(R.id.btn_my_groups);

		// Dashboard My Boxes button
		btn_myBoxes = (Button) findViewById(R.id.btn_my_boxes);

		// Dashboard Unlock Box button
		btn_unlockBox = (Button) findViewById(R.id.btn_unlock_box);

		/**
		 * Handling all button click events
		 * */

		// Listening to My Groups button click
		btn_myGroups.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//Toast.makeText(DashboardActivity.this, "btn_myGroups ", Toast.LENGTH_LONG).show();
				// Launching My Groups Screen
				Intent i = new Intent(getApplicationContext(),
						MyGroupsActivity.class);
				startActivity(i);
				
				//Pass User ID!!
			}
		});

		// Listening My Boxes click
		btn_myBoxes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				//Toast.makeText(DashboardActivity.this, "btn_myBoxes ", Toast.LENGTH_LONG).show();
				// Launching My Boxes Screen
				Intent i = new Intent(getApplicationContext(),
						MyBoxesActivity.class);
				startActivity(i);
			}
		});

		// Listening Unlock Box button click
		btn_unlockBox.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Launching create new user activity
				Intent i = new Intent(getApplicationContext(), UnlockBoxActivity.class);  
				startActivity(i);
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

}
