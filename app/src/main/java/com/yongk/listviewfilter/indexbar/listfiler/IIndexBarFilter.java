// @author Bhavya Mehta
package com.yongk.listviewfilter.indexbar.listfiler;

// Gives index bar view touched Y axis value, position of section and preview text value to list view 
/** 
* 
* @ClassName: IIndexBarFilter 
* @author fuyongkang
* @date 2016-6-6 下午3:26:34 
*/
public interface IIndexBarFilter {
	void filterList(float sideIndexY,int position,String previewText);
}
