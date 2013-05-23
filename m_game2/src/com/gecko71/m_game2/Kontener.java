package com.gecko71.m_game2;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class Kontener {

	
	public static Bitmap getResizedBitmap(Bitmap bm, int newWidth,int newHeight) {
		 
		int width = bm.getWidth();
	    int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = 	Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
		return resizedBitmap;
	}

	

	
	
	public static void  adjustTextSize(Paint mTextPaint,String mText,int mViewHeight) {
	    mTextPaint.setTextSize(100);
	    mTextPaint.setTextScaleX(1.0f);
	    Rect bounds = new Rect();
	    // ask the paint for the bounding rect if it were to draw this
	    // text
	    mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
	 
	    // get the height that would have been produced
	    int h = bounds.bottom - bounds.top;
	 
	    // make the text text up 70% of the height
	    float target = (float)mViewHeight*.7f;
	 
	    // figure out what textSize setting would create that height
	    // of text
	    float size  = ((target/h)*100f);
	 
	    // and set it into the paint
	    mTextPaint.setTextSize(size);
	}
	
	public static  int adjustTextScale(Paint mTextPaint,String mText,int mViewHeight,int mViewWidth) {
		int mTextBaseline;
	    // do calculation with scale of 1.0 (no scale)
	    mTextPaint.setTextScaleX(1.0f);
	    Rect bounds = new Rect();
	    // ask the paint for the bounding rect if it were to draw this
	    // text.
	    mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
	 
	    // determine the width
	    int w = bounds.right - bounds.left;
	 
	    // calculate the baseline to use so that the
	    // entire text is visible including the descenders
	    int text_h = bounds.bottom-bounds.top;
	    mTextBaseline=bounds.bottom+((mViewHeight-text_h)/2);
	 
	    // determine how much to scale the width to fit the view
	    float xscale = ((float) (mViewWidth-4)) / w;
	 
	    // set the scale for the text paint
	    mTextPaint.setTextScaleX(xscale);
	    return mTextBaseline;
	}
}
