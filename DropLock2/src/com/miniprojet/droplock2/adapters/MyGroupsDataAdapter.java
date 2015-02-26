package com.miniprojet.droplock2.adapters;

import java.util.ArrayList;

import com.miniprojet.droplock2.MyGroupsActivity;
import com.miniprojet.droplock2.MyGroupsActivity.MyGroupsViewHolder;
import com.miniprojet.droplock2.R;
import com.miniprojet.droplock2.data.MyGroupsData;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyGroupsDataAdapter extends BaseAdapter implements OnClickListener {

	private static final String debugTag = "MyGroupsDataAdapter";
	private MyGroupsActivity activity;
	private LayoutInflater layoutInflater;
	private ArrayList<MyGroupsData> groups;

	public MyGroupsDataAdapter (MyGroupsActivity a, LayoutInflater l, ArrayList<MyGroupsData> data)
	{
		this.activity = a;
		//this.imgFetcher = i;
		this.layoutInflater = l;
		this.groups = data;
	}

	@Override
	public int getCount() {
		return this.groups.size();
	}

	@Override
	public boolean areAllItemsEnabled ()
	{
		return true;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGroupsViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate (R.layout.group_row, parent, false);
			holder = new MyGroupsViewHolder();
			holder.groupName = (TextView) convertView.findViewById(R.id.group_name);
			holder.boxName = (TextView) convertView.findViewById(R.id.group_box_name);
			//holder.icon = (ImageView) convertView.findViewById(R.id.group_icon);
			//holder.trackButton = (Button) convertView.findViewById(R.id.track_button);
			//holder.trackButton.setTag(holder);
			convertView.setTag(holder);
		}
		else {
			holder = (MyGroupsViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);

		MyGroupsData group = groups.get(position);
		holder.group = group;
		holder.groupName.setText(group.getGroupName());
		holder.boxName.setText(group.getGroupBoxID());
		/**holder.trackButton.setOnClickListener(this);
		if(group.getImageUrl() != null) {
			holder.icon.setTag(group.getImageUrl());
			Drawable dr = imgFetcher.loadImage(this, holder.icon);
			if(dr != null) {
				holder.icon.setImageDrawable(dr);
			}
		} else {
			holder.icon.setImageResource(R.drawable.filler_icon);
		}*/

		return convertView;
	}

	@Override
	public void onClick(View v) {
		MyGroupsViewHolder holder = (MyGroupsViewHolder) v.getTag();
		/**if (v instanceof Button) {

			Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
					Uri.parse(holder.group.getGroupName()));
			this.activity.startActivity(intent);

		} else 
			*/ if (v instanceof View) {
				//Toast.makeText(getApplicationContext(), "Item Clicked", Toast.LENGTH_LONG).show();
			//this.activity.startActivity(intent); START Activity and pass data
		}
		Log.d(debugTag,"OnClick pressed.");

	}

}
