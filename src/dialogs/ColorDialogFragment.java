package dialogs;

import java.util.ArrayList;
import java.util.List;

import values.ColorSet;

import models.Group;

import com.allBadCats.utils.mytasks.R;

import database.DataSource;

import adapters.ColorArrayAdapter;
import adapters.GroupArrayAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ColorDialogFragment extends DialogFragment {

	private NoticeDialogListener mListener;
	
	public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
	
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    
		// set up nessisary layout for dialog
	    LinearLayout layout = new LinearLayout(getActivity());
	    ListView listView = new ListView(getActivity());
	    ArrayList<ColorSet> colorList = new ArrayList<ColorSet>();
	    
	    for(ColorSet set : ColorSet.values()){
	    	colorList.add(set);
	    }

	    ArrayAdapter<ColorSet> adapter = new ColorArrayAdapter(getActivity(),R.layout.layout_color_dialog_item, colorList);
	    listView.setAdapter(adapter);
	    
	    layout.addView(listView);
	    
	    // set dialog attributes
	    builder.setTitle("Select a task group")
	    	.setView(layout)
        	.setPositiveButton("Done", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        			mListener.onDialogPositiveClick(ColorDialogFragment.this);
        		}
        	})
        	.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog, int id) {
        			mListener.onDialogNegativeClick(ColorDialogFragment.this);
        		}
        	});
        return builder.create();
    }
	

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }

}
