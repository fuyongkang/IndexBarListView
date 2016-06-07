package com.yongk.listviewfilter.indexbar.listfiler;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PingYinUtil {

	/**
	 * 拼音汉字首字母
	 * @param inputString
	 * @return
	 */
	public static char getFirstLetter(String inputString) {
		char temp_ = '@';
		
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		char input = inputString.trim().toCharArray()[0];
		
		if(input>='A' && input<='Z' ){
			return input;
		}else if(input>='a' && input<='z'){
			return (char) (input-32);
		}else{
			try {
				String[] s = PinyinHelper.toHanyuPinyinStringArray(
						input, format);
				if (s == null) {
					return temp_;
				} else {
					return s[0].toUpperCase().charAt(0);
				}
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} 
		return temp_;
	}

}