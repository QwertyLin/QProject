package q.project.os.draw;

import q.util.QLog;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class RepeatDrawA extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new DrawView(this));
	}
}

class DrawView extends View {
	private Paint[] mPaints;
	private Paint mFramePaint;
	private RectF[] mOvals;
	private RectF mBigOval;
	private float mStart;
	private float mSweep;
	private int mBigIndex;
	private boolean[] mUseCenters;

	private static final float START_INC = 0;
	private static final float SWEEP_INC = 2;

	public DrawView(Context context) {
		super(context);

		mPaints = new Paint[4];
		mUseCenters = new boolean[4];
		mOvals = new RectF[4];

		mPaints[0] = new Paint();
		mPaints[0].setAntiAlias(true);
		mPaints[0].setColor(0x88111111);
		mUseCenters[0] = false;

		mPaints[1] = new Paint(mPaints[0]);
		mPaints[1].setStyle(Paint.Style.FILL);
		mPaints[1].setColor(0x88FF0000);
		mUseCenters[1] = true;

		mPaints[2] = new Paint(mPaints[0]);
		mPaints[2].setStyle(Paint.Style.STROKE);
		mPaints[2].setStrokeWidth(10);
		mPaints[2].setColor(0x8800FF00);
		mUseCenters[2] = false;

		mPaints[3] = new Paint(mPaints[2]);
		mPaints[3].setStyle(Paint.Style.FILL_AND_STROKE);
		mPaints[3].setColor(0x880000FF);
		mUseCenters[3] = true;

		mBigOval = new RectF(40, 310, 280, 550);

		mOvals[0] = new RectF(10, 570, 70, 630);
		mOvals[1] = new RectF(90, 570, 150, 630);
		mOvals[2] = new RectF(170, 570, 230, 630);
		mOvals[3] = new RectF(250, 570, 310, 630);

		mFramePaint = new Paint();
		mFramePaint.setAntiAlias(true);
		mFramePaint.setStyle(Paint.Style.STROKE);
		mFramePaint.setStrokeWidth(2);
	}

	private void drawArcs(Canvas canvas, RectF oval, boolean useCenter,
			Paint paint) {
		canvas.drawRect(oval, mFramePaint);
		canvas.drawArc(oval, mStart, mSweep, useCenter, paint);
	}

	protected void onDraw(Canvas canvas) {
		QLog.log(this, "onDraw");
		canvas.drawColor(Color.WHITE);//背景
		//画笔
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(0xFFFF0000);
		paint.setStyle(Paint.Style.STROKE);
		//
		canvas.drawRect(new Rect(10, 10, 100, 100), paint);
		
		
		drawArcs(canvas, mBigOval, mUseCenters[mBigIndex],
				mPaints[mBigIndex]);

		for (int i = 0; i < 4; i++) {
			drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i]);
		}

		mSweep += SWEEP_INC;
		if (mSweep > 360) {
			mSweep -= 360;
			mStart += START_INC;
			if (mStart >= 360) {
				mStart -= 360;
			}
			mBigIndex = (mBigIndex + 1) % mOvals.length;
		}
		invalidate(); //重复循环
	}
	
}