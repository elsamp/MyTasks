package layouts;

import values.GroupIcon;
import values.GroupIcon.IconState;

import com.allBadCats.utils.mytasks.R;

import models.Group;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Simple custom RelativeLayout extended to support a couple assosiated fields as well as
 * some functionality for setting it's text.
 * @author Erica
 *
 */
public class RelativeGroupLayout extends RelativeLayout {
	
	private Context context;
	private Group group;
	private EditText groupCode;
	private EditText groupName;
	private Button groupStyle;

	public RelativeGroupLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public RelativeGroupLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RelativeGroupLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public Group getGroup(){
		return group;
	}
	
	public void setGroupFields(Group group){
		this.group = group;
		
		groupCode = (EditText) findViewById(R.id.group_code);
		groupName = (EditText) findViewById(R.id.group_desc);
		groupStyle = (Button) findViewById(R.id.colour_selection);

		groupCode.setText(group.getGroupCode());
		groupName.setText(group.getGroupName());
		groupStyle.setBackground(new GroupIcon(group.getGroupStyle(), IconState.UP));
		
	}
	
	public void updateGroupProperties(){
		
		group.setGroupCode(groupCode.getText().toString());
		group.setGroupName(groupName.getText().toString());
	}
	
}
