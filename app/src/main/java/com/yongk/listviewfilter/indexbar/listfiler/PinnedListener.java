package com.yongk.listviewfilter.indexbar.listfiler;

/**
 * 因为泛型的不同，所以通过这里备注就修改对应处理
 */
public interface PinnedListener<T> {

	/**
	 * 排序分组所使用的字段内容
	 * @param t
	 * @return
	 */
	public String titleNamePacket(T t);
	/**<Pre>
	 * list所在组的头要显示的数据
	 * (这个决定着adapter里的头信息显示什么)
	 * @param section  首字母
	 * @return
	 */
	public T newTitle(char section, T t);
}
