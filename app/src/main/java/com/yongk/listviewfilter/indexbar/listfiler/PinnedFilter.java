package com.yongk.listviewfilter.indexbar.listfiler;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.Filter;

/** 
* 
* @ClassName: PinnedFilter 
* @author fuyongkang
* @date 2016-6-6 下午3:27:20 
*/
public class PinnedFilter<T> extends Filter {

	private FilterBackResults<T> mFilterResults;
	
	public PinnedFilter(FilterBackResults<T> filterResults) {
		mFilterResults = filterResults;
	}

	protected FilterResults performFiltering(CharSequence constraint) {
		// NOTE: this function is *always* called from a background thread,
		// and
		// not the UI thread.
		FilterResults result = new FilterResults();
		
		ArrayList<T> mItems=(ArrayList<T>) mFilterResults.itemsFilter();
		if(mItems==null)return null;

		if (constraint != null && constraint.toString().length() > 0 &&  mItems.size()>0) {
			
			String constraintStr = constraint.toString().toLowerCase(
					Locale.getDefault());
			
			ArrayList<T> filterItems = new ArrayList<T>();

			synchronized (this) {
				for (T item : mItems) {
					if (startsWith(item, constraintStr)) {
						filterItems.add(item);
					}
				}
				result.count = filterItems.size();
				result.values = filterItems;
			}
		} else {
			synchronized (this) {
				result.count = mItems.size();
				result.values = mItems;
			}
		}
		return result;
	}

	/**<pre>
	 * 输入的内容和t中的对比，是否匹配相同
	 * (这个是根据不同的匹配要求进行修改)
	 * @param t
	 * @param constraintStr 输入的内容
	 * @return
	 */
	private boolean startsWith(T t, String constraintStr){
		return mFilterResults.seachFilter(t, constraintStr);
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		if(results!=null){
			@SuppressWarnings("unchecked")
			ArrayList<T> filtered = (ArrayList<T>) results.values;
			mFilterResults.publishResults(constraint.toString(), filtered);
		}else{
			mFilterResults.publishResults(constraint.toString(), new ArrayList<T>());
		}
		
	}

}
