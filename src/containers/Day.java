package containers;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Locale;


import android.content.Context;

import models.Task;

/**
 * Simple data object for holding day attributes.
 * @author Erica
 *
 */
public class Day implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	private Date date;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE | MMM. d",Locale.US);
	private SimpleDateFormat dataFormat = new SimpleDateFormat("d:MM:y",Locale.US);
	private boolean isWeekendDay;

	private ArrayList<Task> taskList;
	
	public Day(Date date, boolean isWeekendDay){
		this.isWeekendDay = isWeekendDay;
		this.date = date;
	}
	
	/**
	 * 
	 * @return date string in data format for saving to the database.
	 */
	public String getDataDate(){
		return dataFormat.format(date);
	}
	
	/**
	 * 
	 * @return date string in typical display format for displaying in UI.
	 */
	public String getDateString(){
		return dateFormat.format(date);
	}
	
	public ArrayList<Task> getTaskList(){
		return taskList;
	}
	
	public boolean isWeekenDay(){
		return isWeekendDay;
	}
	
	public void addTask(Task task){
		taskList.add(task);
	}

	public void setTaskList(ArrayList<Task> taskList){
		this.taskList = taskList;
	}

}
