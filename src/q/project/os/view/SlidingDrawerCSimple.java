package q.project.os.view;

import q.QLog;
import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 简化的滑动的抽屉，只从右边滑出，必须放在RelativeLayout里面。
 *
 */
public class SlidingDrawerCSimple extends LinearLayout {
    
    private boolean mIsShrinking;//是否处于关闭状态
	private int mDuration;
	private boolean mLinearFlying;
	private View mHandle;
	private View mContent;
	private float mTrackX;
	private float mTrackY;
	private float mVelocity;
	
	private enum State {
		ABOUT_TO_ANIMATE,//开始动画效果之前
		ANIMATING,//动画自动滑动
		READY,
		TRACKING,//手动滑动
		FLYING,//处于既非关闭也非打开的中间阶段
	};
	private State mState;
	private GestureDetector mGestureDetector;
	private int mContentHeight;
	private int mContentWidth;
	private PanelOnGestureListener mGestureListener;
	
	 public SlidingDrawerCSimple(Context ctx, int position, View handle, View content) {
		super(ctx);
		mDuration = 750;//Defines panel animation duration in ms.
		mLinearFlying = false;//Defines if flying gesture forces linear interpolator in animation.
		//
		setOrientation(HORIZONTAL);
		mState = State.READY;
		mGestureListener = new PanelOnGestureListener();
		mGestureDetector = new GestureDetector(mGestureListener);
		mGestureDetector.setIsLongpressEnabled(false);
		//
		mHandle = handle;
		if (mHandle == null) {
	        throw new RuntimeException("Your Panel must have a View whose id attribute is 'R.id.panelHandle'");
		}
		mHandle.setOnTouchListener(touchListener);
		mContent = content;
		if (mContent == null) {
	        throw new RuntimeException("Your Panel must have a View whose id attribute is 'R.id.panelContent'");
		}
		// reposition children
		removeView(mHandle);
		removeView(mContent);
		//根据位置调整宽度、高度
		setGravity(Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams rlpwf = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.FILL_PARENT);
		rlpwf.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		setLayoutParams(rlpwf);
		//根据位置调整子布局顺序
		addView(mHandle);
		addView(mContent);
		//初始化子布局
		mContent.setVisibility(GONE);
	 }
	 
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (mContentWidth == 0) {
			mContentWidth = mContent.getWidth();
			mContentHeight = mContent.getHeight();
		}
	}

	/**
	 * 当打开或关闭完成时
	 */
	private void onFinish() {
		// invoke listener if any
		if(isOpen()){
			//TODO when opened
		}else{
			//TODO when closed
		}
	}
    
	/**
	 * Set the opened state of Panel.
     * 
     * @param open True if Panel is to be opened, false if Panel is to be closed.
	 * @param animate True if use animation, false otherwise.
	 * 
	 */
	public void setOpen(boolean open, boolean animate) {
		System.out.println(true ^ false);
		if (isOpen() ^ open) {
			mIsShrinking = !open;
			if (animate) {
				mState = State.ABOUT_TO_ANIMATE;
				if (!mIsShrinking) {
					// this could make flicker so we test mState in dispatchDraw()
					// to see if is equal to ABOUT_TO_ANIMATE
					mContent.setVisibility(VISIBLE);
				}
				post(startAnimation);
			} else {
				mContent.setVisibility(open? VISIBLE : GONE);
				onFinish();
			}
		}
	}

    /**
     * Returns the opened status for Panel.
     * 
     * @return True if Panel is opened, false otherwise.
     */
	public boolean isOpen() {
		return mContent.getVisibility() == VISIBLE;
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		//主要用于处理打开抽屉或手动滑动抽屉时的子布局显示
		if (mState == State.ABOUT_TO_ANIMATE && !mIsShrinking) {
			QLog.log(this, "dispatchDraw");
			QLog.log(this, "state ABOUT_TO_ANIMATE");
			int delta = mContentWidth;
			QLog.log(this, "delta" + delta);
			canvas.translate(delta, 0);
		}
		if (mState == State.TRACKING || mState == State.FLYING) {
			//QLog.a(this, "dispatchDraw");
			//QLog.b(this, "state", "TRACKING");
			//QLog.b(this, "translate", "mTrackX=" + mTrackX + " mTrackY=" + mTrackY);
			canvas.translate(mTrackX, mTrackY);//拖动时对原位置的偏移
		}
		super.dispatchDraw(canvas);
	}
	
	private float ensureRange(float v, int min, int max) {
		v = Math.max(v, min);
		v = Math.min(v, max);
		return v;
	}

	OnTouchListener touchListener = new OnTouchListener() {
		int initX;
		boolean setInitialPosition;
		public boolean onTouch(View v, MotionEvent event) {
			QLog.log(this, "onTouch");
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				QLog.log(this, "action ACTION_DOWN");
				initX = 0;
				if (mContent.getVisibility() == GONE) {
					// since we may not know content dimensions we use factors here
					initX = 1;
				}
				setInitialPosition = true;
			} else {
				if (setInitialPosition) {
					// now we know content dimensions, so we multiply factors...
					initX *= mContentWidth;
					// ... and set initial panel's position
					mGestureListener.setScroll(initX, 0); //为滑动设置偏移量
					QLog.log(this, "mGestureListener.setScroll" + "x=" + initX);
					setInitialPosition = false;
					// for offsetLocation we have to invert values
					initX = -initX;
				}
				// offset every ACTION_MOVE & ACTION_UP event 
				event.offsetLocation(initX, 0);
			}
			if (!mGestureDetector.onTouchEvent(event)) {
				//System.out.println("!mGestureDetector.onTouchEvent(event)");
				if (action == MotionEvent.ACTION_UP) {//几乎不发生
					System.out.println("ACTION_UP");
					// tup up after scrolling
					post(startAnimation);
				}
			}
			return false;
		}
	};

	Runnable startAnimation = new Runnable() {
		public void run() {
			QLog.log(this, "startAnimation");
			// this is why we post this Runnable couple of lines above:
			// now its save to use mContent.getHeight() && mContent.getWidth()
			TranslateAnimation animation;
			int fromXDelta = 0, toXDelta = 0, fromYDelta = 0, toYDelta = 0;
			if (mState == State.FLYING) {
				QLog.log(this, "state FLYING");
				mIsShrinking = (false) ^ (mVelocity > 0);
				QLog.log(this, "mIsShrinking" + String.valueOf(mIsShrinking));
			}
			int calculatedDuration;
				int width = mContentWidth;
				if (!mIsShrinking) {
					fromXDelta = width;
				} else {
					toXDelta = width;
				}
				if (mState == State.TRACKING) {
					if (Math.abs(mTrackX - fromXDelta) < Math.abs(mTrackX - toXDelta)) {
						mIsShrinking = !mIsShrinking;
						toXDelta = fromXDelta;
					}
					fromXDelta = (int) mTrackX;
				} else
				if (mState == State.FLYING) {
					fromXDelta = (int) mTrackX;
				}
				// for FLYING events we calculate animation duration based on flying velocity
				// also for very high velocity make sure duration >= 20 ms
				if (mState == State.FLYING && mLinearFlying) {
					calculatedDuration = (int) (1000 * Math.abs((toXDelta - fromXDelta) / mVelocity));
					calculatedDuration = Math.max(calculatedDuration, 20);
				} else {
					calculatedDuration = mDuration * Math.abs(toXDelta - fromXDelta) / mContentWidth;
				}
			
			mTrackX = mTrackY = 0;
			if (calculatedDuration == 0) {
				mState = State.READY;
				if (mIsShrinking) {
					mContent.setVisibility(GONE);
				}
				onFinish();
				return;
			}
			
			System.out.println(fromXDelta + " " + toXDelta + " " + fromYDelta + " " + toYDelta);
			animation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
			animation.setDuration(calculatedDuration);
			animation.setAnimationListener(animationListener);
			if (mState == State.FLYING && mLinearFlying) {
				animation.setInterpolator(new LinearInterpolator());
			}
			startAnimation(animation);
		}
	};

	private AnimationListener animationListener = new AnimationListener() {
		public void onAnimationEnd(Animation animation) {
			setAnimation(new TranslateAnimation(0,0,0,0));//重要！加上后就不会闪动。
			mState = State.READY;
			if (mIsShrinking) {
				mContent.setVisibility(GONE);
			}
			onFinish();
		}
		public void onAnimationRepeat(Animation animation) {
		}
		public void onAnimationStart(Animation animation) {
			mState = State.ANIMATING;
		}
	};
	
	class PanelOnGestureListener implements OnGestureListener {
		float scrollY;
		float scrollX;
		public void setScroll(int initScrollX, int initScrollY) {
			scrollX = initScrollX;
			scrollY = initScrollY;
		}
		public boolean onDown(MotionEvent e) {
			QLog.log(this, "onDown");
			scrollX = scrollY = 0;
			if (mState != State.READY) {
				// we are animating or just about to animate
				return false;
			}
			mState = State.ABOUT_TO_ANIMATE;
			mIsShrinking = mContent.getVisibility() == VISIBLE;
			if (!mIsShrinking) {
				// this could make flicker so we test mState in dispatchDraw()
				// to see if is equal to ABOUT_TO_ANIMATE
				mContent.setVisibility(VISIBLE);
			}
			return true;
		}
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			System.out.println("onFling");
			mState = State.FLYING;
			mVelocity = velocityX;
			post(startAnimation);
			return true;
		}
		public void onLongPress(MotionEvent e) {
			// not used
		}
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			System.out.println("onScroll");
			mState = State.TRACKING;
			float tmpY = 0, tmpX = 0;
				scrollX -= distanceX;
				tmpX = ensureRange(scrollX, 0, mContentWidth);
			if (tmpX != mTrackX || tmpY != mTrackY) {
				mTrackX = tmpX;
				mTrackY = tmpY;
				invalidate();
			}
			return true;
		}
		public void onShowPress(MotionEvent e) {
			// not used
		}
		//单击
		public boolean onSingleTapUp(MotionEvent e) {
			QLog.log(this, "onSingleTapUp");
			// simple tap: click
			post(startAnimation);
			return true;
		}
	}
}
