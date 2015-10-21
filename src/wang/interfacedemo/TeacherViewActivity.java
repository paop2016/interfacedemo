package wang.interfacedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TeacherViewActivity extends Activity{
	Button button;
	Button button1;
	EditText editText;
	String account;
	String input;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.teacher_layout);
		
        TextView backButton=(TextView) findViewById(R.id.backButtonId);
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        titleView.setText("老师辛苦了");
        backButton.setVisibility(View.GONE);
		
        account=getIntent().getStringExtra("jay");
		button=(Button) findViewById(R.id.sousuoId);
		button1=(Button) findViewById(R.id.dasousuoId);
		editText=(EditText) findViewById(R.id.inputId);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				input=editText.getText().toString();
				if(TextUtils.isEmpty(input))
					Toast.makeText(TeacherViewActivity.this, "搜索栏不能为空", 2000).show();
				else{
					Intent intent=new Intent(TeacherViewActivity.this,__TeacherSearchResultActivity.class);
					intent.putExtra("account", account);
					intent.putExtra("input", input);
					startActivity(intent);
				}
			}
		});
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(TeacherViewActivity.this,__TeacherSeeAllStudentActivity.class);
				intent.putExtra("account", account);
				startActivity(intent);
			}
		});
	}
	long time;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	if((System.currentTimeMillis()-time)>2000){
    		Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
    		time=System.currentTimeMillis();
    	}
    	else{
    		finish();
    	}
	}
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	finish();
    }
}
