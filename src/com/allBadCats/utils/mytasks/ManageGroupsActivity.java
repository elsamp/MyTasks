package com.allBadCats.utils.mytasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import values.ColorSet;
import values.GroupIcon;
import values.GroupIcon.IconState;

import containers.Day;
import database.DataSource;
import dialogs.ColorDialogFragment;
import dialogs.GroupDialogFragment;

import layouts.RelativeColorItemLayout;
import layouts.RelativeGroupItemLayout;
import layouts.RelativeGroupLayout;
import layouts.RelativeTaskLayout;
import models.Group;
import models.Task;


import adapters.GroupArrayAdapter;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Activity enabling the creating, editing and removing of groups from those avalible
 * withing the WeekViewMain group selection dialoge.
 * @author Erica
 *
 */
public class ManageGroupsActivity extends FragmentActivity implements Serializable, ColorDialogFragment.NoticeDialogListener{
	
	private LinearLayout ll;
	private LayoutInflater inflater;
	private List<Group> groupList = new ArrayList<Group>();
	private List<RelativeGroupLayout> groupLayouts = new ArrayList<RelativeGroupLayout>();
	
	private Group activeGroup;
	private Group editGroup;
	private RelativeGroupLayout activeGroupView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_groups);
		arrangeLayout();
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage_groups_menu, menu);
		return true;
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		saveGroups();
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		if(editGroup != null){
			activeGroup.setGroupStyle(editGroup.getGroupStyle().getIndex());
		}

		activeGroupView.setGroupFields(activeGroup);
		DataSource.dsInstance.updateGroup(activeGroup);
		arrangeLayout();
		editGroup = null;
	}
	
	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		arrangeLayout();
		editGroup = null;
	}

	/**
	 * Inflates and arranges group display layout.
	 */
	public void arrangeLayout(){
		inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ll = (LinearLayout)findViewById(R.id.group_container);
		groupLayouts.clear();
		groupList = DataSource.dsInstance.getGroups();

		if(groupList.size() <= 0){
			Group group = new Group();
			group.setGroupCode("G");
			group.setGroupStyle(ColorSet.GREY.getIndex());
			group.setGroupName("Default Group");
			DataSource.dsInstance.insertGroup(group);
			groupList.add(group);
		}

		ll.removeAllViews();
		
		for(Group group : groupList){
			RelativeGroupLayout groupView = new RelativeGroupLayout(this);
			groupView = (RelativeGroupLayout)inflater.inflate(R.layout.layout_group_item,
						(ViewGroup)findViewById(R.layout.activity_manage_groups),false);
			groupView.setGroupFields(group);
			groupLayouts.add(groupView);
			ll.addView(groupView);
		}
	}
	
	/**
	 * Adds a group item to the groupList then calls for it to be redrawn.
	 * 
	 * @param view onClick view initiating the action.
	 */
	public void addGroup(View view){
		Group group = new Group();
		groupList.add(group);
		DataSource.dsInstance.insertGroup(group);
		arrangeLayout();
	}
	
	/**
	 * Saves each group and it's attributes into the database.
	 */
	private void saveGroups(){
		for(RelativeGroupLayout layout : groupLayouts){
			layout.updateGroupProperties();
			DataSource.dsInstance.updateGroup(layout.getGroup());
		}
	}
	
	/**
	 * Removes the group assosiated with the passed view from the database.
	 * @param view onClick view initiating the action.
	 */
	public void removeGroup(View view){
		RelativeGroupLayout groupView = (RelativeGroupLayout) view.getParent();
		Group group = groupView.getGroup();
		DataSource.dsInstance.removeGroup(group);
		arrangeLayout();
	}
	
	/**
	 * Shows a dialog displaying avalible group colour choices.
	 * @param view onClick view initiating the action.
	 */
	public void showDialog(View view) {
		
		FragmentManager fm = getSupportFragmentManager();
	    ColorDialogFragment colorSelection = new ColorDialogFragment();

	    activeGroupView = (RelativeGroupLayout)view.getParent();
	    
	    colorSelection.show(fm, "dialog");
	    activeGroup = activeGroupView.getGroup();

	}
	
	/**
	 * Sets the colour of the selected goup to that selected in the dialoge box.
	 * @param view onClick view initiating the action.
	 */
	public void setTaskGroup(View view){
		RelativeColorItemLayout groupView = (RelativeColorItemLayout) view;
		ImageView groupIconView = (ImageView)groupView.findViewById(R.id.color_icon);
		groupIconView.setBackground(new GroupIcon(groupView.getColorSet(), IconState.SELECTED));
	    
		editGroup = new Group(activeGroup);
	    editGroup.setGroupStyle(groupView.getColorSet().getIndex());
	}
	
}
