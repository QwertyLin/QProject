package q.util.view.scrollView;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * 基础ScrollView
 *
 */
public class BaseScrollView extends ScrollView {
	
	protected LinearLayout insideLayout; //直接子布局

	/**
	 * @param ctx
	 * @param parentLayout 父布局
	 */
	public BaseScrollView(Context ctx, FrameLayout parentLayout) {
		super(ctx);
		//初始化自身
		this.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		//初始化内部LinearLayout
		insideLayout = new LinearLayout(ctx);
		insideLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT));
		insideLayout.setOrientation(LinearLayout.VERTICAL);
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
