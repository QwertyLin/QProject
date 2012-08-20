package q.project.os.view;

import q.project.QProjectList;

public class A extends QProjectList {
	
	@Override
	protected void onInit() {
		init("Dialog 对话框", DialogA.class);
		init("GestureDetector 手势", GestureDetectorA.class);
		init("GridView 网格", GridViewA.class);
		init("PopupWindow", PopupWindowA.class);
		init("SlidingDrawer 滑动抽屉", SlidingDrawerA.class);
		init("TextView 文本", TextViewA.class);
		init("View", ViewA.class);
		init("WebView", WebViewA.class);
		init("在屏幕任意位置显示布局 WindowManager", WindowManagerA.class);
	}
	
}