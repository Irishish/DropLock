package com.miniprojet.droplock2.adapters;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miniprojet.droplock2.MyBoxesActivity;
import com.miniprojet.droplock2.MyBoxesActivity.MyBoxesViewHolder;
import com.miniprojet.droplock2.R;
import com.miniprojet.droplock2.data.MyBoxesData;


public class MyBoxesDataAdapter extends BaseAdapter implements OnClickListener {

	private static final String debugTag = "MyGroupsDataAdapter";
	private MyBoxesActivity activity;
	private LayoutInflater layoutInflater;
	private ArrayList<MyBoxesData> boxes;

	public MyBoxesDataAdapter (MyBoxesActivity a, LayoutInflater l, ArrayList<MyBoxesData> data)
	{
		this.activity = a;
		//this.imgFetcher = i;
		this.layoutInflater = l;
		this.boxes = data;
	}

	@Override
	public int getCount() {
		return this.boxes.size();
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
		MyBoxesViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate (R.layout.box_row, parent, false);
			holder = new MyBoxesViewHolder();
			holder.boxID = (TextView) convertView.findViewById(R.id.box_id);
			holder.status = (TextView) convertView.findViewById(R.id.box_status);
			//holder.icon = (ImageView) convertView.findViewById(R.id.group_icon);
			//holder.trackButton = (Button) convertView.findViewById(R.id.track_button);
			//holder.trackButton.setTag(holder);
			convertView.setTag(holder);
		}
		else {
			holder = (MyBoxesViewHolder) convertView.getTag();
		}

		convertView.setOnClickListener(this);

		MyBoxesData box = boxes.get(position);
		holder.box = box;
		holder.boxID.setText(box.getBoxID());
		holder.status.setText(box.getStatus());
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
		MyBoxesViewHolder holder = (MyBoxesViewHolder) v.getTag();
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

