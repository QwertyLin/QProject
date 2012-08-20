package q.project.os.viewc;

import q.project.QProjectList;

public class A extends QProjectList {

	@Override
	protected void onInit() {
		init("可拖拽的ListView", ListViewDragA.class);	
    	init("按首字母分组排序的ListView", ListViewOrderGroupA.class);	
    	init("分屏分页显示的ScrollView", ScrollViewPagingA.class);
    	init("view paint 画布", ViewPaintA.class);
	}
	
	
}