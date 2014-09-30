package models;

import java.io.Serializable;

import values.ColorSet;

/**
 * Simple data object for holding Group attributes and passing values to the DataSource.
 * @author Erica
 *
 */
public class Group implements Serializable  {

	private static final long serialVersionUID = 1L;
	private long id;
	private int groupStyle;
	private String groupCode; 
	private String groupName;
	
	public Group(){
		groupStyle = 1;
		groupCode = "--";
		groupName = "Misc.";
	}
	
	public Group(Group group){
		groupStyle = group.getGroupStyle().getIndex();
		groupCode = group.getGroupCode();
		groupName = group.getGroupName();
		id = group.getGroupId();
	}
	/* Getters */

	public long getGroupId(){
		return id;
	}
	public String getGroupCode(){
		return groupCode;
	}
	public String getGroupName(){
		return groupName;
	}
	
	public ColorSet getGroupStyle(){
		return ColorSet.getSetFromIndex(groupStyle);			
	}
	
	/* Setters */
	
	public void setGroupStyle(int groupStyle){
		this.groupStyle = groupStyle;
	}
	public void setGroupId(long id){
		this.id = id;
	}
	public void setGroupCode(String groupCode){
		this.groupCode = groupCode;
	}
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}


}
