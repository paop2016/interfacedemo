package wang.interfacedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wang.tools.Test_DataManage;
import wang.tools.Test_User;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class __FailDetailsActivity extends Activity{
	Test_DataManage dataManage;
	Test_User student;
	String account;
	String xuehao;
	String school;
	ListView listView;
	ArrayList<String> arr;
	int flag;
	List<Map<String,Object>> subjectList;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fail_details_layout);
        
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        TextView button=(TextView) findViewById(R.id.backButtonId);
        titleView.setText("´ý²¹¿¼Ñ§¿Æ");
        titleView.setTextColor(0xFFFA4245);
        
        listView=(ListView) findViewById(R.id.fail_details_listViewId);
        dataManage=new Test_DataManage(this);
        account=getIntent().getStringExtra("jay");
        flag=getIntent().getIntExtra("flag", 4);
        xuehao=account==null?getIntent().getStringExtra("xuehao"):dataManage.getUser(account).getXuehao();
        school=account==null?getIntent().getStringExtra("school"):dataManage.getUser(account).getSchool();
        student=dataManage.getStudentByXuehao(school, xuehao);
        arr=student.getFailSubject();
        subjectList=new ArrayList<Map<String,Object>>();
        dataload(school,arr);
        SimpleAdapter adapter=new SimpleAdapter(this,subjectList, R.layout.fail_detail_item_layout,new String[]{"Subject","Time","Credit"},new int[]{R.id.subjectId,R.id.timeId,R.id.xuefenId});
        listView.setAdapter(adapter);
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
					
			}
		});
	}
	private void dataload(String school, ArrayList<String> arr) {
		// TODO Auto-generated method stub
		Iterator<String> it=arr.iterator();
		while(it.hasNext()){
			String subject=it.next();
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("Subject", subject);
			map.put("Time", dataManage.getTime(school, subject));
			map.put("Credit", dataManage.getCredit(school, subject));
			subjectList.add(map);
		}
	}
}