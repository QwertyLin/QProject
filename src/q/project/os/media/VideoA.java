package q.project.os.media;

import q.project.QProjectItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoA extends QProjectItem {

	@Override
	protected void onInit() {
		final VideoView vv = new VideoView(this);
		vv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 300));
		layout.addView(vv, 0);
		//
		btn = getNextButton();
		btn.setText("播放");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vv.setVideoPath("/sdcard/video/test.mp4");
				vv.start();
			}
		});
		//
		btn = getNextButton();
		btn.setText("控制条");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				vv.setMediaController(new MediaController(ctx));
			}
		});
	}
}
