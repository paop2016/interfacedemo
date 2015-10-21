package wang.interfacedemo;

import wang.fragment.Manager_Fragment1;
import wang.fragment.Manager_Fragment2;
import wang.fragment.Manager_Fragment3;
import wang.tools.Test_DataManage;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerViewActivity extends Activity{
	Fragment[] fm_pages=new Fragment[3];
	FragmentManager fragmentManager;
	TextView textView1;
	TextView textView2;
	TextView textView3;
	Button button1;
	Button button2;
	Button button3;
	Manager_Fragment1 fragment1;
	Manager_Fragment2 fragment2;
	Manager_Fragment3 fragment3;
	public String account;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.manager_layout);
		
		Intent intent=getIntent();
		account=intent.getStringExtra("jay");
		textView1=(TextView) findViewById(R.id.flagTest1Id);
		textView2=(TextView) findViewById(R.id.flagTest2Id);
		textView3=(TextView) findViewById(R.id.flagTest3Id);
		button1=(Button) findViewById(R.id.button1Id);
		button2=(Button) findViewById(R.id.button2Id);
		button3=(Button) findViewById(R.id.button3Id);
        TextView backButton=(TextView) findViewById(R.id.backButtonId);
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        titleView.setText("请谨慎操作");
        backButton.setVisibility(View.GONE);
        fragment1=new Manager_Fragment1();
        fragment2=new Manager_Fragment2();
        fragment3=new Manager_Fragment3();
        fm_pages[0]=fragment1;
        fm_pages[1]=fragment2;
        fm_pages[2]=fragment3;
        fragmentManager=getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fm_pages.length; i++) {
			fragmentTransaction.add(R.id.FragmentHolderId, fm_pages[i],""+i);
		}
        fragmentTransaction.commit();
        showFragment(0);
		textView1.setBackgroundColor(0xFFED787A);
		textView2.setBackgroundColor(0xFFFFFF);
		textView3.setBackgroundColor(0xFFFFFF);

        button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFragment(0);
				textView1.setBackgroundColor(0xFFED787A);
				textView2.setBackgroundColor(0xFFFFFF);
				textView3.setBackgroundColor(0xFFFFFF);
				fragment1.refresh();
			}
		});
        button2.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		showFragment(1);
				textView1.setBackgroundColor(0xFFFFFF);
				textView2.setBackgroundColor(0xFFED787A);
				textView3.setBackgroundColor(0xFFFFFF);
				fragment2.refresh();
        	}
        });
        button3.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		showFragment(2);
				textView1.setBackgroundColor(0xFFFFFF);
				textView2.setBackgroundColor(0xFFFFFF);
				textView3.setBackgroundColor(0xFFED787A);
				fragment3.refresh();
        	}
        });
	}
	private void showFragment(int position) {
		// TODO Auto-generated method stub
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        for (int i = 0; i < fm_pages.length; i++) {
			if(i==position)
				fragmentTransaction.show(fm_pages[i]);
			else
				fragmentTransaction.hide(fm_pages[i]);
		}
        fragmentTransaction.commit();
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
    public String getAccount(){
    	return account; 
    }
}
