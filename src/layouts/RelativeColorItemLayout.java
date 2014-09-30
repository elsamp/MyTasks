package layouts;

import values.ColorSet;
import models.Group;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Simple custom RelativeLayout extended to include some assosiated data.
 * @author Erica
 *
 */
public class RelativeColorItemLayout extends RelativeLayout {
	
	private Context context;
	private ColorSet colorSet;

	public RelativeColorItemLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
	
	public RelativeColorItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public RelativeColorItemLayout(Context context) {
		super(context);
		this.context = context;
	}
	
	public ColorSet getColorSet(){
		return colorSet;
	}
	
	public void setColorSet(ColorSet colorSet){
		this.colorSet = colorSet;
	}
	
	

}
