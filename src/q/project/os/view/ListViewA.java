package q.project.os.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import q.project.QProjectItem;
import qv.adapter.QAdapterBase;
import qv.list.EndlessListViewHelper.OnEndlessListViewListener;
import qv.list.ListViewUtil;
import qv.list.QPullToRefreshListView;

public class ListViewA extends QProjectItem {
	
	List<String> dataStr = new ArrayList<String>();
	Adapter adapter;

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
		adapter = new Adapter(mCtx, dataStr);
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
				ListViewUtil.init(listView);
				listView.setAdapter(adapter);
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
	
	class Adapter extends QAdapterBase<String, Adapter.Holder> {

		public Adapter(Context ctx, List<String> datas) {
			super(ctx, datas);
		}

		@Override
		protected int getLayoutId() {
			return android.R.layout.simple_list_item_1;
		}

		@Override
		protected Holder getHolder(View v) {
			Holder h = new Holder();
			h.tv = (TextView)v.findViewById(android.R.id.text1);
			return h;
		}

		@Override
		protected void initItem(int position, String data, Holder h) {
			h.tv.setText(data);
		}
		
		class Holder {
			TextView tv;
		}

		
	}


	
}
