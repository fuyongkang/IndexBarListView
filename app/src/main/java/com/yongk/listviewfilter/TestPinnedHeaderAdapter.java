// @author Bhavya Mehta
package com.yongk.listviewfilter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yongk.listviewfilter.indexbar.listfiler.PinnedHeaderAdapter;

// Customized adaptor to populate data in PinnedHeaderListView
public class TestPinnedHeaderAdapter extends PinnedHeaderAdapter<String> {

	public TestPinnedHeaderAdapter(Context context) {
		super(context);
	}
	
	public static class ViewHolder {
		public TextView textView;
	}

	@Override
	public View getView(int position, View convertView, int type) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();

			switch (type) {
			case TYPE_CONTENT:
				convertView = getLayoutInflater().inflate(R.layout.row_view, null);
				break;
			case TYPE_HEADER:
				convertView = getLayoutInflater().inflate(R.layout.section_row_view, null);
				break;
			}
			holder.textView = (TextView) convertView.findViewById(R.id.row_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textView.setText(mListItems.get(position).toString());
		return convertView;
	}

}
