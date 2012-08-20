package q.project;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridActivity extends Activity implements OnItemClickListener {
	
	List<ResolveInfo> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_list);
		
		Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        data = getPackageManager().queryIntentActivities(mainIntent, 0);
		
		//getViews
		GridView gridView = (GridView) findViewById(R.id.grid);
		
		//setViews
		gridView.setAdapter(new DataAdapter());
		
		//setListener
		gridView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("Q", "==onItemClick: "+position);
	}
	
	class DataAdapter extends BaseAdapter {
		
		private LayoutInflater inflater = getLayoutInflater();

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
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.grid_item, null);
				holder = new Holder();
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				convertView.setTag(holder);
			}else {
				holder = (Holder)convertView.getTag();
			}
			holder.image.setImageDrawable(data.get(position).activityInfo.loadIcon(getPackageManager()));
			return convertView;
		}
		
	}
	
	static class Holder {
		ImageView image;
	}
}
