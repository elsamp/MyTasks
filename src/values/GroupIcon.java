package values;

import android.graphics.drawable.GradientDrawable;

import com.allBadCats.utils.mytasks.WeekViewMain;


public class GroupIcon extends GradientDrawable{
	
	public enum IconState {UP, DOWN, SELECTED};
	
	private IconState iconState;
	private ColorSet iconStyle;
	private int iconStrokeDp = 2;
	private int iconStrokeSelectedDp = 4;
	private int iconSizeDp = 40;
	private int iconStrokePx;
	private int iconSizePx;
	
	/**
	 * Contructs the Icon to reflect the passed colour and state.
	 * @param style the colour set assigned to the icon.
	 * @param state the state of the button (UP, DOWN, SELECTED)
	 */
	public GroupIcon(ColorSet style, IconState state){
		iconState = state;
		iconStyle = style;
		
		iconSizePx = (int) Math.ceil(iconSizeDp * WeekViewMain.LOGICAL_DESITY);
		
		if(iconState == IconState.SELECTED){
			iconStrokePx = (int) Math.ceil(iconStrokeSelectedDp * WeekViewMain.LOGICAL_DESITY);
		}
		else{
			iconStrokePx = (int) Math.ceil(iconStrokeDp * WeekViewMain.LOGICAL_DESITY);
		}
		
		initIcon();
	}
	
	/**
	 * Creates the icon drawable
	 */
	private void initIcon(){
		int[] colors = {iconStyle.getBaseColor(), iconStyle.getDarkColor()};
		setColors(colors);
		setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
		setShape(GradientDrawable.OVAL);
		setStroke(iconStrokePx, iconStyle.getDarkColor());
		setSize(iconSizePx, iconSizePx);
	}

}
