package values;

import android.content.Context;
import android.content.res.Resources;

import com.allBadCats.utils.mytasks.R;
import com.allBadCats.utils.mytasks.WeekViewMain;

/**
 * Enum for handeling sets of colours used for different task layout elements. 
 * Colour sets are generaly assigned to a group.
 * 
 * This implementation works... but when I get a chance this will be made more elegant.
 * @author Erica
 *
 */
public enum ColorSet {
	// ok this is kinda gross
	GREY (1, WeekViewMain.CONTEXT.getResources().getColor(R.color.grey), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_grey), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	GREEN (2, WeekViewMain.CONTEXT.getResources().getColor(R.color.green), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_green), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	TEAL (3, WeekViewMain.CONTEXT.getResources().getColor(R.color.teal), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_teal), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	BLUE (4, WeekViewMain.CONTEXT.getResources().getColor(R.color.blue), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_blue), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	PURPLE (5, WeekViewMain.CONTEXT.getResources().getColor(R.color.purple), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_purple), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	RED (6, WeekViewMain.CONTEXT.getResources().getColor(R.color.red), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_red), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.white)),
	ORANGE (7, WeekViewMain.CONTEXT.getResources().getColor(R.color.orange), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_orange),
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_grey)),
	YELLOW (8, WeekViewMain.CONTEXT.getResources().getColor(R.color.yellow), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_yellow), 
			WeekViewMain.CONTEXT.getResources().getColor(R.color.dark_grey));

	private final int index;
	private final int baseColor;
	private final int darkColor;
	private final int textColor;
	
	ColorSet(int index,int baseColor, int darkColor, int textColor){
		this.index = index;
		this.baseColor = baseColor;
		this.darkColor = darkColor;
		this.textColor = textColor;
	}
	
	public int getIndex(){
		return index;
	}
	
	public int getBaseColor(){
		return baseColor;
	}
	
	public int getDarkColor(){
		return darkColor;
	}
	
	public int getTextColor(){
		return textColor;
	}
	
	public static ColorSet getSetFromIndex(int index){
		switch (index){
		case 1:
			return GREY;
		case 2:
			return GREEN;
		case 3:
			return TEAL;
		case 4:
			return BLUE;
		case 5:
			return PURPLE;
		case 6:
			return RED;
		case 7:
			return ORANGE;
		case 8:
			return YELLOW;
		default:
			return GREY;
		}
	}

}
