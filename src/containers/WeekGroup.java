package containers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.content.Context;

/**
 * Simple data object for holding and setting the days assosiated with the given currently displayed week.
 * @author Erica
 *
 */
public class WeekGroup implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private GregorianCalendar cal = new GregorianCalendar();
	private List<Day> dayList;
	private SimpleDateFormat weekRangeFormatter = new SimpleDateFormat("MMMM d",Locale.US);
	private Date weekStartDate;
	private Date weekEndDate;
	
	public WeekGroup(){
		dayList = new ArrayList<Day>();
		setWeekDays();
	}
	
	public List<Day> getDayList(){
		return dayList;
	}
	
	/**
	 * 
	 * @return string week range to be displayed in the UI.
	 */
	public String getWeekRangeString(){
		
		String startDate = weekRangeFormatter.format(weekStartDate);
		String endDate = weekRangeFormatter.format(weekEndDate);
		
		return startDate + " - " + endDate;
	}
	
	public void incrementWeek(){
		cal.add(Calendar.WEEK_OF_YEAR, 1);
		setWeekDays();
	}
	
	public void decrementWeek(){
		System.out.println("Decrement Week: ");
		cal.add(Calendar.WEEK_OF_YEAR, -1);
		setWeekDays();
	}
	
	/**
	 * Sets the dayList days for the currently defined week. If the increment and decrement
	 * functions have not been called the first day in the week will be the current day.
	 */
	public void setWeekDays(){
		System.out.println("Setting Days: ");
		dayList.clear();
		boolean isWeekend;
		weekStartDate = cal.getTime();
		
		for(int i=0 ; i < 7 ; i++){
			//System.out.println("setting days:" + cal.getTime());
			// Marks weekend days as such for display in UI.
			if(cal.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY || 
					cal.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY){
				isWeekend = true;
			}
			else{
				isWeekend = false;
			}
			
			dayList.add(new Day(cal.getTime(), isWeekend));
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		cal.add(Calendar.DAY_OF_MONTH, -1);
		weekEndDate = cal.getTime();
		cal.add(Calendar.DAY_OF_MONTH, -6);
	}

}
