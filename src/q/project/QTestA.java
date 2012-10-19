package q.project;

import qv.web.oauth.OnWvOauthListener;
import qv.web.oauth.WvOauthEntity;
import qv.web.oauth.WvOauthHandleSinaWeibo;
import qv.web.oauth.WvOauthUtil;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class QTestA extends Activity implements OnWvOauthListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		System.out.println("QTestA");
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		WebView web = new WebView(this);
		setContentView(web);
		WvOauthUtil.init(new WvOauthEntity(0, this, web, new WvOauthHandleSinaWeibo()));
	}

	@Override
	public void onWvOauthLoadingFinish(WvOauthEntity en) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWvOauthAuthing(WvOauthEntity en) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWvOauthSuccess(WvOauthEntity en) {
		System.out.println(en.getToken().getToken());
	}

	@Override
	public void onWvOauthError(WvOauthEntity en) {
		// TODO Auto-generated method stub
		
	}

}
