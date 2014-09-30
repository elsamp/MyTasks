package adapters;

import java.util.List;

import values.ColorSet;
import values.GroupIcon;
import values.GroupIcon.IconState;

import layouts.RelativeColorItemLayout;
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

public class ColorArrayAdapter extends ArrayAdapter<ColorSet>{
	
	private Context context;
	private int layoutResource;
	private List<ColorSet> colors;

	public ColorArrayAdapter(Context context, int resource, List<ColorSet> objects) {
		super(context, resource, objects);
		this.context = context;
		layoutResource = resource;
		colors = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeColorItemLayout colorItem = (RelativeColorItemLayout) inflater.inflate(layoutResource, parent, false);

		colorItem.setPadding(20, 10, 20, 10);
		
		TextView colorName = (TextView) colorItem.findViewById(R.id.color_name);
		TextView colorText = (TextView) colorItem.findViewById(R.id.color_text);
		ImageView colorIcon = (ImageView) colorItem.findViewById(R.id.color_icon); 
		
		colorName.setText(colors.get(position).toString());
		colorText.setTextColor(colors.get(position).getTextColor());
		colorIcon.setBackground(new GroupIcon(colors.get(position), IconState.UP));
		
		colorItem.setColorSet(colors.get(position));
		
        return colorItem;
	}
}
