package wang.interfacedemo;


import java.io.StringReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import wang.tools.MyDatabaseHelper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Test_XML_Pull extends Activity {
	Button button;
	MyDatabaseHelper myDatabaseHelper;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_xml_pull);
		
//		myDatabaseHelper=new MyDatabaseHelper(context, name, factory, version);≤‚ ‘¥˙¬Î √ª”–context
		db=myDatabaseHelper.getWritableDatabase();
		button=(Button) findViewById(R.id.buttontest);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendRequestWithHttpClient();
			}
		});
	}
	private void sendRequestWithHttpClient() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				HttpClient httpClient=new DefaultHttpClient();
				HttpGet httpGet=new HttpGet("http://1.wwwxml.sinaapp.com/xml.xml");
				try {
					HttpResponse httpResponse=httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity=httpResponse.getEntity();
						String response=EntityUtils.toString(entity, "UTF-8");
						parseXMLWithPull(response);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
	private void parseXMLWithPull(String response) {
		// TODO Auto-generated method stub
		try {
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser=factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response));
			int eventType=xmlPullParser.getEventType();
			String name="";
			String xuehao="";
			while(eventType!=XmlPullParser.END_DOCUMENT){
				String nodeName=xmlPullParser.getName();
				switch(eventType){
				case XmlPullParser.START_TAG:
					if(nodeName.equals("name")){
						name=xmlPullParser.nextText();
					}
					else if(nodeName.equals("xuehao")){
						xuehao=xmlPullParser.nextText();
					}
					break;
				case XmlPullParser.END_TAG:
					if(nodeName.equals("app")){
						ContentValues cv=new ContentValues();
						cv.put("name", name);
						cv.put("xuehao", xuehao);
						db.insert("Chengji",null, cv);
					}
					break;
				default:
					break;
				}
				eventType=xmlPullParser.next();	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
