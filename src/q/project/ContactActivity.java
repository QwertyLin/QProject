package q.project;

import java.util.List;

import q.util.QBitmap;
import q.util.a.QContact;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ContactActivity extends Activity {
	
	private List<QContact.Contact> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.alert_dark_frame);
		QContact.updatePhoto(this, 3, QBitmap.toByte(bitmap));
		
		data = QContact.findAllContacts(this);
		
		//getViews
		ListView listView = (ListView) findViewById(R.id.listView);
		
		//setViews
		listView.setAdapter(new DataAdapter());
		
		
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
				convertView = inflater.inflate(R.layout.contact_item, null);
				holder = new Holder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.photo = (ImageView) convertView.findViewById(R.id.photo);
				holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
				convertView.setTag(holder);
			}else {
				holder = (Holder)convertView.getTag();
			}
			QContact.Contact item = data.get(position);
			System.out.println(item.id);
			holder.name.setText(item.name);
			holder.photo.setImageBitmap(item.photo);
			return convertView;
		}
		
	}
	
	static class Holder {
		TextView name;
		ImageView photo;
		TextView textView2;
	}
}
