package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import containers.Day;

import models.Group;
import models.Task;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * This class handles all direct contact with the DatabaseHelper and handles all CRUD requests
 * coming from different parts of the application.
 * @author Erica
 *
 */
public class DataSource implements Serializable {

	private static final long serialVersionUID = 1L;
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private Context context;
	
	public static DataSource dsInstance;
	
	public static void initDataSource(Context context){
		dsInstance = new DataSource(context);
	}
	
	public DataSource(Context context) {
		this.context = context;
	    dbHelper = new DatabaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
	    dbHelper.close();
	}
	
	/* TASKS CRUD */
	
	/**
	 * Inserts a single task into the database
	 * @param task the task to be added.
	 * @return the Task that was inserted with it's id set to the pk of the inserted row.
	 */
	public Task insertTask(Task task){
		
		try{
			open();
			
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.COL_TASK_DESC, task.getTaskDesc());
			cv.put(DatabaseHelper.COL_TASK_DATE, task.getTaskDate());
			cv.put(DatabaseHelper.COL_TASK_GROUP_ID, task.getGroupId());
			cv.put(DatabaseHelper.COL_TASK_TIME, task.getTaskTime());
			
			task.setId(database.insert(DatabaseHelper.TABLE_TASK, null, cv));
		}
		catch(Exception e){
			// handle exception <- tell user the task was not inserted
			// pop up a toast perhaps?
		}
		finally{
			close();
		}
		
