package q.project;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.AlertDialog;

import q.util.QLog;
import q.util.http.QHttpManager;

public class QProjectActivity extends QProjectList {

	@Override
	protected void onInit() {
		
		Document doc = Jsoup.parse("我在 <a para=\"56639\" type=\"63\" title=\"辉记潮汕海鲜砂锅粥 \" href=\"http://food.poco.cn/res_detail-htx-id-56639.shtml\" location_id=\"101029001006001\">#辉记潮汕海鲜砂锅粥#</a> 波萝炒虾球鲜甜可口，嗯");
		
		//
		QLog.log(this, doc.html());
		QLog.log(this, doc.select("a").first().attr("href"));
		
		//startActivity(new Intent(this, ScrollViewPagingA.class));
    	//finish();
    	/*if(true){
    		return;
    	}*/		
		
		
		/*QHttpManager.getInstance(this).get("http://www.36kr.com", new QHttpManager.CallbackText() {
			
			@Override
			public void onError(IOException e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected long getCacheTime() {
				// TODO Auto-generated method stub
				return 10000000000l;
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
				//QLog.log(text);
				Document doc = Jsoup.parse(text);
				Element list = doc.getElementById("posts");
				boolean one = true;
				Element title;
				for(Element item : list.getElementsByClass("posts")){
					if(one){
						one = false;
						title = item.select("h3 a").first();
						QLog.kv(this, "", "title", title.text());
						QLog.kv(this, "", "url", title.attr("href"));
						QLog.kv(this, "", "time", item.select(".timeago").first().attr("title"));
						QLog.kv(this, "", "image", item.select(".thumbnail img").first().attr("src"));
						//new AlertDialog.Builder(QProjectActivity.this).setMessage(item.text()).show();
					}
				}
				
			}
		});*/
		
		/*QHttpManager.getInstance(this).get("http://www.36kr.com/p/153198.html", new QHttpManager.CallbackText() {
			
			@Override
			public void onError() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			protected long getCacheTime() {
				// TODO Auto-generated method stub
				return 10000000;
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
				//QLog.log(text);
				Document doc = Jsoup.parse(text);
				//Element list = doc.getElementById(".instapaper_body");
				System.out.println(doc.select("article").size());
				Element list = doc.select("article").first();
				boolean verify = false;
				for(Element e : doc.getElementById("post").children()){
					if(verify){
						QLog.log(this, e.text());
						if(e.tagName().equals("div")){
							break;
						}
					}
					if(e.tagName().equals("hr")){
						verify = true;
					}
					
				}
				
				
			}

			@Override
			protected boolean verify(String text) {
				if(text.contains("36氪")){
					return true;
				}
				return false;
			}
		});*/
		
    	
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