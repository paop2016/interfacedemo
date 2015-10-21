package wang.interfacedemo;

import wang.tools.Test_DataManage;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity{
	
	EditText account;
	EditText oldPassword;
	EditText newPassword;
	EditText confirmNewPassword;
	Button confirmButton;
	String a;
	String b;
	String c;
	String d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.changepassword_layout);
		
		account=(EditText) findViewById(R.id.changepasswordaccountId);
		oldPassword=(EditText) findViewById(R.id.changepasswordoldmimaId);
		newPassword=(EditText) findViewById(R.id.changepasswordnewmimaId);
		confirmNewPassword=(EditText) findViewById(R.id.changepasswordconfirmmimaId);
		confirmButton=(Button) findViewById(R.id.changepasswordbuttonId);
		
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				a=account.getText().toString();
				b=oldPassword.getText().toString();
				c=newPassword.getText().toString();
				d=confirmNewPassword.getText().toString();
				if(!(TextUtils.isEmpty(a)||TextUtils.isEmpty(b)))
					if(!(TextUtils.isEmpty(c)||TextUtils.isEmpty(d)))
						if(c.equals(d)){
							Test_DataManage dataManage=new Test_DataManage(ChangePasswordActivity.this);
							boolean flag=dataManage.passwordChange(a,b,c);
							if(flag){
								Intent intent=new Intent(ChangePasswordActivity.this,MainActivity.class);
								intent.putExtra("jay1",a);
								intent.putExtra("chou1",c);
								startActivity(intent);
							}
						}
						else
							Toast.makeText(ChangePasswordActivity.this, "两次密码不同", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ChangePasswordActivity.this, "请输入账号和旧密码", Toast.LENGTH_SHORT).show();
			}
		});
	}
	long time;
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
    	if((System.currentTimeMillis()-time)>2000){
    		Toast.makeText(this,"再按一次退出修改密码", Toast.LENGTH_SHORT).show();
    		time=System.currentTimeMillis();
    	}
    	else{
    		Intent intent=new Intent(ChangePasswordActivity.this,MainActivity.class);
    		startActivity(intent); 
    	}
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		finish();
	}
}