		return task;
	}
	
	/**
	 * Updates the passes task in the database
	 * @param task the Task to be updated.
	 */
	public void updateTask(Task task){
		try{
			open();
			//System.out.println("UpdateTask -> TaskGroupId : " + task.getGroupId());
			
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.COL_TASK_DESC, task.getTaskDesc());
			cv.put(DatabaseHelper.COL_TASK_DATE, task.getTaskDate());
			cv.put(DatabaseHelper.COL_TASK_GROUP_ID, task.getGroupId());
			cv.put(DatabaseHelper.COL_TASK_TIME, task.getTaskTime());
			
			database.update(DatabaseHelper.TABLE_TASK, cv, 
					DatabaseHelper.COL_TASK_ID + " = ?", 
					new String []{String.valueOf(task.getId())});
		
			// System.out.println("Updating database: " + "Task ID = " + task.getId() + "Description Value: " + task.getGroupId());
		}
		catch(Exception e){
			// handle exception <- tell user the task could not be updated
			// pop up a toast perhaps?
		}
		finally{
			close();
		}
	}
	
	/**
	 * Removes a single task from the database.
	 * @param task the task to be removed
	 * @return true if the process was successful and false if it was not.
	 */
	public boolean removeTask(Task task){
		boolean operationOutcome = false;
		try{
			open();
			String table_name = DatabaseHelper.TABLE_TASK;
			String where = DatabaseHelper.COL_TASK_ID + " = ?";
			String[] whereArgs = {String.valueOf(task.getId())};
			database.delete(table_name, where, whereArgs);
			operationOutcome = true;
		}
		catch(Exception e){
			// handle exception <- tell user the task could not be removed
			// pop up a toast perhaps?
		}
		finally{
			close();
		}
		return operationOutcome;
	}
	
	/**
	 * Retrieves the list of tasks assosiated with the passed date value.
	 * @param dataDate the date for which the tasks are requested.
	 * @return the list of tasks assosiated with the passed date
	 */
	public List<Task> getDayTasks(String dataDate){
		
		List<Task> taskList =  new ArrayList<Task>();
		Cursor cursor = null;

		try{
			open();
			cursor = database.query(DatabaseHelper.TABLE_TASK, 
						new String[]{DatabaseHelper.COL_TASK_ID, 
									DatabaseHelper.COL_TASK_GROUP_ID,
									DatabaseHelper.COL_TASK_DATE,
									DatabaseHelper.COL_TASK_TIME,
									DatabaseHelper.COL_TASK_DESC}, 
						DatabaseHelper.COL_TASK_DATE + " = ?",
						new String[] {dataDate},
						null, null, null);
			
			int idColIndex = cursor.getColumnIndex(DatabaseHelper.COL_TASK_ID);
			int groupIdColIndex = cursor.getColumnIndex(DatabaseHelper.COL_TASK_GROUP_ID);
			int taskDateColIndex = cursor.getColumnIndex(DatabaseHelper.COL_TASK_DATE);
			int taskTimeColIndex = cursor.getColumnIndex(DatabaseHelper.COL_TASK_TIME);
			int taskDescColIndex = cursor.getColumnIndex(DatabaseHelper.COL_TASK_DESC);
			
			while (cursor.moveToNext()){
				Task task = new Task(dataDate);
				
				task.setId(cursor.getLong(idColIndex));
				task.setGroupId(cursor.getLong(groupIdColIndex));
				task.setTaskDate(cursor.getString(taskDateColIndex));
				task.setTaskTime(cursor.getString(taskTimeColIndex));
				task.setTaskDesc(cursor.getString(taskDescColIndex));
				
				taskList.add(task);
			}
		}
		catch(Exception e){
			// handle exception <- tell user the task list could not be found
			// pop up a toast perhaps?
		}
		finally{
			cursor.close();
			close();
		}

		return taskList;
	}
	
	/* GROUPS CRUD */

	/**
	 * Inserts a single group into the database.
	 * 
	 * @param group the new group to be inserted into the database
	 * @return The passed group with the id set to the pk of the new row int he database.
	 */
	public Group insertGroup(Group group){

		try{
			open();
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.COL_GROUP_CODE, group.getGroupCode());
			cv.put(DatabaseHelper.COL_GROUP_NAME, group.getGroupName());
			cv.put(DatabaseHelper.COL_GROUP_STYLE, group.getGroupStyle().getIndex());
			
			group.setGroupId(database.insert(DatabaseHelper.TABLE_GROUP, null, cv));
		}
		catch(Exception e){
			// handle exception <- tell user the group could not be added
			// pop up a toast perhaps?
		}
		finally{
			close();
		}

		return group;
	}
	
	/**
	 * Updates a single existing group in the database
	 * @param group the group to be updated
	 */
	public void updateGroup(Group group){
		
		try {
			open();
			ContentValues cv = new ContentValues();
			cv.put(DatabaseHelper.COL_GROUP_CODE, group.getGroupCode());
			cv.put(DatabaseHelper.COL_GROUP_NAME, group.getGroupName());
			cv.put(DatabaseHelper.COL_GROUP_STYLE, group.getGroupStyle().getIndex());
			
			database.update(DatabaseHelper.TABLE_GROUP, cv, 
					DatabaseHelper.COL_GROUP_ID + " = ?", 
					new String []{String.valueOf(group.getGroupId())});
		}
		catch(Exception e){
			// handle exception <- tell user the group could not be updated
			// pop up a toast perhaps?
		}
		finally{
			close();
		}
	}
	
	/**
	 * Retrieves the group assosiated with the passed task
	 * @param task the task for which the group is requested.
	 * @return the Group assosiated with the task.
	 */
	public Group getGroupByTask(Task task){
		
		System.out.println("GroupByTask -> TaskGroupId : " + task.getGroupId());
		Cursor cursor = null;
		Group group = new Group();
		
		try{
			open();
			cursor = database.query(DatabaseHelper.TABLE_GROUP, 
						new String[]{DatabaseHelper.COL_GROUP_CODE,
									DatabaseHelper.COL_GROUP_NAME,
									DatabaseHelper.COL_GROUP_STYLE}, 
						DatabaseHelper.COL_GROUP_ID + " = ?", 
						new String[]{String.valueOf(task.getGroupId())}, 
						null, null, null);
	
			int codeColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_CODE);
			int nameColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_NAME);
			int styleColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_STYLE);
			
			cursor.moveToFirst();
			
			group.setGroupCode(cursor.getString(codeColIndex));
			group.setGroupName(cursor.getString(nameColIndex));
			group.setGroupStyle(cursor.getInt(styleColIndex));
	
			System.out.println("GroupCode : " + group.getGroupCode());
		}
		catch(Exception e){
			// handle exception <- tell user the group could not be found
			// pop up a toast perhaps?
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			close();
		}
		
		return group;
	}
	
	/**
	 * Retrieves all the groups curently defined in the groups table.
	 * @return the list of all groups.
	 */
	public List<Group> getGroups(){
		
		List<Group> groupList =  new ArrayList<Group>();
		Cursor cursor = null;

		try{
			open();
			cursor = database.query(DatabaseHelper.TABLE_GROUP, 
						new String[]{DatabaseHelper.COL_GROUP_ID, 
									DatabaseHelper.COL_GROUP_CODE,
									DatabaseHelper.COL_GROUP_NAME,
									DatabaseHelper.COL_GROUP_STYLE}, 
						null, null, null, null, null);
			
			int idColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_ID);
			int codeColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_CODE);
			int nameColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_NAME);
			int styleColIndex = cursor.getColumnIndex(DatabaseHelper.COL_GROUP_STYLE);
			
			while (cursor.moveToNext()){
				Group group = new Group();
				
				group.setGroupId(cursor.getLong(idColIndex));
				group.setGroupCode(cursor.getString(codeColIndex));
				group.setGroupName(cursor.getString(nameColIndex));
				group.setGroupStyle(cursor.getInt(styleColIndex));
				
				System.out.println(group.getGroupCode());
				
				groupList.add(group);
			}
			
			//System.out.println("Group query length: " + groupList.size());
		}
		catch(Exception e){
			// handle exception <- tell user the groups could not be retrieved
			// pop up a toast perhaps?
		}
		finally{
			if(cursor != null){
				cursor.close();
			}
			close();
		}
		
		return groupList;
	}
	
	public boolean removeGroup(Group group){
		boolean operationOutcome = false;
		try{
			open();
			String table_name = DatabaseHelper.TABLE_GROUP;
			String where = DatabaseHelper.COL_GROUP_ID + " = ?";
			String[] whereArgs = {String.valueOf(group.getGroupId())};
			database.delete(table_name, where, whereArgs);
			operationOutcome = true;
		}
		catch(Exception e){
			// handle exception <- tell user the group could not be removed
			// pop up a toast perhaps?
		}
		finally{
			close();
		}
		return operationOutcome;
	}
}
