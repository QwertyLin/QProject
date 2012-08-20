package q.project.other;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;

public class XmlParser extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		setContentView(tv);
		//
		try {
			StringBuffer sb = new StringBuffer();
			for(Item item : parseXml(getAssets().open("xml.xml"))){
				sb.append("id=" + item.id + " name=" + item.name + "\n");
			}
			tv.setText(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Item> parseXml(InputStream inputStream) throws Exception{
		List<Item> list = null;
		Item item = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(inputStream, "UTF-8");
		int event = parser.getEventType();
		while(event != XmlPullParser.END_DOCUMENT){
			switch (event) {
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<Item>();
				break;
			case XmlPullParser.START_TAG:
				if("item".equals(parser.getName())){
					item = new Item();
				}
				if( item != null){
					if("id".equals(parser.getName())){
						item.id = Integer.parseInt(parser.nextText());
					}
					if("name".equals(parser.getName())){
						item.name = parser.nextText();
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if("item".equals(parser.getName())){
					list.add(item);
					item = null;
				}
				break;
			}
			event = parser.next();
		}
		inputStream.close();
		return list;
	}
	
	static class Item {
		int id;
		String name;
	}
}
