package q.util.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * 可以使整个屏幕失去焦点的Dialog
 *
 */
class CoverDialog extends Dialog{
    private Context ctx;

    public CoverDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.ctx = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new FrameLayout(this.ctx));
    }
}
