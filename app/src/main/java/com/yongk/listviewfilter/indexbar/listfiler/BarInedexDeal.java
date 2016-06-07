package com.yongk.listviewfilter.indexbar.listfiler;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.yongk.listviewfilter.indexbar.PinnedConfig;


// sort array and extract sections in background Thread here we use
// AsyncTask

/** 
* 
* @ClassName: BarInedexDeal 
* @author fuyongkang
* @date 2016-6-6 下午3:26:02 
*/
public class BarInedexDeal<T> extends AsyncTask<ArrayList<T>, Void, Void> {

	// array list to store section positions
	ArrayList<Integer> mListSectionPos;

	// array list to store listView data
	ArrayList<T> mListItems;
	
	PinnedConfig<T> mPinnedListener;
	
	public BarInedexDeal(PinnedConfig<T> pinnedListener){
		mPinnedListener=pinnedListener;
	}
	
	@Override
	protected void onPreExecute() {

		mListSectionPos = new ArrayList<Integer>();
		mListItems = new ArrayList<T>();
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(ArrayList<T>... params) {
		mListItems.clear();
		mListSectionPos.clear();
		ArrayList<T> items = params[0];
		if (items.size() > 0) {
	
			char prev_section = 0;
			for (T current_item : items) {
				String current_srt = titleName(current_item);
				char current_section = PingYinUtil.getFirstLetter(current_srt);
	
				if (prev_section != current_section) {
					T temp = newT(current_section, current_item);
					mListItems.add(temp);
					mListItems.add(current_item);
					// array list of section positions
					mListSectionPos.add(mListItems.indexOf(temp));//��ǰcurrent_section ��  mListItems�е�һ����ͬ��λ��
					prev_section = current_section;
				} else {
					mListItems.add(current_item);
				}
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (!isCancelled()) {
			if(mListItems.size() > 0){
				mPinnedListener.onPostExecute(mListSectionPos, mListItems);
				return;
			}
		}
		mPinnedListener.onPostExecute(null, null);
	}

	/**
	 * 使用的排序字段内容
	 * @param t
	 * @return
	 */
	protected String titleName(T t){
		return mPinnedListener.titleNamePacket(t);
	}

	/**
	 * list所在组的头数据
	 * @param section 
	 * @return
	 */
	protected T newT(char section, T t){
		return mPinnedListener.newTitle(section, t);
	}
	
}