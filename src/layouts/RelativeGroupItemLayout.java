package layouts;

import models.Group;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Simple custom RelativeLayout extended to support a couple assosiated fields.
 * @author Erica
 *
 */
public class RelativeGroupItemLayout extends RelativeLayout {
	
	private Context context;
	private Group group;

	public RelativeGroupItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public RelativeGroupItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RelativeGroupItemLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public Group getGroup(){
		return group;
	}
	
	public void setGroup(Group group){
		this.group = group;
	}
	
	

}
