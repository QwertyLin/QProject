package q.project.os.view;

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
 * 滑动的抽屉，必须放在RelativeLayout里面。
 *
 */
public class SlidingDrawerC extends LinearLayout {
    
    private boolean mIsShrinking;//是否处于关闭状态
	private int mPosition;
	private int mDuration;
	private boolean mLinearFlying;
	private View mHandle;
	private View mContent;
	private float mTrackX;
	private float mTrackY;
	private float mVelocity;

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	private enum State {
		ABOUT_TO_ANIMATE,//开始动画效果之前
		ANIMATING,//开始动画效果
		READY,
		TRACKING,
		FLYING,//处于既非关闭也非打开的中间阶段
	};
	private State mState;
	private GestureDetector mGestureDetector;
	private int mContentHeight;
	private int mContentWidth;
	private int mOrientation;
	private PanelOnGestureListener mGestureListener;
	
	 public SlidingDrawerC(Context ctx, int position, View handle, View content) {
		super(ctx);
		mDuration = 750;//Defines panel animation duration in ms.
		mPosition = position;//Defines panel position on the screen.
		mLinearFlying = false;//Defines if flying gesture forces linear interpolator in animation.
		//
		mOrientation = (mPosition == TOP || mPosition == BOTTOM)? VERTICAL : HORIZONTAL;
		setOrientation(mOrientation);
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
		if (mPosition == TOP || mPosition == BOTTOM) { 
			setGravity(Gravity.CENTER_HORIZONTAL);
			RelativeLayout.LayoutParams rlpfw = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			if(mPosition == BOTTOM){
				rlpfw.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			}
			setLayoutParams(rlpfw);
		} else {
			setGravity(Gravity.CENTER_VERTICAL);
			RelativeLayout.LayoutParams rlpwf = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.FILL_PARENT);
			if(mPosition == RIGHT){
				rlpwf.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			}
			setLayoutParams(rlpwf);
		}
		//根据位置调整子布局顺序
		if (mPosition == TOP || mPosition == LEFT) { 
			addView(mContent);
			addView(mHandle);
		} else {
			addView(mHandle);
			addView(mContent);
		}
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
		//System.out.println("dispatchDraw");
//		String name = getResources().getResourceEntryName(getId());
//		Log.d(TAG, name + " ispatchDraw " + mState);
		// this is why 'mState' was added:
		// avoid flicker before animation start
		if (mState == State.ABOUT_TO_ANIMATE && !mIsShrinking) {
			int delta = mOrientation == VERTICAL? mContentHeight : mContentWidth;
			if (mPosition == LEFT || mPosition == TOP) {
				delta = -delta;
			}
			if (mOrientation == VERTICAL) {
				canvas.translate(0, delta);
			} else {
				canvas.translate(delta, 0);
			}
		}
		if (mState == State.TRACKING || mState == State.FLYING) {
			canvas.translate(mTrackX, mTrackY);
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
		int initY;
		boolean setInitialPosition;
		public boolean onTouch(View v, MotionEvent event) {
//			Log.d(TAG, "state: " + mState + " x: " + event.getX() + " y: " + event.getY());
			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				System.out.println("ACTION_DOWN");
				initX = 0;
				initY = 0;
				if (mContent.getVisibility() == GONE) {
					System.out.println("mContent GONE");
					// since we may not know content dimensions we use factors here
					if (mOrientation == VERTICAL) {
						initY = mPosition == TOP? -1 : 1;
					} else {
						initX = mPosition == LEFT? -1 : 1;
					}
				}
				setInitialPosition = true;
			} else {
				if (setInitialPosition) {
					// now we know content dimensions, so we multiply factors...
					initX *= mContentWidth;
					initY *= mContentHeight;
					// ... and set initial panel's position
					mGestureListener.setScroll(initX, initY);
					setInitialPosition = false;
					// for offsetLocation we have to invert values
					initX = -initX;
					initY = -initY;
				}
				// offset every ACTION_MOVE & ACTION_UP event 
				event.offsetLocation(initX, initY);
			}
			if (!mGestureDetector.onTouchEvent(event)) {
				//System.out.println("!mGestureDetector.onTouchEvent(event)");
				if (action == MotionEvent.ACTION_UP) {
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
			System.out.println("startAnimation");
			// this is why we post this Runnable couple of lines above:
			// now its save to use mContent.getHeight() && mContent.getWidth()
			TranslateAnimation animation;
			int fromXDelta = 0, toXDelta = 0, fromYDelta = 0, toYDelta = 0;
			if (mState == State.FLYING) {
				//System.out.println("mState == State.FLYING");
				mIsShrinking = (mPosition == TOP || mPosition == LEFT) ^ (mVelocity > 0);
			}
			int calculatedDuration;
			if (mOrientation == VERTICAL) {
				int height = mContentHeight;
				if (!mIsShrinking) {
					fromYDelta = mPosition == TOP? -height : height;
				} else {
					toYDelta = mPosition == TOP? -height : height;
				}
				if (mState == State.TRACKING) {
					if (Math.abs(mTrackY - fromYDelta) < Math.abs(mTrackY - toYDelta)) {
						mIsShrinking = !mIsShrinking;
						toYDelta = fromYDelta;
					}
					fromYDelta = (int) mTrackY;
				} else
				if (mState == State.FLYING) {
					fromYDelta = (int) mTrackY;
				}
				// for FLYING events we calculate animation duration based on flying velocity
				// also for very high velocity make sure duration >= 20 ms
				if (mState == State.FLYING && mLinearFlying) {
					calculatedDuration = (int) (1000 * Math.abs((toYDelta - fromYDelta) / mVelocity));
					calculatedDuration = Math.max(calculatedDuration, 20);
				} else {
					calculatedDuration = mDuration * Math.abs(toYDelta - fromYDelta) / mContentHeight;
				}
			} else {
				int width = mContentWidth;
				if (!mIsShrinking) {
					fromXDelta = mPosition == LEFT? -width : width;
				} else {
					toXDelta = mPosition == LEFT? -width : width;
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
			System.out.println("setScroll");
			scrollX = initScrollX;
			scrollY = initScrollY;
		}
		public boolean onDown(MotionEvent e) {
			System.out.println("onDown");
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
			mVelocity = mOrientation == VERTICAL? velocityY : velocityX;
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
			if (mOrientation == VERTICAL) {
				scrollY -= distanceY;
				if (mPosition == TOP) {
					tmpY = ensureRange(scrollY, -mContentHeight, 0);
				} else  {
					tmpY = ensureRange(scrollY, 0, mContentHeight);
				}
			} else {
				scrollX -= distanceX;
				if (mPosition == LEFT) {
					tmpX = ensureRange(scrollX, -mContentWidth, 0);
				} else {
					tmpX = ensureRange(scrollX, 0, mContentWidth);
				}
			}
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
			System.out.println("onSingleTapUp");
			// simple tap: click
			post(startAnimation);
			return true;
		}
	}
}
