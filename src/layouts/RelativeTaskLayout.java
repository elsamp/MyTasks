package layouts;

import java.io.Serializable;

import values.ColorSet;
import values.GroupIcon;
import values.GroupIcon.IconState;

import com.allBadCats.utils.mytasks.R;

import models.Task;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Simple custom RelativeLayout extended to support a couple assosiated fields as well as
 * some functionality for setting it's text.
 * @author Erica
 *
 */
public class RelativeTaskLayout extends RelativeLayout {

	private Task task;
	private Context context;
	
	public RelativeTaskLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public RelativeTaskLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RelativeTaskLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setTask(Task task){
		this.task = task;
		ColorSet style = task.getTaskGroup().getGroupStyle();
		
		System.out.println("Task GroupCode " + task.getGroupId() + " " + task.getTaskGroupCode());
		
		EditText taskDesc = (EditText)findViewById(R.id.task_descript);
		EditText taskTime = (EditText)findViewById(R.id.time_alloted);
		Button taskGroupButton = (Button)findViewById(R.id.task_group_icon);
		
		taskGroupButton.setBackground(new GroupIcon(style, IconState.UP));
		taskGroupButton.setTextColor(style.getTextColor());
		
		taskDesc.setText(task.getTaskDesc());
		taskDesc.setTextColor(style.getTextColor());
		taskDesc.setBackgroundColor(style.getBaseColor());
		taskTime.setText(task.getTaskTime());
		taskGroupButton.setText(task.getTaskGroupCode());
		

	}
	
	public Task getTask(){
		return task;
	}
	

	public long getTaskId(){
		return task.getId();
	}


}
