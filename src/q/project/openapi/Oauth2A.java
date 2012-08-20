package q.project.openapi;
/*
import java.io.IOException;
import java.util.Date;
import q.project.QProjectItem;
import q.util.QUtil;
import q.util.a.view.QOauth2;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Oauth2A extends QProjectItem {
	
	private int type;
	
	@Override
	protected void onInit() {
		this.type = getIntent().getIntExtra(QOauth2.EXTRA_TYPE, 0);
		//
		pd = new ProgressDialog(this);
		//
		tv1 = new TextView(this);
		layout.addView(tv1, 0);
		//
		tv2 = new TextView(this);
		layout.addView(tv2, 1);
		//
		tv3 = new TextView(this);
		layout.addView(tv3, 2);
		//
		btn = getNextButton();
		btn.setText("授权");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(ctx, QOauth2.class).putExtra(QOauth2.EXTRA_TYPE, type));
			}
		});
		//
		btn = getNextButton();
		btn.setText("清除授权");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				QOauth2.removeToken(ctx, type);
				onStart();
			}
		});
		//
		btn = getNextButton();
		btn.setText("用户信息");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				new Thread(){
					public void run() {
						try {
							handler.sendMessage(handler.obtainMessage(MSG_REQUEST_SUCCESS, queryUserInfo()));
						} catch (IOException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_ERROR);
						} catch (QOauth2.UnAuthException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_UNAUTH);
						}
					}
				}.start();
			}
		});
		//
		btn = getNextButton();
		btn.setText("发文字");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				new Thread(){
					public void run() {
						try {
							handler.sendMessage(handler.obtainMessage(MSG_REQUEST_SUCCESS, queryPostText()));
						} catch (IOException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_ERROR);
						} catch (QOauth2.UnAuthException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_UNAUTH);
						}
					}
				}.start();
			}
		});
		//
		btn = getNextButton();
		btn.setText("发图片");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				new Thread(){
					public void run() {
						try {
							handler.sendMessage(handler.obtainMessage(MSG_REQUEST_SUCCESS, queryPostPic()));
						} catch (IOException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_ERROR);
						} catch (QOauth2.UnAuthException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_UNAUTH);
						}
					}
				}.start();
			}
		});
		//
		btn = getNextButton();
		btn.setText("@好友列表");
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pd.show();
				new Thread(){
					public void run() {
						try {
							handler.sendMessage(handler.obtainMessage(MSG_REQUEST_SUCCESS, queryFriends()));
						} catch (IOException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_ERROR);
						} catch (QOauth2.UnAuthException e) {
							e.printStackTrace();
							handler.sendEmptyMessage(MSG_REQUEST_UNAUTH);
						}
					}
				}.start();
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		long expire = QOauth2.getExpireTime(this, type);
		tv1.setText("expire: " + expire + "(" + QUtil.Date_formatDateToStr(new Date(expire), "yyyy-MM-dd HH:mm:ss") + ")");		
		tv2.setText("access token: " + QOauth2.getToken(this, type));
		switch(type){
		case QOauth2.TYPE_QQ_WEIBO: tv3.setText("openId: " + QOauth2.qQQWeiboOpenId(this)); break;
		case QOauth2.TYPE_QZONE: tv3.setText("openId: " + QOauth2.qQzoneOpenId(this)); break;
		case QOauth2.TYPE_SINA_WEIBO: tv3.setText("uid: " + QOauth2.qSinaWeiboUid(this)); break;
		}
	}
	
	protected void onRequestSuccess(String data){
		pd.dismiss();
		new AlertDialog.Builder(this).setMessage(data).show();
	}
	
	protected void onRequestError(){
		pd.dismiss();
		new AlertDialog.Builder(this).setMessage("发送失败").show();
	}

	protected void onRequestUnAuth(){
		pd.dismiss();
		new AlertDialog.Builder(this).setMessage("未授权").show();
	}
	
	//
	private Dialog pd;
	private TextView tv1, tv2, tv3;
	//	
	protected static final int MSG_REQUEST_SUCCESS = 1;
	protected static final int MSG_REQUEST_ERROR = 2;
	protected static final int MSG_REQUEST_UNAUTH = 3;
	protected Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case MSG_REQUEST_SUCCESS:
				onRequestSuccess((String)msg.obj);
				break;
			case MSG_REQUEST_ERROR:
				onRequestError();
				break;
			case MSG_REQUEST_UNAUTH:
				onRequestUnAuth();
				break;
			}
		};
	};
	
	private String queryUserInfo() throws IOException, QOauth2.UnAuthException{
		switch(type){
		case QOauth2.TYPE_QQ_WEIBO: return QOauth2.qQQWeiboUserInfo(this);
		case QOauth2.TYPE_QZONE: return QOauth2.qQzoneUserInfo(this);
		case QOauth2.TYPE_RENREN: return QOauth2.qRenrenUserInfo(this);
		case QOauth2.TYPE_SINA_WEIBO: return QOauth2.qSinaWeiboUserInfo(this);
		}
		return null;
	}
	
	private String queryPostText() throws IOException, QOauth2.UnAuthException{
		String text = "测试gogo";
		switch(type){
		case QOauth2.TYPE_QQ_WEIBO: return QOauth2.qQQWeiboPostText(this, text);
		case QOauth2.TYPE_QZONE: return QOauth2.qQzonePostText(this, text);
		case QOauth2.TYPE_RENREN: return QOauth2.qRenrenPostText(this, text);
		case QOauth2.TYPE_SINA_WEIBO: return QOauth2.qSinaWeiboPostText(this, text, "23.126034", "113.268938");
		}
		return null;
	}
	
	private String queryPostPic() throws IOException, QOauth2.UnAuthException{
		String text = "图片";
		String pic = "/sdcard/DCIM/Camera/test.jpg";
		switch(type){
		case QOauth2.TYPE_QQ_WEIBO: return QOauth2.qQQWeiboPostPic(this, text, pic);
		case QOauth2.TYPE_QZONE: return QOauth2.qQzonePostPic(this, text, pic);
		case QOauth2.TYPE_RENREN: return QOauth2.qRenrenPostPic(this, text, pic);
		case QOauth2.TYPE_SINA_WEIBO: return QOauth2.qSinaWeiboPostPic(this, text, pic, "23.126034", "113.268938");
		}
		return null;
	}

	private String queryFriends() throws IOException, QOauth2.UnAuthException{
		switch(type){
		case QOauth2.TYPE_QQ_WEIBO: return QOauth2.qQQWeiboFriends(this);
		case QOauth2.TYPE_QZONE: break;
		case QOauth2.TYPE_RENREN: return QOauth2.qRenrenFriends(this);
		case QOauth2.TYPE_SINA_WEIBO: return QOauth2.qSinaWeiboFriends(this);
		}
		return null;
	}
}
*/