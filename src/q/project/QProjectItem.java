package q.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public abstract class QProjectItem extends Activity {
	
	protected LinearLayout layout;
	protected Button btn;
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10,btn11, btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20;
	protected Context ctx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.q_project);
		ctx = this;
		//
		layout = (LinearLayout)findViewById(R.id.layout);
		btn1 = (Button)findViewById(R.id.btn1);
		btn2 = (Button)findViewById(R.id.btn2);
		btn3 = (Button)findViewById(R.id.btn3);
		btn4 = (Button)findViewById(R.id.btn4);
		btn5 = (Button)findViewById(R.id.btn5);
		btn6 = (Button)findViewById(R.id.btn6);
		btn7 = (Button)findViewById(R.id.btn7);
		btn8 = (Button)findViewById(R.id.btn8);
		btn9 = (Button)findViewById(R.id.btn9);
		btn10 = (Button)findViewById(R.id.btn10);
		btn11 = (Button)findViewById(R.id.btn11);
		btn12 = (Button)findViewById(R.id.btn12);
		btn13 = (Button)findViewById(R.id.btn13);
		btn14 = (Button)findViewById(R.id.btn14);
		btn15 = (Button)findViewById(R.id.btn15);
		btn16 = (Button)findViewById(R.id.btn16);
		btn17 = (Button)findViewById(R.id.btn17);
		btn18 = (Button)findViewById(R.id.btn18);
		btn19 = (Button)findViewById(R.id.btn19);
		btn20 = (Button)findViewById(R.id.btn20);
		//
		onInit();
	}
	
	protected abstract void onInit();
	
	private int i = 0;

	protected Button getNextButton(){
		i++;
		switch(i){
		case 1: return btn1;
		case 2: return btn2;
		case 3: return btn3;
		case 4: return btn4;
		case 5: return btn5;
		case 6: return btn6;
		case 7: return btn7;
		case 8: return btn8;
		case 9: return btn9;
		case 10: return btn10;
		case 11: return btn11;
		case 12: return btn12;
		case 13: return btn13;
		case 14: return btn14;
		case 15: return btn15;
		case 16: return btn16;
		case 17: return btn17;
		case 18: return btn18;
		case 19: return btn19;
		case 20: return btn20;
		}
		return null;
	}
	
	protected void dialog(View v){
		new AlertDialog.Builder(this).setView(v).show();
	}
}
