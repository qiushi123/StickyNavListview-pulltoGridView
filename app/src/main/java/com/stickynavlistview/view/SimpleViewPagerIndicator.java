package com.stickynavlistview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stickynavlistview.listenter.ListenerConstans;

public class SimpleViewPagerIndicator extends LinearLayout
{

	private static final int COLOR_TEXT_NORMAL = 0xFF000000;
	private static final int COLOR_INDICATOR_COLOR = Color.BLACK;

	private String[] mTitles;
	private int mTabCount;
	private int mIndicatorColor = COLOR_INDICATOR_COLOR;
	private float mTranslationX;
	private Paint mPaint = new Paint();
	private int mTabWidth;
	private int mTabCutWidth=50;//如果觉得指示线太长，这里可以设置减少长度(这里的值设置最好不要超过100)

	public SimpleViewPagerIndicator(Context context)
	{
		this(context, null);
	}

	public SimpleViewPagerIndicator(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mPaint.setColor(mIndicatorColor);
		mPaint.setStrokeWidth(2.0F);//底部指示线的宽度
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mTabWidth =(w / mTabCount) -mTabCutWidth;//指示线的长度
	}

	public void setTitles(String[] titles)
	{
		mTitles = titles;
		mTabCount = titles.length;
		generateTitleView();

	}

	public void setIndicatorColor(int indicatorColor)
	{
		this.mIndicatorColor = indicatorColor;
	}

	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		canvas.save();
		canvas.translate(mTranslationX, getHeight() - 2);
		canvas.drawLine(mTabCutWidth, 0, mTabWidth, 0, mPaint);//（startX, startY, stopX, stopY, paint）
		canvas.restore();
	}

	public void scroll(int position, float offset)
	{
		/**
		 * <pre>
		 *  0-1:position=0 ;1-0:postion=0;
		 * </pre>
		 */
		mTranslationX = getWidth() / mTabCount * (position + offset);
		invalidate();
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		return super.dispatchTouchEvent(ev);
	}

	private void generateTitleView()
	{
		if (getChildCount() > 0)
			this.removeAllViews();
		int count = mTitles.length;

		setWeightSum(count);
		for (int i = 0; i < count; i++)
		{
			TextView tv = new TextView(getContext());
			LayoutParams lp = new LayoutParams(0,
					LayoutParams.MATCH_PARENT);
			lp.weight = 1;
			tv.setGravity(Gravity.CENTER);
			tv.setTextColor(COLOR_TEXT_NORMAL);
			tv.setText(mTitles[i]);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);//字体大小
			tv.setLayoutParams(lp);
			final int finalI = i;
			tv.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					ListenerConstans.mQunZuPager .setCurrentItem(finalI);
				}
			});
			addView(tv);
		}
	}

}
