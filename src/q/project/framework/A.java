package q.project.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class A extends ListActivity {
	
	private static final String TEXT = "text", INTENT = "intent";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
    	setListAdapter(new SimpleAdapter(this, getData(),
                android.R.layout.simple_list_item_1, new String[] { TEXT },
                new int[] { android.R.id.text1 }));
    }
    
    private List<Map<String, Object>> getData(){
    	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  	
    	initData(list, "头部", TitleA.class);
    	return list;
    }
    
    private void initData(List<Map<String, Object>> list, String text, Class<?> clazz){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put(TEXT, text);
    	map.put(INTENT, new Intent(this, clazz));
    	list.add(map);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) l.getItemAtPosition(position);
    	Intent intent = (Intent) map.get(INTENT);
        startActivity(intent);
    }
    
}