package layouts;

import java.io.Serializable;
import java.util.ArrayList;

import models.Task;

import com.allBadCats.utils.mytasks.R;

import containers.Day;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Simple custom RelativeLayout extended to include some assosiated values and additional 
 * functionality for setting it's assosiated day object.
 * @author Erica
 *
 */
public class RelativeDayLayout extends RelativeLayout{
	
	private Day day;
	private Context context;
	private ArrayList<RelativeTaskLayout> taskViews = new ArrayList<RelativeTaskLayout>();

	public RelativeDayLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public RelativeDayLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RelativeDayLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public void setDay(Day day){
		this.day = day;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView dateText = (TextView)this.findViewById(R.id.week_day_label);
		dateText.setText(day.getDateString());
		
		if(day.isWeekenDay()){
			dateText.setBackgroundColor(getResources().getColor(R.color.dark_grey));
		}

        for(Task task : day.getTaskList()){ 
        	RelativeTaskLayout taskLayout = new RelativeTaskLayout(context);
        	taskLayout = (RelativeTaskLayout)inflater.inflate(R.layout.layout_task_item, this, false);
        	LinearLayout taskContainer = (LinearLayout) this.findViewById(R.id.task_container);
        	taskLayout.setTask(task);
        	System.out.println(task.getGroupId());
        	taskViews.add(taskLayout);
        	taskContainer.addView(taskLayout);
        }

	}
	
	public Day getDay(){
		return day;
	}
	
	public ArrayList<RelativeTaskLayout> getTaskLayouts(){
		return taskViews;
	}

}
