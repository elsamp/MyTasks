package adapters;

import java.util.List;

import values.GroupIcon;
import values.GroupIcon.IconState;

import layouts.RelativeGroupItemLayout;
import models.Group;
import models.Task;


import com.allBadCats.utils.mytasks.R;

import containers.Day;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GroupArrayAdapter extends ArrayAdapter<Group>{
	
	private Context context;
	private int layoutResource;
	private List<Group> groups;

	public GroupArrayAdapter(Context context, int resource, List<Group> objects) {
		super(context, resource, objects);
		this.context = context;
		layoutResource = resource;
		groups = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeGroupItemLayout groupItem = (RelativeGroupItemLayout) inflater.inflate(layoutResource, parent, false);

		groupItem.setPadding(20, 10, 20, 10);
		
		TextView groupName = (TextView) groupItem.findViewById(R.id.group_name);
		TextView groupCode = (TextView) groupItem.findViewById(R.id.group_code);
		ImageView groupIcon = (ImageView) groupItem.findViewById(R.id.group_icon); 
		
		groupItem.setGroup(groups.get(position));
		groupName.setText(groups.get(position).getGroupName());
		groupCode.setText(groups.get(position).getGroupCode());
		groupCode.setTextColor(groups.get(position).getGroupStyle().getTextColor());
		groupIcon.setBackground(new GroupIcon(groups.get(position).getGroupStyle(), IconState.UP));
		
		System.out.println("Returning view position: " + position);
		
        return groupItem;

	}
}
