package q.project;

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

public abstract class QProjectList extends ListActivity {
	
	private static final String TEXT = "text", INTENT = "intent";
	private List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();  
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);  	
    	onInit();
    	setListAdapter(new SimpleAdapter(this, list,
                android.R.layout.simple_list_item_1, new String[] { TEXT },
                new int[] { android.R.id.text1 }));
    }
    
    protected abstract void onInit();
    
    protected void init(String text, Class<?> clazz){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put(TEXT, text);
    	map.put(INTENT, new Intent(this, clazz));
    	list.add(map);
    }
    
    protected void init(String text, Class<?> clazz, String extraName, int extraValue){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put(TEXT, text);
    	map.put(INTENT, new Intent(this, clazz).putExtra(extraName, extraValue));
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