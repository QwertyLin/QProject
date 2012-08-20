package q.project.other;

import q.project.QProjectList;

public class A extends QProjectList {
	
	@Override
	protected void onInit() {
		//init("Http缓存", HttpCache.class);
    	init("拍照", QCamera.class);
    	init("Xml解析", XmlParser.class);
    	init("物理按钮监听", KeyListener.class);
    	init("正则表达式", RegExpA.class);
	}
	
}