package q.util.view.scrollView;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class BaseHorizontalScrollView extends HorizontalScrollView {
	
	protected LinearLayout insideLayout; //直接子布局

	/**
	 * @param ctx
	 * @param parentLayout 父布局
	 */
	public BaseHorizontalScrollView(Context ctx, FrameLayout parentLayout) {
		super(ctx);
		//初始化自身
		this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		//初始化内部LinearLayout
		insideLayout = new LinearLayout(ctx);
		insideLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		insideLayout.setOrientation(LinearLayout.HORIZONTAL);
		insideLayout.setGravity(Gravity.CENTER_VERTICAL);
		this.addView(insideLayout);		
		//设置父Layout
		parentLayout.addView(this);		
	}
	
	/**
	 * 添加子View
	 */
	public void addItemView(View v){
		insideLayout.addView(v);
	}
}
