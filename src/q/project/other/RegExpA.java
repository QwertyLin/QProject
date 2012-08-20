package q.project.other;

import java.util.regex.Matcher;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import q.project.QProjectItem;
import q.util.QRegExp;

public class RegExpA extends QProjectItem {

	TextView tvOld, tvNew;
	
	@Override
	protected void onInit() {
		tvOld = new TextView(this);
		layout.addView(tvOld, 0);
		//
		tvNew = new TextView(this);
		layout.addView(tvNew, 1);
		//
		btn = getNextButton();
		btn.setText("匹配#abc#");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = "gogogog#abc# lsdf s #J#";
				setText(str, QRegExp.sharp2sharp(str));
			}
		});
	}
	
	private void setText(String strOld, Matcher m){
		tvOld.setText(strOld);
		StringBuffer sb = new StringBuffer();
		while(m.find()){
			sb.append(m.group() + ", ");
		}
		tvNew.setText(sb.toString());
	}

}
