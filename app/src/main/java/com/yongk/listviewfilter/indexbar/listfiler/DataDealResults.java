package com.yongk.listviewfilter.indexbar.listfiler;

import java.util.ArrayList;

/** 
* 
* @ClassName: DataDealResults 
* @author fuyongkang
*/
public interface DataDealResults<T> {
	/**
	 * 处理完后的数据
	 * @param listSectionPos 组数据的索引
	 * @param listItems 数据的结果数据
	 */
	public  void onPostExecute(ArrayList<Integer> listSectionPos, ArrayList<T> listItems);
}
