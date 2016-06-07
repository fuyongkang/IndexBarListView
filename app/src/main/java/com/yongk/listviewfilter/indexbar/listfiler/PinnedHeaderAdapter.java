// @author Bhavya Mehta
package com.yongk.listviewfilter.indexbar.listfiler;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.yongk.listviewfilter.indexbar.view.PinnedSectionListView.PinnedSectionListAdapter;

// Customized adaptor to populate data in PinnedHeaderListView
/**
 * @param <T>
 */
public abstract class PinnedHeaderAdapter<T> extends BaseAdapter implements  Filterable, PinnedSectionListAdapter {

	protected static final int TYPE_CONTENT = 0;
	protected static final int TYPE_HEADER = 1;
	private static final int TYPE_MAX_COUNT = TYPE_HEADER + 1;

	private LayoutInflater mLayoutInflater;

	// array list to store section positions
	protected ArrayList<Integer> mListSectionPos;

	// array list to store list view data
	protected ArrayList<T> mListItems;

	// context object
	protected Context mContext;

	public PinnedHeaderAdapter(Context context) {
		this.mContext = context;

		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void init( ArrayList<T> listItems, ArrayList<Integer> listSectionPos){
		this.mListItems = listItems;
		this.mListSectionPos=listSectionPos;
	}
	
	public LayoutInflater getLayoutInflater(){
		return mLayoutInflater;
	}
	
	@Override
	public int getCount() {
		return mListItems==null?0:mListItems.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return !mListSectionPos.contains(position);
	}

	@Override
	public int getViewTypeCount() {
		return TYPE_MAX_COUNT;
	}

	@Override
	public int getItemViewType(int position) {
		return mListSectionPos.contains(position) ? TYPE_HEADER : TYPE_CONTENT;
	}

	@Override
	public T getItem(int position) {
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mListItems.get(position).hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		
		return getView(position, convertView, type);
	}

	public boolean isItemViewTypePinned(int viewType) {
		return viewType == TYPE_HEADER;
	}

	/**
	 * getView逻辑
	 * @param position
	 * @param convertView
	 * @param type 类型，是头还是内容
	 * @return
	 */
	public abstract View getView(int position, View convertView, int type) ;

	private PinnedFilter<T> mPinnedFilter;
	private FilterBackResults<T> mFilterBackResults;
	public void setFilterBackResults(FilterBackResults<T> filterBackResults){
		this.mFilterBackResults=filterBackResults;
	}
	public Filter getFilter() {
		if(mPinnedFilter==null){
			mPinnedFilter=new PinnedFilter<T>(mFilterBackResults);
		}
		return mPinnedFilter;
	}

}
