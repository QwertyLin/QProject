package q.project.os.app;

import q.project.QProjectItem;
import q.util.QToStr;
import q.util.os.QPackageUtil;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

public class ManagerA extends QProjectItem {
	
	@Override
	protected void onInit() {
		btn = getNextButton();
		btn.setText("已安装软件");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(mCtx).setMessage(QToStr.toStr(mCtx, QPackageUtil.list(mCtx))).show();
			}
		});
	}
	
}
