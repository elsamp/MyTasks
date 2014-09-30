package database;

import java.io.Serializable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class handles the creation of the database as well as the update for future versions.
 * Currenlty the update funcationality has not been implemented.
 * @author Erica
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//constants for database table and colum names.
	public static final String DATABASE_NAME = "tasks.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_GROUP = "Task_Group";
	public static final String COL_GROUP_ID = "_id";
	public static final String COL_GROUP_CODE = "group_code";
	public static final String COL_GROUP_NAME = "group_name";
	public static final String COL_GROUP_STYLE = "group_style";
	
	public static final String TABLE_TASK = "Task";
	public static final String COL_TASK_ID = "_id";
	public static final String COL_TASK_GROUP_ID = "group_id";
	public static final String COL_TASK_DATE = "task_date";
	public static final String COL_TASK_TIME = "task_time";
	public static final String COL_TASK_DESC = "task_desc";
	
	// constant sql string for creating the group table
	private static final String CREATE_GROUP_TABLE = "create table " + TABLE_GROUP + " (" +
			COL_GROUP_ID + " integer primary key autoincrement, " +
			COL_GROUP_CODE + " text, " +
			COL_GROUP_NAME + " text, " +
			COL_GROUP_STYLE + " integer" +
			");";
	
	// constant sql string for creating the task table
	private static final String CREATE_TASK_TABLE = "create table " + TABLE_TASK + " (" +
			COL_TASK_ID + " integer primary key autoincrement, " +
			COL_TASK_GROUP_ID + " integer, " +
			COL_TASK_DATE + " text, " +
			COL_TASK_TIME + " text, " +
			COL_TASK_DESC + " text " +
			");";


	public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	@Override
	/**
	 * creates the database tables
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_GROUP_TABLE);
		db.execSQL(CREATE_TASK_TABLE);
	}

	@Override
	/**
	 * Used only if table version changes and the table needs to be rebuilt. 
	 * Obviously this should not remain like this and should contain functionality for
	 * correctly handeling the transfer of data.
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS task");
		db.execSQL("DROP TABLE IF EXISTS group");
	    onCreate(db);
	}

}
