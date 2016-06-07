package com.yongk.listviewfilter.indexbar;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.yongk.listviewfilter.R;
import com.yongk.listviewfilter.indexbar.listfiler.BarInedexDeal;
import com.yongk.listviewfilter.indexbar.listfiler.DataDealResults;
import com.yongk.listviewfilter.indexbar.listfiler.FilterBackResults;
import com.yongk.listviewfilter.indexbar.listfiler.PinnedHeaderAdapter;
import com.yongk.listviewfilter.indexbar.view.IndexBarView;
import com.yongk.listviewfilter.indexbar.view.PinnedHeaderListView;

public abstract class IndexBarHelper<T> extends PinnedConfig<T>{

	// array list to store section positions
	ArrayList<Integer> mListSectionPos;

	// array list to store listView data
	ArrayList<T> mListItems;

	PinnedHeaderListView mListView;
	// custom adapter
	PinnedHeaderAdapter<T> mAdaptor;
	IndexBarView indexBarView;
	
	private DataDealResults<T> mDataDealResults;
	
	public IndexBarHelper() {
		mListSectionPos = new ArrayList<Integer>();
		mListItems = new ArrayList<T>();
	}

	public ArrayList<Integer> getListSectionPos() {
		return mListSectionPos;
	}

	public ArrayList<T> getListItems() {
		return mListItems;
	}

	/** 
	 * 实例化
	 * @param mListView
	 * @param adapter
	 * @param filterBackResults  使用信息检索就要实例这个参数
	 */
	public void initListAdapter(PinnedHeaderListView mListView, PinnedHeaderAdapter<T> adapter, FilterBackResults<T> filterBackResults){
		this.mListView=mListView;
		
		// create instance of PinnedHeaderAdapter and set adapter to list view
		mAdaptor = adapter;
		// create instance of PinnedHeaderAdapter and set adapter to list view
		mAdaptor.init(mListItems, mListSectionPos);
		if(filterBackResults!=null)mAdaptor.setFilterBackResults(filterBackResults);
		mListView.setAdapter(mAdaptor);
		
//		mListView.postDelayed(new Runnable() {
//			public void run() {
//				postView();
//			}
//		}, 200);
		postView();
	}
	
	private void postView(){
		LayoutInflater inflater = (LayoutInflater) mListView.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		// set index bar view
		indexBarView = (IndexBarView) inflater.inflate(R.layout.view_list_index_bar, mListView, false);
		mListView.setIndexBarView(indexBarView);
		
		// set preview text view
		View previewTextView = inflater.inflate(R.layout.view_preview, mListView, false);
		mListView.setPreviewView(previewTextView);
	}
	
	/**<pre>
	 * 数据检索
	 * (这个用的要求是，必须实例方法{@linkplain initListAdapter}中的{@link FilterBackResults}参数)
	 * @param constraint
	 */
	public void filter(CharSequence constraint){
		if (mAdaptor != null && constraint != null)
			mAdaptor.getFilter().filter(constraint);
	}
	
	public void setListAdaptor() {
		mAdaptor.notifyDataSetChanged();
		
		indexBarView.setData(mListView, mListItems, mListSectionPos);
		indexBarView.setPinnedListener(this);
	}
	
	/**
	 * 处理完后的数据
	 * @param listSectionPos 组数据的索引
	 * @param listItems 数据的结果数据
	 */
	public void onPostExecute(ArrayList<Integer> listSectionPos,
			ArrayList<T> listItems) {
		if(listItems!=null){
			setList(listSectionPos, listItems);
		}
		if(mDataDealResults!=null){
			mDataDealResults.onPostExecute(listSectionPos, listItems);
		}
	}

	
	public void setList(ArrayList<Integer> listSectionPos, ArrayList<T> listItems){
		if(listSectionPos==null ||  listItems==null)return;
		
		mListItems.clear();
		mListSectionPos.clear();
	
		mListSectionPos.addAll(listSectionPos);
		mListItems.addAll(listItems);
		setListAdaptor();
	}
	
	/** <pre>
	 * 添加数据
	 * (这个使用要求是数据排序过后再调用)
	 * @param filtered  
	 */
	@SuppressWarnings("unchecked")
	public void poplulateDeal(ArrayList<T> filtered){
		new BarInedexDeal<T>(this).execute(filtered);
	}

	public DataDealResults<T> getDataDealResults() {
		return mDataDealResults;
	}

	/** 
	 * 监听数据处理结果
	 * @param dataDealResults  
	 */
	public void setDataDealResults(DataDealResults<T> dataDealResults) {
		this.mDataDealResults = dataDealResults;
	}
	
}
