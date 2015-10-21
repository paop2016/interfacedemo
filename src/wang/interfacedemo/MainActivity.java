package wang.interfacedemo;





import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.Activity;
import android.content.Context;
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


public class MainActivity extends Activity {

	Button LoginButton;
	Button registerButton; 
	TextView textView;
	TextView textView1;
	EditText zhanghao;
	EditText mima;
	String a1;
	String b1;
	String userAccount;
	String userPassword;
	Test_DataManage dataManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        
        
        dataManage=new Test_DataManage(this);
        dataManage.vip("西电", "1", "1");
        dataManage.vip("科大", "2", "2");
        dataManage.vip("交大", "3", "3");
        LoginButton=(Button) findViewById(R.id.xdengrubuttonId);
        registerButton=(Button) findViewById(R.id.zhucebuttonId);
        textView=(TextView) findViewById(R.id.guanyuId);
        textView1=(TextView) findViewById(R.id.changeButtonId);
        zhanghao= (EditText) findViewById(R.id.zhanghaoId);
        mima=(EditText) findViewById(R.id.mimaId);
        textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,AboutUsActivity.class);
				startActivity(intent);
			}
		});
        textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,ChangePasswordActivity.class);
				startActivity(intent);
			}
		});
        LoginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

		        userAccount=zhanghao.getText().toString();
		        userPassword=mima.getText().toString();
		        Test_User u=dataManage.accountPasswordCheck(userAccount, userPassword);
		        if(u==null)
		        	Toast.makeText(MainActivity.this, "账号或密码错误", 2000).show();
		        else{
		        	if(u.getIdentity().equals("学生")){
		        		StudentViewActivity studentViewActivity = new StudentViewActivity();
		        		studentViewActivity.start(MainActivity.this,u.getAccount(),null,null,1);
		        	}
		        	else{
		        		Intent intent=new Intent();
		        		intent.putExtra("jay", u.getAccount());
			        	if(u.getIdentity().equals("班长")){
			        		intent.setClass(MainActivity.this, StudentViewActivity.class);
			        	}
			        	else if(u.getIdentity().equals("老师")){
			        		intent.setClass(MainActivity.this, TeacherViewActivity.class);
			        	}
			        	else if(u.getIdentity().equals("管理员")){
			        		intent.setClass(MainActivity.this, ManagerViewActivity.class);
			        	}
			        	startActivity(intent);
		        	}
		        }
			}
		});
        registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	finish();
    }
    long time=0;
    @Override
    public void onBackPressed() {
    	// TODO Auto-generated method stub
    	if((System.currentTimeMillis()-time)>2000){
    		Toast.makeText(this,"再按一次退出程序", Toast.LENGTH_SHORT).show();
    		time=System.currentTimeMillis();
    	}
    	else
    		finish();
    }


}
