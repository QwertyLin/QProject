package q.project.os.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import q.project.QProjectItem;
import q.util.view.QBaseAdapter;
import q.view.list.QListViewUtilAutoLoadMore;

public class ListViewA extends QProjectItem {
	
	List<String> dataStr = new ArrayList<String>();

	@Override
	protected void onInit() {
		dataStr.add("a");
		dataStr.add("b");
		dataStr.add("c");
		//
		btn = getNextButton();
		btn.setText("QListViewUtilAutoLoadMore 自动加载更多");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListView listView = new ListView(ctx);
				final Adapter adapter = new Adapter(ctx, dataStr);
				listView.setAdapter(adapter);
				new QListViewUtilAutoLoadMore(ctx, listView, null, new QListViewUtilAutoLoadMore.Callback() {
					
					@Override
					public boolean onMoreStart() {
						return true;
					}

					@Override
					public void onMoreFinish() {
						adapter.notifyDataSetChanged();
					}

					@Override
					public boolean onMoreOnThread() {
						SystemClock.sleep(1500);
						if(dataStr.size() < 10){
							dataStr.addAll(dataStr);
							return true;
						}
						return false;
					}

					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {}

					
				});
		        listView.setAdapter(adapter);
				dialog(listView);
			}
		});
		//
	}

	
	class Adapter extends QBaseAdapter<String> {

		public Adapter(Context ctx, List<String> datas) {
			super(ctx, datas);
		}

		@Override
		protected int getLayoutId() {
			return android.R.layout.simple_list_item_1;
		}

		@Override
		protected Object getViewHolder(View v) {
			Holder h = new Holder();
			h.tv = (TextView)v.findViewById(android.R.id.text1);
			return h;
		}

		@Override
		protected void onInitItem(int position, String data, Object viewHolder) {
			Holder h = (Holder)viewHolder;
			h.tv.setText(data);
		}
		
		class Holder {
			TextView tv;
		}

		
	}
}
