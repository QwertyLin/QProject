package q.util.view.LinearLayout;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 能够自动换行的Button群，即当Button的个数在一行排不下时，在下一行接着排。
 */
public class LineWrapButtonGroupLinearLayout extends LinearLayout {
	
	public interface Callback {
		/**
		 * 当子按钮被点击时
		 * @param index
		 */
		void onItemClick(int index);
	}
	
	private Context ctx;
	private Button[] btns;
	private LinearLayout tempLayout;//临时布局，用于预先显示Button以便计算Button的宽度
	private final int MARGIN_BOTTOM = 15;//LinearLayout(temp)的下外边距
	private final int MARGIN_RIGHT = 10;//Button右外边距
	private final int PADDING = 20;//Button内边距
	
	public LineWrapButtonGroupLinearLayout(Context ctx, String[] texts, final Callback callback) {
		super(ctx);
		this.ctx = ctx;
		//
		btns = new Button[texts.length];
		//
		this.setOrientation(LinearLayout.VERTICAL);
		//先将高度设为0，等初始化完再设置为wrap_content，以隐藏过渡效果
		this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, 0));
		//this.setBackgroundColor(Color.RED);
		//
		tempLayout = new LinearLayout(ctx);
		tempLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		tempLayout.setOrientation(LinearLayout.VERTICAL);
		this.addView(tempLayout);
		//tempLayout.setBackgroundColor(Color.BLUE);
		//			
		int length = texts.length;
		LinearLayout.LayoutParams lpww = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		lpww.setMargins(0, 0, MARGIN_RIGHT, MARGIN_BOTTOM);
		for(int i = 0; i < length; i++){
			Button b  = new Button(ctx);
			b.setLayoutParams(lpww);
			b.setText(texts[i]);
			//b.setBackgroundColor(Color.RED);
			b.setPadding(PADDING, PADDING, PADDING, PADDING);
			b.setSingleLine();
			final int index = i;
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					callback.onItemClick(index);
				}
			});
			tempLayout.addView(b);
			btns[i] = b;
		}
		//
		new Thread(){
			public void run() {
				SystemClock.sleep(200);
				handler.sendEmptyMessage(0);
			};
		}.start();
	}
	
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			onInitButtons();
		};
	};
	
	private void onInitButtons(){
		//System.out.println("parentLayout:"+getWidth());
		//
		int width = getWidth();
		//
		int widthRest = width;
		int length = btns.length;
		LinearLayout[] layout = new LinearLayout[length];
		LinearLayout.LayoutParams lpfw = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		for(int i = 0, line = 0; i < length; i++){
			TextView tv = btns[i];
			if(null == layout[line]){
				LinearLayout l = new LinearLayout(ctx);
				l.setLayoutParams(lpfw);
				l.setOrientation(LinearLayout.HORIZONTAL);
				//l.setBackgroundColor(Color.BLUE);
				this.addView(l, line);
				layout[line] = l;
			}
			if((widthRest -= tv.getWidth()) > 0){
				widthRest -= this.MARGIN_RIGHT;
				tempLayout.removeView(tv);
				layout[line].addView(tv);
			}else{
				widthRest = width;
				i--;
				line++;
				continue;
			}
		}
		//
		this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT));
	}	
}

/*

初始:
<LinearLayout 
    android:id="@+id/parent"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical"
    >
    <LinearLayout 
        android:id="@+id/temp"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        >
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button1"
            />
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button2"
            />
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button3"
            />
    </LinearLayout>
</LinearLayout>

变化:
<LinearLayout 
    android:id="@+id/parent"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent"
    android:orientation="vertical"
    >
    <LinearLayout 
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        >
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button1"
            />
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button2"
            />
    </LinearLayout>
    <LinearLayout 
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        >
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button3"
            />
        <Button 
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:singleLine="true"
            android:layout_marginRight="10dip"
            android:padding="20dip"
            android:text="button4"
            />
    </LinearLayout>
    <LinearLayout 
        android:id="@+id/temp"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        >
    </LinearLayout>
</LinearLayout>

 */