// @author Bhavya Mehta
package com.yongk.listviewfilter.indexbar.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yongk.listviewfilter.R;
import com.yongk.listviewfilter.indexbar.listfiler.IIndexBarFilter;
import com.yongk.listviewfilter.indexbar.listfiler.PinnedListener;

// Represents right side index bar view with unique first latter of list view row text 
public class IndexBarView extends View {

    // index bar margin
    float mIndexbarMargin;

    // user touched Y axis coordinate value
    float mSideIndexY;

    // flag used in touch events manipulations
    boolean mIsIndexing = false;

    // holds current section position selected by user
    int mCurrentSectionPosition = -1;

    // array list to store section positions
    public ArrayList<Integer> mListSections;

    // array list to store listView data
    ArrayList<?> mListItems;

    // paint object
    Paint mIndexPaint;

    // context object
    Context mContext;

    // interface object used as bridge between list view and index bar view for
    // filtering list view content on touch event
    IIndexBarFilter mIndexBarFilter;

	@SuppressWarnings("rawtypes")
	PinnedListener mPinnedListener;
	
    public IndexBarView(Context context) {
        super(context);
        layoutInit();
    }

    public IndexBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutInit();
    }
    

    public IndexBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        layoutInit();
    }
    
    private void layoutInit(){
    	this.mContext = getContext();
    }

	/**
	 * 初始化
	 * @param mListView PinnedHeaderListView
	 * @param adapter PinnedHeaderAdapter
	 * @param filterBackResults FilterBackResults
	 */
    public void setData(PinnedHeaderListView listView, ArrayList<?> listItems,ArrayList<Integer> listSections) {
        this.mListItems = listItems;
        this.mListSections = listSections;
        
        // list view implements mIndexBarFilter interface
        mIndexBarFilter = listView;

        // set index bar margin from resources
        mIndexbarMargin = mContext.getResources().getDimension(R.dimen.index_bar_view_margin);

        // index bar item color and text size
        mIndexPaint = new Paint();
        mIndexPaint.setColor(Color.parseColor("#ff000000"));
        mIndexPaint.setAntiAlias(true);
        mIndexPaint.setTextSize(mContext.getResources().getDimension(R.dimen.index_bar_view_text_size));
    }
    
	public <T> void setPinnedListener(PinnedListener<T> pinnedListener){
		mPinnedListener=pinnedListener;
	}
    
    private float padd;
    // draw view content on canvas using paint
    @Override
    protected void onDraw(Canvas canvas) {
        if (mListSections != null && mListSections.size() > 1) {
            float sectionHeight = (getMeasuredHeight() - 2 * mIndexbarMargin)/ mListSections.size();
            float paddingTop = (sectionHeight - (mIndexPaint.descent() - mIndexPaint.ascent())) / 2;

            if(padd==0)padd= (mIndexbarMargin + paddingTop + mIndexPaint.descent())/2;
            for (int i = 0; i < mListSections.size(); i++) {
                float paddingLeft = (getMeasuredWidth() - mIndexPaint.measureText(getSectionText(mListSections.get(i)))) / 2;

                canvas.drawText(getSectionText(mListSections.get(i)),
                        paddingLeft,
                        mIndexbarMargin + (sectionHeight * i) + paddingTop + mIndexPaint.descent(),
                        mIndexPaint);
            }
            
        }
        super.onDraw(canvas);
    }

    @SuppressWarnings("unchecked")
	public String getSectionText(int sectionPosition) {
        return mPinnedListener.titleNamePacket(mListItems.get(sectionPosition));
    }

    boolean contains(float x, float y) {
        // Determine if the point is in index bar region, which includes the
        // right margin of the bar
        return (x >= getLeft() && y >= getTop() && y <= getTop() + getMeasuredHeight());
    }

    
    void filterListItem(float sideIndexY) {
        mSideIndexY = sideIndexY;

        // filter list items and get touched section position with in index bar
        mCurrentSectionPosition = (int) (((mSideIndexY) - padd) /
                                    ((getMeasuredHeight() - (2 * mIndexbarMargin)) / mListSections.size()));

        if (mCurrentSectionPosition >= 0 && mCurrentSectionPosition < mListSections.size()) {
            int position = mListSections.get(mCurrentSectionPosition);
            String previewText = getSectionText(position);
            mIndexBarFilter.filterList(mSideIndexY, position, previewText);
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            
            case MotionEvent.ACTION_DOWN:
                // If down event occurs inside index bar region, start indexing
                if (contains(ev.getX(), ev.getY())) {
                    // It demonstrates that the motion event started from index
                    // bar
                    mIsIndexing = true;
                    // Determine which section the point is in, and move the
                    // list to
                    // that section
                    filterListItem(ev.getY());
                    return true;
                }
                else {
                    mCurrentSectionPosition = -1;
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if (mIsIndexing) {
                    // If this event moves inside index bar
                    if (contains(ev.getX(), ev.getY())) {
                        // Determine which section the point is in, and move the
                        // list to that section
                        filterListItem(ev.getY());
                        return true;
                    }
                    else {
                        mCurrentSectionPosition = -1;
                        return false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsIndexing) {
                    mIsIndexing = false;
                    mCurrentSectionPosition = -1;
                }
                break;
        }
        return false;
    }
}
