package com.ttu.ttu.schedule;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ttu.ttu.JSONParser;
import com.ttu.ttu.R;
import com.ttu.ttu.login.Helper;
import com.ttu.ttu.menu.Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ScheduleList extends Activity {

    private Activity instance;
    private String url;
    TextView Line1;
    TextView Line2;
    ListView list;

    private static final String TAG_OS = "android";
    private static final String START = "start";
    private static final String START_W = "start_w";
    private static final String END = "end";
    static final String END_W = "end_w";
    private static final String CONTENT = "content";
    private static final String YEAR = "year";

    String year;
    String start;
    String start_w;
    String end;
    String end_w;
    String content;
    JSONArray jsonArray = null;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule);
        instance = this;
        url = Helper.getURL(instance)+"getScheduleByDate.php";
         Log.e("Schedule", url);
        new Item2_AT().execute("url",url);
    }

    class Item2_AT extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Line1 = (TextView) findViewById(R.id.scheduletext1);
            Line2 = (TextView) findViewById(R.id.scheduletext2);
            pDialog = new ProgressDialog(ScheduleList.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... param) {
            if(isCancelled()) return null;
            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            //此method是在doInBackground完成以後，才會呼叫的
            super.onPostExecute(json);
            pDialog.dismiss();
            try {
                jsonArray = json.getJSONArray(TAG_OS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    year = c.getString(YEAR);
                    start= c.getString(START);
                    start_w = c.getString(START_W);
                    end = c.getString(END);
                    end_w = c.getString(END_W);
                    content = c.getString(CONTENT);

                    HashMap<String, String> map = new HashMap<String, String>();
                    if(start.equals(end)){
                        map.put("LINE1", start + " (" + start_w + ")");
                    }else{
                        map.put("LINE1", start  +  "(" + start_w + ")-" + end + "(" + end_w + ")");
                    }

                    map.put("LINE2", content);
                    oslist.add(map);
                    list = (ListView) findViewById(R.id.schedulelistView);
                    ListAdapter adapter = new SimpleAdapter(ScheduleList.this, oslist,
                            R.layout.schedule_detail,
                            new String[]{"LINE1", "LINE2"}, new int[]{
                            R.id.scheduletext1, R.id.scheduletext2});
                    list.setAdapter(adapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScheduleList.this, Menu.class);
        ScheduleList.this.startActivity(intent);
        ScheduleList.this.finish();
    }

}