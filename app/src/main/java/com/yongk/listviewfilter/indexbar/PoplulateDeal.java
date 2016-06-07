package com.yongk.listviewfilter.indexbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.yongk.listviewfilter.indexbar.listfiler.BarInedexDeal;


// sort array and extract sections in background Thread here we use
// AsyncTask
/**
 * 排序
* 
* @ClassName: PoplulateDeal 
* @author fuyongkang
* @date 2016-6-6 下午3:24:35
 */
public class PoplulateDeal<T> extends BarInedexDeal<T> {

	private Comparator<T> mComparator;
	
	public PoplulateDeal(PinnedConfig<T> pinnedListener,
			Comparator<T> comparator) {
		super(pinnedListener);
		this.mComparator=comparator;
	}

	protected Void doInBackground(ArrayList<T>... params) {
		ArrayList<T> items = params[0];
		Collections.sort(items, mComparator);

		return super.doInBackground(params);
	}
	
}