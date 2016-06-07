package com.yongk.listviewfilter;

import java.util.ArrayList;
import java.util.Comparator;

import com.yongk.listviewfilter.indexbar.IndexBarHelper;
import com.yongk.listviewfilter.indexbar.PoplulateDeal;
import com.yongk.listviewfilter.indexbar.listfiler.PingYinUtil;

/** 
* 
* @ClassName: BrandBarHelper 
* @author fuyongkang
* @date 2016-6-6 下午3:25:26 
*/
public class BrandBarHelper extends IndexBarHelper<String>{

	
	public void poplulateDeal(ArrayList<String> filtered){
//		super.poplulateDeal(filtered);
		new PoplulateDeal<String>(this, new SortIgnoreCase()).execute(filtered);
	}

	public String titleNamePacket(String t) {
		return t;
//		return (t.getBrandEn()==null || t.getBrandEn().equals(""))? t.getBrandName():t.getBrandEn();
	}

	public String newTitle(char section, String t) {
		return new String(section+"");
	}
	
	public static class SortIgnoreCase implements Comparator<String> {
		@Override
		public int compare(String lhs, String rhs) {
			String s1=PingYinUtil.getFirstLetter(lhs)+"";
			String s2=PingYinUtil.getFirstLetter(rhs)+"";
			
			return s1.compareToIgnoreCase(s2);
		}
	}
}
