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
import qv.adapter.QAdapterBase;
import qv.list.QAutoLoadMoreListViewFilter;
import qv.list.QListViewUtil;
import qv.list.QPullToRefreshListView;

public class ListViewA extends QProjectItem {
	
	List<String> dataStr = new ArrayList<String>();

	@Override
	protected void onInit() {
		dataStr.add("a");
		dataStr.add("b");
		dataStr.add("c");
		dataStr.add("a");
		dataStr.add("b");
		dataStr.add("c");
		dataStr.add("a");
		dataStr.add("b");
		dataStr.add("c");
		final Adapter adapter = new Adapter(mCtx, dataStr);
		//
		btn = getNextButton();
		btn.setText("原始");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListView listView = new ListView(mCtx);
				listView.setAdapter(adapter);
				dialog(listView);
			}
		});
		//
		btn = getNextButton();
		btn.setText("初始化");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListView listView = new ListView(mCtx);
				QListViewUtil.init(listView);
				listView.setAdapter(adapter);
				dialog(listView);
			}
		});
		//
		btn = getNextButton();
		btn.setText("自动加载更多");
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListView listView = new ListView(mCtx);
				new QAutoLoadMoreListViewFilter(mCtx, listView, adapter, null, 
						new QAutoLoadMoreListViewFilter.OnLoadMoreListener() {

					@Override
					public boolean onBackground() {
						SystemClock.sleep(1500);
						if(dataStr.size() < 20){
							dataStr.addAll(dataStr);
							return true;
						}
						return false;
					}
					@Override
					public void onStart() {}
					@Override
					public void onFinish() {}
					@Override
					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {}
				});
				dialog(listView);
			}
		});
		//
		btn = getNextButton();
		btn.setText("下拉刷新");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final QPullToRefreshListView listPullToRefresh =  new QPullToRefreshListView(mCtx, adapter,
						new QPullToRefreshListView.OnRefreshListener() {
					@Override
					public void onStart() { }
					@Override
					public void onFinish() {}
					@Override
					public void onBackground() {
						dataStr.add("下拉");
						SystemClock.sleep(2000);
					}
				});
				dialog(listPullToRefresh);
			}
		});
	}

	
	class Adapter extends QAdapterBase<String> {

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
