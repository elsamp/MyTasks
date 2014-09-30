package models;

import java.io.Serializable;
import java.util.Date;

import values.ColorSet;

import database.DataSource;

/**
 * Simple data object for holding Task attributes and passing values to the DataSource.
 * @author Erica
 *
 */
public class Task implements Serializable  {

	private static final long serialVersionUID = 1L;
	private long id;
	private long groupId;
	private String taskDate;
	private String taskTime;
	private String taskDesc;
	
	private Group group;

	
	public Task(String dataDate){
		groupId = 1;
		taskDate = dataDate;
	}
	
	public Task(long id, long groupId, String taskDate, String taskTime, String taskDesc ){
		this.id = id;
		this.groupId = groupId;
		this.taskDate = taskDate;
		this.taskTime = taskTime;
		this.taskDesc = taskDesc;
	}
	
	public Task(Task task){
		id = task.getId();
		groupId = task.getGroupId();
		taskDate = task.getTaskDate();
		taskTime = task.getTaskTime();
		taskDesc = task.getTaskDesc();
	}
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public long getGroupId(){
		return groupId;
	}
	public void setGroupId(long groupId){
		this.groupId = groupId;
	}
	
	public String getTaskDate(){
		return taskDate;
	}
	
	public void setTaskDate(String taskDate){
		this.taskDate = taskDate;
	}
	
	public String getTaskTime(){
		return taskTime;
	}
	
	public void setTaskTime(String taskTime){
		this.taskTime = taskTime;
	}
	
	public String getTaskDesc(){
		return taskDesc;
	}
	
	public void setTaskDesc(String taskDesc){
		this.taskDesc = taskDesc;
	}
	
	private Group getGroupByTask(){
		if (group == null){
			group = DataSource.dsInstance.getGroupByTask(this);
		}
		
		return group;
	}
	
	public String getTaskGroupCode(){
		return getGroupByTask().getGroupCode();
	}
	
	public Group getTaskGroup(){
		return getGroupByTask();
	}
	
	public ColorSet getTaskGroupColour(){
		return getGroupByTask().getGroupStyle();
	}

}
