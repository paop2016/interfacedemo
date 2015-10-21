package wang.fragment;

import java.util.ArrayList;
import java.util.List;

import wang.interfacedemo.ManagerViewActivity;
import wang.interfacedemo.R;
import wang.interfacedemo.R.id;
import wang.interfacedemo.R.layout;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
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

public class Manager_Fragment1 extends Fragment{
	Spinner spinner;
	ListView listView;
	TextView refresh;
	Test_DataManage dataManage;
	Test_User u;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.managealluser_layout, null);
		spinner=(Spinner)view.findViewById(R.id.managerseeSpinnerId);
		listView=(ListView)view.findViewById(R.id.managerseeListViewId);
		refresh=(TextView) view.findViewById(R.id.refreshButtonId);
		dataManage=new Test_DataManage(getActivity());
		u=dataManage.getUser(((ManagerViewActivity)getActivity()).account);
		List<String> list=new ArrayList<String>();
		list.add("ȫ���û�");
		list.add("ѧ��");
		if(u.getSchool().equals("�ƴ�"))
			list.add("�೤");
		list.add("��ʦ");
		list.add("����Ա");
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					listLoad(u.getSchool(), "ȫ��");
					break;
				case 1:
					listLoad(u.getSchool(), "ѧ��");
					break;
				case 2:
					if (u.getSchool().equals("�ƴ�")) {
						listLoad("�ƴ�", "�೤");
					} else {
						listLoad(u.getSchool(), "��ʦ");
					}
					break;
				case 3:
					if (u.getSchool().equals("�ƴ�")) {
						listLoad("�ƴ�", "��ʦ");
					} else {
						listLoad(u.getSchool(), "����Ա");
					}
					break;
				case 4:
					listLoad("�ƴ�", "����Ա");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ˢ�³ɹ�", Toast.LENGTH_SHORT).show();
				refresh();
			}
		});
		return view;
	} 
	private void listLoad(String school,String identity){
		ArrayList<Test_User> list=new ArrayList<Test_User>();
		list=dataManage.findBySchoolIdentityAndStatus(school, identity, "pass");
		ManagerAdapter1 adapter1=new ManagerAdapter1(getActivity(), R.layout.managealluser_item_layout,list );
		listView.setAdapter(adapter1);
	}

	public class ManagerAdapter1 extends ArrayAdapter<Test_User>{

		int resourceId;
//		Test_User u;
		public ManagerAdapter1(Context context, int textViewResourceId,
				List<Test_User> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.resourceId=textViewResourceId;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			final Test_User u=getItem(position);
			ViewHolder viewHolder;
			if(convertView==null){
				view=View.inflate(getContext(),resourceId, null);
				viewHolder=new ViewHolder();
				viewHolder.nameView=(TextView) view.findViewById(R.id.allusernameId);
				viewHolder.identityView=(TextView) view.findViewById(R.id.alluseridentityId);
				viewHolder.gonghaoView=(TextView) view.findViewById(R.id.gongxuehaoId);
				viewHolder.zhengonghaoView=(TextView) view.findViewById(R.id.gongxuehaozhendeId);
				viewHolder.banjiView=(TextView) view.findViewById(R.id.alluserbanjiId);
				viewHolder.zhenbanjiView=(TextView) view.findViewById(R.id.alluserbanjizhendeId);
				viewHolder.button=(Button) view.findViewById(R.id.alluserButtonId);
				view.setTag(viewHolder);
			}
			else{
				view=convertView;
				viewHolder=(ViewHolder) view.getTag();
			}
			viewHolder.nameView.setText(u.getName());
			viewHolder.identityView.setText(u.getIdentity());
			Log.v("jay",u.getName());
			if(u.getIdentity().equals("ѧ��")){
				viewHolder.zhengonghaoView.setText(u.getXuehao());
				viewHolder.gonghaoView.setText("ѧ��:");
				viewHolder.zhenbanjiView.setText(u.getBanji());
				viewHolder.banjiView.setVisibility(View.VISIBLE);
				viewHolder.zhenbanjiView.setVisibility(View.VISIBLE);
			}
			if(u.getIdentity().equals("�೤")){
				viewHolder.zhengonghaoView.setText(u.getMoniterId());
				viewHolder.gonghaoView.setText("��֤��:");
				viewHolder.zhenbanjiView.setText(u.getBanji());
				viewHolder.banjiView.setVisibility(View.VISIBLE);
				viewHolder.zhenbanjiView.setVisibility(View.VISIBLE);
			}
			if(u.getIdentity().equals("��ʦ")){
				viewHolder.zhengonghaoView.setText(u.getGonghao());
				viewHolder.gonghaoView.setText("����:");
				viewHolder.banjiView.setVisibility(View.GONE);
				viewHolder.zhenbanjiView.setVisibility(View.GONE);
			}
			if(u.getIdentity().equals("����Ա")){
				viewHolder.zhengonghaoView.setText(u.getManagerId());
				viewHolder.gonghaoView.setText("��֤��:");
				viewHolder.button.setVisibility(View.GONE);
				viewHolder.banjiView.setVisibility(View.GONE);
				viewHolder.zhenbanjiView.setVisibility(View.GONE);
			}
			viewHolder.button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog();
				}

				private void dialog() {
					// TODO Auto-generated method stub
					AlertDialog.Builder builder=new Builder(getContext());
					builder.setMessage("ȷ��Ҫɾ�� "+u.getName()+" ����û���");
					builder.setCancelable(true);
					builder.setPositiveButton("ȷ��ɾ��",new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog,int which) {
							// TODO Auto-generated method stub
							dataManage.managerOperate(u, "refuse");
							refresh();
							Toast.makeText(getContext(), "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
						}
					});
					builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					});
					builder.show();
				}
			});
			return view;
		}
		class ViewHolder{
			TextView nameView;
			TextView identityView;
			TextView gonghaoView;
			TextView zhengonghaoView;
			TextView banjiView;
			TextView zhenbanjiView;
			Button button;
		}
	}
	public void refresh(){
		int a=spinner.getSelectedItemPosition();
		switch (a) {
		case 0:
			listLoad(u.getSchool(), "ȫ��");
			break;
		case 1:
			listLoad(u.getSchool(), "ѧ��");
			break;
		case 2:
			if (u.getSchool().equals("�ƴ�")) {
				listLoad("�ƴ�", "�೤");
			} else {
				listLoad(u.getSchool(), "��ʦ");
			}
			break;
		case 3:
			if (u.getSchool().equals("�ƴ�")) {
				listLoad("�ƴ�", "��ʦ");
			} else {
				listLoad(u.getSchool(), "����Ա");
			}
			break;
		case 4:
			listLoad("�ƴ�", "����Ա");
			break;
		}
	}
}
