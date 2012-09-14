package q.project;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.os.Environment;
import q.project.os.app.CameraA;
import q.project.os.viewc.ScrollViewPagingA;
import q.util.QAppSp;
import q.util.QLog;
import q.util.file.QFileManager;
import q.util.http.QHttpManager;

public class QProjectActivity extends QProjectList {

	@Override
	protected void onInit() {
		
		//startActivity(new Intent(this, ScrollViewPagingA.class));
    	//finish();
    	/*if(true){
    		return;
    	}*/		
		
		
		QHttpManager.getInstance(this).get("http://www.baidu.com", new QHttpManager.CallbackText() {
			
			@Override
			public void onError(IOException e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected long getCacheTime() {
				// TODO Auto-generated method stub
				return 1000 * 60 * 60;
			}
			
			@Override
			protected String getCacheFile() {
				// TODO Auto-generated method stub
				return "/sdcard/aaa";
			}
			
			@Override
			protected boolean checkExist() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onCompleted(String text, String url) {
				QLog.log(text);
			}
		});
		
    	
		init("测试", QTestA.class);
    	init("其他", q.project.other.A.class);
    	init("框架", q.project.framework.A.class);
    	init("Open API", q.project.openapi.A.class);
    	init("app 系统", q.project.os.app.A.class);
    	init("bitmap 位图", q.project.os.bitmap.A.class);
    	init("canvas 画布", q.project.os.canvas.A.class);
    	init("draw 绘图", q.project.os.draw.A.class);
    	init("intent 内外部跳转", q.project.os.intent.A.class);
    	init("media 多媒体", q.project.os.media.A.class);
    	init("View 布局", q.project.os.view.A.class);
    	init("view custom 自定义布局", q.project.os.viewc.A.class);
    	init("布局Filter", q.project.os.viewf.A.class);
    	init("Tab", q.project.view.tab.A.class);
	}
    
}