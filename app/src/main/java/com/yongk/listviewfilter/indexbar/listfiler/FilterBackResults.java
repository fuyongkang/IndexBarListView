package com.yongk.listviewfilter.indexbar.listfiler;

import java.util.ArrayList;

/** 
* 
* @ClassName: FilterBackResults 
* @author fuyongkang
*/
public interface FilterBackResults<T> {
	/**
	 * 原数据，匹配遍历前的数据
	 * @return
	 */
	public ArrayList<T> itemsFilter();

	
	/**<Pre>
	 * 匹配条件
	 * 输入的内容和t中的对比，是否匹配相同
	 * @param t 当前数据
	 * @param constraintStr 输入的内容
	 * @return
	 */
	public boolean seachFilter(T t, String constraintStr);

	/**
	 * 匹配结果
	 * @param constraint 输入内容
	 * @param filtered 结果
	 */
	public void publishResults(String constraint, ArrayList<T> filtered);
}
