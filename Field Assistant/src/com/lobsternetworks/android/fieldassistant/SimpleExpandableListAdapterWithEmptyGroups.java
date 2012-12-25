package com.lobsternetworks.android.fieldassistant;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;

public class SimpleExpandableListAdapterWithEmptyGroups extends
		SimpleExpandableListAdapter {

	private static final int[] EMPTY_STATE_SET = {};
    private static final int[] GROUP_EXPANDED_STATE_SET =
            {android.R.attr.state_expanded};
    private static final int[][] GROUP_STATE_SETS = {
         EMPTY_STATE_SET, // 0
         GROUP_EXPANDED_STATE_SET // 1
	};

	public SimpleExpandableListAdapterWithEmptyGroups(Context context,
			List<? extends Map<String, ?>> groupData, int groupLayout,
			String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, groupLayout, groupFrom, groupTo, childData,
				childLayout, childFrom, childTo);
	}

	public SimpleExpandableListAdapterWithEmptyGroups(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, String[] childFrom, int[] childTo) {
		super(context, groupData, expandedGroupLayout, collapsedGroupLayout,
				groupFrom, groupTo, childData, childLayout, childFrom, childTo);
	}

	public SimpleExpandableListAdapterWithEmptyGroups(Context context,
			List<? extends Map<String, ?>> groupData, int expandedGroupLayout,
			int collapsedGroupLayout, String[] groupFrom, int[] groupTo,
			List<? extends List<? extends Map<String, ?>>> childData,
			int childLayout, int lastChildLayout, String[] childFrom,
			int[] childTo) {
		super(context, groupData, expandedGroupLayout, collapsedGroupLayout,
				groupFrom, groupTo, childData, childLayout, lastChildLayout,
				childFrom, childTo);
	}

	public View getGroupView (int groupPosition, 
			boolean isExpanded, 
			View convertView, 
			ViewGroup parent) {
		View v = super.getGroupView( groupPosition, isExpanded, convertView, parent);
		
		View ind = v.findViewById( R.id.explist_indicator);
		if( ind != null ) {
			ImageView indicator = (ImageView)ind;
			if( getChildrenCount( groupPosition ) == 0 ) {
				indicator.setVisibility( View.INVISIBLE );
			} else {
				indicator.setVisibility( View.VISIBLE );
				int stateSetIndex = ( isExpanded ? 1 : 0) ;
				Drawable drawable = indicator.getDrawable();
				drawable.setState(GROUP_STATE_SETS[stateSetIndex]);
			}
		}
		return v;
	}

	private void dumpViewGroup( View v ) {
		if( v instanceof ViewGroup ) {
			ViewGroup vg = (ViewGroup)v;
			for( int i = 0 ; i < vg.getChildCount() ; ++i ) {
				View child = vg.getChildAt( i );
				Log.d( LOG_TAG, "dumpViewGroup: child["+i+"]: "+child );
			}
		} else
			Log.d(LOG_TAG, "dumpViewGroup: "+v+" is not a ViewGroup");
	}
	
	private static final String LOG_TAG = "EmptyGroupsAdapter";
}
