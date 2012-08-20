package q.project.os.viewc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import net.sourceforge.pinyin4j.PinyinHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListViewOrderGroupA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(this);
		setContentView(listView);
		//
		listView.setFastScrollEnabled(true);
		listView.setAdapter(new ListAdapterOrderGroup(this, new String[]{"啊", "a", "123", "java", "工程师", "android", "程序员","啊", "a", "123", "java", "工程师", "android", "程序员"}));
	}
}

/**
 * 按首字母分组排序的List Adapter，中文取拼音首字母。
 *
 */
class ListAdapterOrderGroup extends BaseAdapter {
	
	private Context ctx;
	private List<Pinyin> data = new ArrayList<Pinyin>();
	
	public ListAdapterOrderGroup(Context ctx, String[] list){
		this.ctx = ctx;
		//			
		TreeSet<Pinyin> dataSet = new TreeSet<Pinyin>(new Comparator<Pinyin>() {
			@Override
			public int compare(Pinyin o1, Pinyin o2) {
				if(o1.isCn() && o2.isCn()){
					return o1.pinyin.compareTo(o2.pinyin);
				}else if(o1.isCn() && !o2.isCn()){
					return o1.pinyin.compareToIgnoreCase(o2.text);
				}else if( !o1.isCn() && o2.isCn()){
					return o1.text.compareToIgnoreCase(o2.pinyin);
				}else{
					return o1.text.compareToIgnoreCase(o2.text);
				}
			}
		});
    	Pinyin py;
    	for(String s : list){
    		py = new Pinyin();
    		py.text = s;
    		String[] cs = PinyinHelper.toHanyuPinyinStringArray(s.charAt(0));
    		if(cs != null){
    			py.pinyin = PinyinHelper.toHanyuPinyinStringArray(s.charAt(0))[0];
    			py.initial = py.pinyin.charAt(0);
    		}else{
    			py.initial = s.charAt(0);
    		}
    		dataSet.add(py);
    	}	    	
    	Set<Character> tagSet = new HashSet<Character>();
    	Pinyin tag;
    	for(Pinyin p : dataSet){
    		if(tagSet.add(p.initial)){
    			tag = new Pinyin();
    			tag.initial = p.initial;
    			data.add(tag);
    		}
    		data.add(p);
    	}
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public boolean isEnabled(int position) {
		if(data.get(position).isInitial()){
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(int position, View v, ViewGroup parent) {
		Holder h;
		if(v == null){
			LinearLayout layout = new LinearLayout(ctx);
			layout.setPadding(20, 20, 20, 20);
			TextView tv = new TextView(ctx);
			layout.addView(tv);
			h = new Holder();
			h.tv = tv;
			v = layout;
			v.setTag(h);
		}else{
			h = (Holder)v.getTag();
		}
		Pinyin item = data.get(position);
		if(item.isInitial()){
			h.tv.setText(String.valueOf(item.initial));
		}else{
			h.tv.setText(item.text + " py:" + item.pinyin);
		}
		return v;
	}
	
	class Holder {
		TextView tv;
	}
	
	class Pinyin {
    	String text; 
    	String pinyin; 
    	Character initial; //首字母
    	
    	boolean isCn(){ //若pinyin为空，则为英语
    		return pinyin != null;
    	}
    	
    	boolean isInitial(){ //若text为空，则为标签
    		return text == null;
    	}
    }
	
}