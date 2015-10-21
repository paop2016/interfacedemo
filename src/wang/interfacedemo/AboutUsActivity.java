package wang.interfacedemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends Activity{
	ListView listView;
	List<Map<String,Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.us_layout);
		
        TextView switchButton=(TextView) findViewById(R.id.switchButtonId);
        TextView backButton=(TextView) findViewById(R.id.backButtonId);
        TextView titleView=(TextView) findViewById(R.id.titleTextId);
        titleView.setText("我们");
        titleView.setTextColor(0xFFFA4245);
        switchButton.setVisibility(View.GONE);
        backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(AboutUsActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		listView=(ListView) findViewById(R.id.us_listViewId);
		list=new ArrayList<Map<String,Object>>();
		dataload();
		SimpleAdapter adapter=new SimpleAdapter(this,list,R.layout.fail_detail_item_layout,new String[]{"Title","People"},new int[]{R.id.subjectId,R.id.timeId});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==0){
					Toast.makeText(AboutUsActivity.this, "正在检查更新",Toast.LENGTH_SHORT).show();
					Toast.makeText(AboutUsActivity.this, "已经是最新版本",Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	private void dataload() {
		// TODO Auto-generated method stub
		HashMap map1=new HashMap<String,Object>();
		HashMap map2=new HashMap<String,Object>();
		HashMap map3=new HashMap<String,Object>();
		HashMap map4=new HashMap<String,Object>();
		HashMap map5=new HashMap<String,Object>();
		HashMap map6=new HashMap<String,Object>();
		map1.put("Title","版本");
		map1.put("People","V1.2  点击查看更新");
		map2.put("Title","开发团队");
		map2.put("People","王长通,李涵玉");
		map3.put("Title","运营团队");
		map3.put("People","老王,老李");
		map4.put("Title","联系方式");
		map4.put("People","15229341237");
		map5.put("Title","特别鸣谢");
		map5.put("People","感谢国家，感谢党");
		map6.put("Title","未完待续");
		map6.put("People","施工中..");
		list.add(map1);
		list.add(map2);
		list.add(map3);
		list.add(map4);
		list.add(map5);
		list.add(map6);
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent=new Intent(AboutUsActivity.this,MainActivity.class);
		startActivity(intent);
	}
}
