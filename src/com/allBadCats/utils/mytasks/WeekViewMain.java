package com.allBadCats.utils.mytasks;

import java.io.Serializable;
import java.util.ArrayList;

import values.GroupIcon;
import values.GroupIcon.IconState;

import containers.Day;
import containers.WeekGroup;
import database.DataSource;
import dialogs.GroupDialogFragment;
import layouts.RelativeDayLayout;
import layouts.RelativeGroupItemLayout;
import layouts.RelativeTaskLayout;
import models.Task;


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle.Delegate;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Main application activity. Manages the display of all week, day, task information and allows
 * the user to create edit and remove tasks assosiated to a given day.
 * @author Erica
 *
 */
public class WeekViewMain extends FragmentActivity implements Serializable, GroupDialogFragment.NoticeDialogListener{

	private static final long serialVersionUID = 1L;
	private WeekGroup week  = new WeekGroup();
	private ArrayList<RelativeDayLayout> dayViews = new ArrayList<RelativeDayLayout>();
	private Task activeTask;
	private Task editTask = null;
	private RelativeTaskLayout activeTaskView;
	private DisplayMetrics metrics = new DisplayMetrics();
	public static Context CONTEXT;
	
	public static float LOGICAL_DESITY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataSource.initDataSource(this);
		if (savedInstanceState != null){
			week = (WeekGroup)savedInstanceState.getSerializable("savedWeek");
		}
		
		setContentView(R.layout.activity_week_view_main);
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		LOGICAL_DESITY = metrics.density;
	
		CONTEXT = this;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle weekGroup) {
		  super.onSaveInstanceState(weekGroup);
		  saveWeekData();
		  weekGroup.putSerializable("savedWeek", week);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		saveWeekData();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		arrangeWeekLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.week_view_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

	    switch (item.getItemId()) {
	    case R.id.action_manageGroups:
	    	startGroupActivity();
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(editTask != null){
			activeTask.setGroupId(editTask.getGroupId());
		}
		System.out.println("ActiveTask Group id : " + activeTask.getGroupId());
		activeTaskView.setTask(activeTask);
		DataSource.dsInstance.updateTask(activeTask);
		arrangeWeekLayout();
		editTask = null;
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		arrangeWeekLayout();
		editTask = null;
	}
	
	/**
	 * Inflates and sets values for the week view and it's assosiated tasks.
	 */
	public void arrangeWeekLayout(){
		dayViews.clear();
		LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewGroup parent = (ViewGroup)findViewById(R.id.activity_week_view_main);
		
		TextView title = (TextView)parent.findViewById(R.id.week_range);
		title.setText(week.getWeekRangeString());
		
		LinearLayout dayContainer = (LinearLayout) parent.findViewById(R.id.day_container);
		dayContainer.removeAllViews();
		
		for(Day day : week.getDayList()){
			day.setTaskList(buildDayTaskList(day));
			RelativeDayLayout view = new RelativeDayLayout(this);
			view = (RelativeDayLayout) inflater.inflate(R.layout.layout_day_group, parent, false);
			view.setDay(day);
			dayViews.add(view);
	        dayContainer.addView(view);
		}
	}
	
	/**
	 * Starts the ManageGroupsActivity. Typically called as a result of a actionbar menu selection.
	 */
	private void startGroupActivity(){
		Intent i = new Intent(getApplicationContext(), ManageGroupsActivity.class);
		startActivity(i);
	}
	
	/**
	 * Decremens the week by one and calls for layout to be redrawn.
	 * 
	 * @param view onClick view initiating the action.
	 */
	public void goToPreviousWeek(View view){
		saveWeekData();
		week.decrementWeek();
		arrangeWeekLayout();
		findViewById(R.id.activity_week_view_main).invalidate();
	}
	
	/**
	 * Increases the week by one and calls for layout to be redrawn.
	 * 
	 * @param view onClick view initiating the action.
	 */
	public void goToNextWeek(View view){
		saveWeekData();
		week.incrementWeek();
		arrangeWeekLayout();
		findViewById(R.id.activity_week_view_main).invalidate();
	}
	
	/**
	 * Iterates through each day and it's tasks and saves data only if a task description has been set. 
	 * This will need to be updated in the future for more flexibility. 
	 * 
	 * Should add functionality for marking fields as changed and then saving only what is needed.
	 */
	private void saveWeekData(){
		for(RelativeDayLayout view : dayViews){
			for(RelativeTaskLayout taskView : view.getTaskLayouts()){
				EditText taskDesc = (EditText)taskView.findViewById(R.id.task_descript);
				EditText taskTime = (EditText)taskView.findViewById(R.id.time_alloted);
				if(taskDesc != null){
					taskView.getTask().setTaskDesc(taskDesc.getText().toString());
					taskView.getTask().setTaskTime(taskTime.getText().toString());
					DataSource.dsInstance.updateTask(taskView.getTask());
				}
			}
		}
	}
	
	/**
	 * Adds a task to the parent day layout as well as it's assosiated day object. 
	 * Calls for the week layout to be redrawn, and adds task to the database.
	 * 
	 * @param view onClick view initiating the action.
	 */
	public void addTask(View view){
		RelativeDayLayout rl = (RelativeDayLayout) view.getParent();
		System.out.println("addingTask");
		Task task = new Task(rl.getDay().getDataDate());
		DataSource.dsInstance.insertTask(task);
		rl.getDay().addTask(task);
		arrangeWeekLayout();
	}
	
	/**
	 * Removes the parent task from it's parent day layout as well as it's assosiated day object. 
	 * Calls for the week layout to be redrawn and removes the task from the database.
	 * 
	 * @param view onClick view initiating the action.
	 */
	public void removeTask(View view){
		RelativeTaskLayout taskLayout = (RelativeTaskLayout) view.getParent();
		Task task = taskLayout.getTask();
		DataSource.dsInstance.removeTask(task);
		arrangeWeekLayout();
	}
	
	/**
	 * Retreives the list of tasks assosiated with the passed day.
	 * 
	 * @param day Day for which the task list will be retreived
	 * @return list of tasks assosiated with the passed day
	 */
	private ArrayList<Task> buildDayTaskList(Day day){
		
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList = (ArrayList<Task>)DataSource.dsInstance.getDayTasks(day.getDataDate());
		
		return taskList;
	}
	
	/**
	 * Displays dialog for selecting a group for the task
	 * @param view onClick view initiating the action.
	 */
	public void showDialog(View view) {
		
			FragmentManager fm = getSupportFragmentManager();
		    GroupDialogFragment groupSelection = new GroupDialogFragment();
	
		    activeTaskView = (RelativeTaskLayout)view.getParent();
		    
		    groupSelection.show(fm, "dialog");
		    activeTask = activeTaskView.getTask();

	}
	
	/**
	 * Sets task group to the selected group.
	 * @param view onClick view initiating the action.
	 */
	public void setTaskGroup(View view){
		RelativeGroupItemLayout groupView = (RelativeGroupItemLayout) view;
		ImageView groupIconView = (ImageView)groupView.findViewById(R.id.group_icon);
		groupIconView.setBackground(new GroupIcon(groupView.getGroup().getGroupStyle(), IconState.SELECTED));
	    
		editTask = new Task(activeTask);
	    editTask.setGroupId(groupView.getGroup().getGroupId());
	}

	
}
