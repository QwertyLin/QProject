package q.project.os.viewc;

import q.project.R;
import q.util.QLog;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

public class ScrollViewPagingA extends Activity {

	ScrollViewPaging v;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		TextView tv1 = new TextView(this);
	    tv1.setText("第一页");
	    TextView tv2 = new TextView(this);
	    tv2.setText("第二页");
	    TextView tv3 = new TextView(this);
	    tv3.setText("第三页");
	    TextView tv4 = new TextView(this);
	    tv4.setText("第四页");
	    //
	    
	    RelativeLayout layout = new RelativeLayout(this);
	    
	   
	    
	    
	    v = new ScrollViewPaging(this, new View[]{tv1, tv2, tv3, tv4}, getResources().getDrawable(R.drawable.ic_launcher));
	    v.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
	    layout.addView(v);
	    
	    TextView tt = new TextView(this);
	    tt.setText("111");
	    layout.addView(tt);
	    
	    setContentView(layout);
	}
	
	@Override
	protected void onStop() {
		QLog.log("onStop");
		super.onStop();
		QLog.log("onStopFinish");
	}
}

/**
 * 分屏分页显示的ScrollView，类似墨迹天气
 * 
 * http://www.eoeandroid.com/case/2012/0502/1518.html
 */
class ScrollViewPaging extends FrameLayout {
	
	private Scroller scroller;
	  private Drawable bgDrawable; //横向平铺的背景大图
	  private TextView page;
	  private int width, height, childCount;  
	  private Context ctx;
	  
	  /**
	 * @param childViews 子布局
	 * @param bgDrawable 大背景
	 */
	public ScrollViewPaging(Context ctx, View[] childViews, Drawable bgDrawable) {
	    super(ctx);
	    this.ctx = ctx;
	    scroller = new Scroller(ctx);
	    if(bgDrawable != null){
	    	this.bgDrawable = bgDrawable;
	        setBackgroundDrawable(null);
	    }
	    //
	    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
		for(View v : childViews){
			this.addView(v, lp);
		}
	    //
	    WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
	    wlp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
	    wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
	    wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    wlp.y = 20;
	    wlp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
	    wlp.format = PixelFormat.TRANSLUCENT;
	    wlp.windowAnimations = 0;
	    page = new TextView(ctx);
	    page.setBackgroundColor(0xFFFF0000);
	    onItemSelected(0);
	  }
	  
	  private void onItemSelected(int childIndex){
		  page.setText("第" + (childIndex + 1) + "页");
	  }
	  
	  @Override
		protected void onLayout(boolean changed, int l, int t, int r, int b) {
			QLog.log("onLayout");
			if (width == 0) {
				width = getWidth();
				height = getHeight();
				childCount = getChildCount();
				int childLeft = 0;
				for (int i = 0; i < childCount; i++) {
					final View child = getChildAt(i);
					child.layout(childLeft, 0, childLeft + width, height);
					childLeft += width;
				}
			}
		}
	  
	  @Override
	  protected void dispatchDraw(Canvas canvas) {
		  //以横线平铺的方式绘制背景。
		  QLog.log("dispatchDraw");
	    if (null != bgDrawable) {
	      bgDrawable.setBounds(0, 0, childCount * width, height);
	      bgDrawable.draw(canvas);
	    }
	    super.dispatchDraw(canvas);
	  }
	    
	  private int lastX = 0;
	  private final int TOUCH_SLOP = ViewConfiguration.get(getContext()).getScaledTouchSlop();

	  @Override
	  public boolean onInterceptTouchEvent(MotionEvent event) {
		  QLog.log("onInterceptTouchEvent");
	    final int x = (int) event.getX();
	    boolean flag = false;
	    
	    switch(event.getAction()){
	      case MotionEvent.ACTION_DOWN:
	        QLog.log("onInterceptTouchEvent ACTION_DOWN");
	        lastX = x;
	        break;
	      case MotionEvent.ACTION_MOVE:
	    	  QLog.log("onInterceptTouchEvent ACTION_MOVE");
	        final int deltaX = (int) (lastX - x);
	        if (Math.abs(deltaX) > TOUCH_SLOP) {
	          flag = true;
	        }
	        break;
	      case MotionEvent.ACTION_UP:
	    	  QLog.log("onInterceptTouchEvent ACTION_UP");
	        break;
	    }
	    return flag;
	  }
	  
	  public boolean onTouchEvent(MotionEvent event) {
		  QLog.log("onTouchEvent");
	    final int x = (int) event.getX();

	    switch (event.getAction()) {
	      case MotionEvent.ACTION_DOWN:
	    	  QLog.log("onTouchEvent ACTION_DOWN");
	        if (!scroller.isFinished()) {
	          scroller.abortAnimation();
	        }

	        break;
	      case MotionEvent.ACTION_MOVE:
	    	 QLog.log("onTouchEvent ACTION_MOVE");
	        final int deltaX = (int) (lastX - x);
	        if (Math.abs(deltaX) < TOUCH_SLOP) {
	          break;
	        }
	        lastX = x;
	        if (deltaX < 0) {
	          if (getScrollX() > 0) {
	            scrollBy(Math.max(-getScrollX(), deltaX), 0);
	          }
	        } else if (deltaX > 0) {
	          final int availableToScroll = getChildAt(childCount - 1).getRight() - getScrollX() - width;
	          if (availableToScroll > 0) {
	            scrollBy(Math.min(availableToScroll, deltaX), 0);
	          }
	        }

	        break;
	      case MotionEvent.ACTION_UP:
	      case MotionEvent.ACTION_CANCEL:
	    	  QLog.log("onTouchEvent ACTION_UP || ACTION_CANCEL");
	        int dx = (getScrollX() + width / 2) / width;
	        if (dx < 0) {
	          dx = 0;
	        }
	        if (dx > childCount - 1) {
	          dx = childCount - 1;
	        }
	        onItemSelected(dx);
	        dx *= width;
	        dx -= getScrollX();
	        scroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 3);
	        break;
	    }
	    invalidate();
	    return true;
	  }
	  
	  @Override
	  public void computeScroll() {
		  QLog.log("computeScroll");
	    if (scroller.computeScrollOffset()) {
	      scrollTo(scroller.getCurrX(), scroller.getCurrY());
	      QLog.log("scrollTo" + "x=" + scroller.getCurrX() + " y=" + scroller.getCurrY());
	      invalidate();
	    }
	  }
}