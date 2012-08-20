package q.util.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * 选中子元素自动居中的ScrollView
 */
public class AlignCenterScrollView extends ScrollView {
	
	public interface Callback {

		/**
		 * 选中子元素时回调
		 * @param index 当前索引，从0开始
		 */
		void onItemSelected(int index);
		
		/**
		 * 根据选中的索引初始化选中界面
		 * @param index  当前索引，从0开始
		 */
		View initSelectedView(int index);
	}
	
	private Context ctx;
	private Callback callback;
	private int itemHeight;//子元素高度
	private int marginHeight; //底部与底部空白高度
	private int currentIndex = 0; //当前索引
	private LinearLayout insideLayout; //直接子布局
	private RelativeLayout outsideLayout; //直接父布局
	private FrameLayout selectedLayout; //选中子元素后显示的布局
	
	public AlignCenterScrollView(Context ctx, int itemHeight, View[] childViews, FrameLayout parentLayout, Callback callback) {
		super(ctx);
		this.ctx = ctx;
		this.itemHeight = itemHeight;
		this.callback = callback;
		//初始化自身
		this.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT));
		//初始化外部RelativeLayout
		outsideLayout = new RelativeLayout(ctx);
		outsideLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		outsideLayout.addView(this);
		//选中视图
		selectedLayout = new FrameLayout(ctx);
		selectedLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, itemHeight));
		outsideLayout.addView(selectedLayout);
		//初始化内部LinearLayout
		insideLayout = new LinearLayout(ctx);
		insideLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		insideLayout.setOrientation(LinearLayout.VERTICAL);
		this.addView(insideLayout);
		//监听触摸
		this.setOnTouchListener(onTouchListener);
		//设置父Layout
		parentLayout.addView(outsideLayout);
		//设置子view
		addChild(childViews);
		//重新处理显示，需延迟一点时间
		new Thread(){
			public void run() {
				SystemClock.sleep(500);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	//设置子元素，实质是设置子布局LinearLayout的子元素
	private void addChild(View[] views){
		int size = views.length;
		for(int i = 0; i < size; i++){
			final int index = i;
			View v = views[i];
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setCurrentIndex(index);
				}
			});
			insideLayout.addView(v);
		}
	}
	
	//初始化界面
	private void init(){
		int scrollHeight = getHeight();
		//QUtil4A.log(AlignCenterScrollView.this, "scrollHeight", scrollHeight);
		marginHeight = (scrollHeight - itemHeight) / 2;
		if((scrollHeight - itemHeight) % 2 != 0){
			marginHeight++;//保证不小于
		}
		//顶部空白
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, marginHeight);
		View v = new View(ctx);
		v.setLayoutParams(lp);
		insideLayout.addView(v, 0);
		//底部空白
		v = new View(ctx);
		v.setLayoutParams(lp);
		insideLayout.addView(v);
		//初始化选中页
		((RelativeLayout.LayoutParams)selectedLayout.getLayoutParams()).setMargins(0, marginHeight, 0, 0);
		setCurrentIndex(currentIndex);
	}
	
	/**
	 * 获得当前索引
	 */
	public int getCurrentIndex(){
		return currentIndex;
	}
	
	/**
	 * 设置当前索引，调用Callback.onItemSeleted。不要交叉调用2个类的setCurrentIndex，否则会重复循环调用
	 */
	public void setCurrentIndex(int index){
		if(getScrollY() != index * itemHeight) {
			//QUtil4A.log(AlignCenterScrollView.this, "scrollHeight adjust", index * itemHeight);
			smoothScrollTo(0, index * itemHeight);
		}
		if(index != currentIndex) {
			currentIndex = index;
		}
		//QUtil4A.log(AlignCenterScrollView.this, "index", index);
		callback.onItemSelected(index);
		selectedLayout.removeAllViews();
		selectedLayout.addView(callback.initSelectedView(index));	
		selectedLayout.setVisibility(VISIBLE);
	}
	
	private OnTouchListener onTouchListener = new OnTouchListener() {
		private int lastY = 0;
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (lastY != msg.what) {
					lastY = msg.what;
					handler.sendEmptyMessageDelayed(AlignCenterScrollView.this.getScrollY(), 5);
				} else {
					handleStop();
				}
			}
		};
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch(event.getAction()){
			case MotionEvent.ACTION_UP:
				handler.sendEmptyMessageDelayed(v.getScrollY(), 5);
				break;
			case MotionEvent.ACTION_MOVE:
				selectedLayout.setVisibility(INVISIBLE);
			}
			return false;
		}
		
		// 这里写真正的事件
		private void handleStop() {
			int scrollY = AlignCenterScrollView.this.getScrollY();
			//QUtil4A.log(AlignCenterScrollView.this, "scrollY", scrollY);
			int index = scrollY / itemHeight; //索引
			if(scrollY % itemHeight > marginHeight){
				index ++; //对齐
			}
			setCurrentIndex(index);
		}
	}; 
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			init();
		};
	};
	
}

