package wang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wang.interfacedemo.R;
import wang.interfacedemo.__TeacherSearchResultActivity;
import wang.tools.Test_DataManage;
import wang.tools.Test_User;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Teacher_Fragment3 extends Fragment{
	ArrayList<Test_User> list;
	ListView listView;
	List<Map<String,String>> studentList;
	Test_DataManage dataManage;
	TextView textView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.techearmanyresultfragment, null);
		list=((__TeacherSearchResultActivity)getActivity()).getList();
		listView=(ListView) view.findViewById(R.id.resultlistId);
		textView=(TextView) view.findViewById(R.id.numId);
		textView.setText("找到"+list.size()+"个符合条件的学生");
		studentList=new ArrayList<Map<String,String>>();
		dataManage=new Test_DataManage(getActivity());
		dataload(list);
		SimpleAdapter adapter=new SimpleAdapter(getActivity(), studentList, R.layout.teacher_fragment3_item_layout, new String[]{"Name","Xuehao","Banji"}, new int[]{R.id.nameId,R.id.xuehaoId,R.id.banjiId});
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Test_User u=list.get(position);
				((__TeacherSearchResultActivity)getActivity()).fragment2.load(u);
				((__TeacherSearchResultActivity)getActivity()).showFragment(1);
			}
		});
		return view;
	}
	private void dataload(ArrayList<Test_User> list) {
		// TODO Auto-generated method stub
		Iterator<Test_User> it=list.iterator();
		while(it.hasNext()){
			Test_User u=it.next();
			HashMap<String,String> map=new HashMap<String, String>();
			map.put("Name", u.getName());
			map.put("Xuehao", u.getXuehao());
			map.put("Banji", u.getBanji());
			studentList.add(map);
		}
	}
}
