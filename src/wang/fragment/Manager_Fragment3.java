package wang.fragment;

import java.util.ArrayList;
import java.util.List;

import wang.interfacedemo.ManagerViewActivity;
import wang.interfacedemo.R;
import wang.interfacedemo.R.id;
import wang.interfacedemo.R.layout;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import wang.tools.TitleLayout;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class Manager_Fragment3 extends Fragment{
	Spinner spinner;
	ListView listView;
	TextView refresh;
	Button button;
	TitleLayout titleLayout;
	ArrayList<Test_User> list;
	Test_DataManage dataManage;
	Test_User u;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.waitcheckuser_layout, null);
		dataManage=new Test_DataManage(getActivity());
		u=dataManage.getUser(((ManagerViewActivity)getActivity()).account);
		spinner = (Spinner) view.findViewById(R.id.managerseeSpinner1Id);
		listView = (ListView) view.findViewById(R.id.managerseeListView1Id);
		refresh=(TextView) view.findViewById(R.id.refreshButton1Id);
		titleLayout=(TitleLayout) view.findViewById(R.id.titleId);
		titleLayout.setVisibility(View.GONE);
		button=(Button) view.findViewById(R.id.rejectedUserId);
		button.setText("删除全部");
		List<String> list = new ArrayList<String>();
		list.add("全部被拒绝用户");
		list.add("学生");
		if(u.getSchool().equals("科大"))
			list.add("班长");
		list.add("老师");
		list.add("管理员");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					listLoad(u.getSchool(),"全部");
					break;
				case 1:
					listLoad(u.getSchool(),"学生");
					break;
				case 2:
					if (u.getSchool().equals("科大")) {
						listLoad("科大", "班长");
					} else {
						listLoad(u.getSchool(), "老师");
					}
					break;
				case 3:
					if (u.getSchool().equals("科大")) {
						listLoad("科大", "老师");
					} else {
						listLoad(u.getSchool(), "管理员");
					}
					break;
				case 4:
					listLoad("科大", "管理员");
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder=new Builder(getActivity());
				builder.setMessage("确定要删除 全部记录吗");
				builder.setCancelable(true);
				builder.setPositiveButton("确定删除",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog,int which) {
						// TODO Auto-generated method stub
						dataManage.delateAll("西电");
						refresh();
						Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
				builder.show();
			}
		});
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				refresh();
				Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
			}
		});
		return view;
	} 
	private void listLoad(String school,String identity) {
		list=dataManage.findBySchoolIdentityAndStatus(school, identity, "refuse");
		ManagerAdapter3 managerAdapter3 = new ManagerAdapter3(getActivity(),
				R.layout.waitcheckuser_item_layout, list);
		listView.setAdapter(managerAdapter3);
	}
	public class ManagerAdapter3 extends ArrayAdapter<Test_User>{

		int resourceId;
		Test_DataManage dataManage=new Test_DataManage(getContext());
		public ManagerAdapter3(Context context, int textViewResourceId,
				List<Test_User> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.resourceId=textViewResourceId;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			final Test_User u=getItem(position);
			View view;
			ViewHolder viewHolder;
			if(convertView==null){
				view=LayoutInflater.from(getContext()).inflate(resourceId, null);
				viewHolder=new ViewHolder();
				viewHolder.nameView=(TextView) view.findViewById(R.id.allusernameId);
				viewHolder.identityView=(TextView) view.findViewById(R.id.alluseridentityId);
				viewHolder.gonghaoView=(TextView) view.findViewById(R.id.gongxuehao1Id);
				viewHolder.zhengonghaoView=(TextView) view.findViewById(R.id.gongxuehaozhende1Id);
				viewHolder.shenfenView=(TextView) view.findViewById(R.id.shenfenzhengId);
				viewHolder.studentclass=(TextView) view.findViewById(R.id.banji1Id);
				viewHolder.zhendestudentclass=(TextView) view.findViewById(R.id.banjizhende1Id);
				viewHolder.button1=(Button) view.findViewById(R.id.waituserpassId);
				viewHolder.button2=(Button) view.findViewById(R.id.waituserrefuseId);
				viewHolder.button=(Button) view.findViewById(R.id.delateId);
				view.setTag(viewHolder);
			}
			else{
				view=convertView;
				viewHolder=(ViewHolder) view.getTag();
			}
				viewHolder.button1.setVisibility(View.GONE);
				viewHolder.button2.setVisibility(View.GONE);
			if(u.getIdentity().equals("学生")){
				viewHolder.gonghaoView.setText("学号");
				viewHolder.studentclass.setText("班级");
				viewHolder.zhengonghaoView.setText(u.getXuehao());
			}
			if(u.getIdentity().equals("老师")){
				viewHolder.gonghaoView.setText("工号");
				viewHolder.studentclass.setVisibility(View.GONE);
				viewHolder.zhendestudentclass.setVisibility(View.GONE);
				viewHolder.zhengonghaoView.setText(u.getGonghao());
			}
			if(u.getIdentity().equals("班长")){
				viewHolder.gonghaoView.setText("认证号");
				viewHolder.studentclass.setText("班级");
				viewHolder.zhengonghaoView.setText(u.getMoniterId());
			}
			if(u.getIdentity().equals("管理员")){
				viewHolder.gonghaoView.setText("认证号");
				viewHolder.studentclass.setVisibility(View.GONE);
				viewHolder.zhendestudentclass.setVisibility(View.GONE);
				viewHolder.zhengonghaoView.setText(u.getManagerId());
			}
			viewHolder.nameView.setText(u.getName());
			viewHolder.identityView.setText(u.getIdentity());
			viewHolder.shenfenView.setText(u.getShenfenzheng());
			viewHolder.zhendestudentclass.setText(u.getBanji());
			viewHolder.button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AlertDialog.Builder aBuilder=new Builder(getContext());
					aBuilder.setTitle("attention");
					aBuilder.setMessage("确定删除 "+u.getName()+" 这条记录吗?");
					aBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					aBuilder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dataManage.delate(u);
							refresh();
							Toast.makeText(getActivity(), "已删除", Toast.LENGTH_SHORT).show();
						}
					});
					aBuilder.show();
				}
			});
					
			return view;
		}
		private class ViewHolder{
			TextView nameView;
			TextView identityView;
			TextView gonghaoView;
			TextView zhengonghaoView;
			TextView shenfenView;
			TextView studentclass;
			TextView zhendestudentclass;
			Button button;
			Button button1;
			Button button2;
		}
	}
	public void refresh(){
		int a=spinner.getSelectedItemPosition();
		switch (a) {
		case 0:
			listLoad(u.getSchool(),"全部");
			break;
		case 1:
			listLoad(u.getSchool(),"学生");
			break;
		case 2:
			if (u.getSchool().equals("科大")) {
				listLoad("科大", "班长");
			} else {
				listLoad(u.getSchool(), "老师");
			}
			break;
		case 3:
			if (u.getSchool().equals("科大")) {
				listLoad("科大", "老师");
			} else {
				listLoad(u.getSchool(), "管理员");
			}
			break;
		case 4:
			listLoad("科大", "管理员");
		}
	}
}
