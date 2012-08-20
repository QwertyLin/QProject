package q.project.os.view;

import q.project.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridViewA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GridView gv = new GridView(this);
		setContentView(gv);
		//
		gv.setNumColumns(3);
		gv.setHorizontalSpacing(20);
		gv.setVerticalSpacing(20);
		gv.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
		gv.setGravity(Gravity.CENTER);
		//
		String[] data = new String[30];
		for(int i = 0; i < 30; i++){
			data[i] = i + "" + i;
		}
		gv.setAdapter(new DataAdapter(this, data));
	}
	
	class DataAdapter extends BaseAdapter {
		
		Context ctx;
		String[] data;
		
		public DataAdapter(Context ctx, String[] data){
			this.ctx = ctx;
			this.data = data;
		}

		@Override
		public int getCount() {
			return data.length;
		}

		@Override
		public Object getItem(int position) {
			return data[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View v, ViewGroup parent) {
			Holder h = null;
			if(v == null){
				LinearLayout layout = new LinearLayout(ctx);
				layout.setOrientation(LinearLayout.VERTICAL);
				v = layout;
				TextView tv = new TextView(ctx);
				layout.addView(tv);
				ImageView iv = new ImageView(ctx);
				layout.addView(iv);
				h = new Holder();
				h.tv = tv;
				h.iv = iv;
				v.setTag(h);
			}else{
				h = (Holder)v.getTag();
			}
			String item = (String)getItem(position);
			h.tv.setText(item);
			h.iv.setImageResource(R.drawable.ic_launcher);
			return v;
		}
		
		class Holder{
			TextView tv;
			ImageView iv;
		}
		
	}

	
}

