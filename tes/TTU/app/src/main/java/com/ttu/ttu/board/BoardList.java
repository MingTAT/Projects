package com.ttu.ttu.board;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ttu.ttu.JSONParser;
import com.ttu.ttu.R;
import com.ttu.ttu.menu.Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardList extends Activity {

    ListView list;
    TextView title;
    TextView date;
    TextView id;
    private Activity instance;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
    private static String url;
    private static final String TAG_OS = "android";
    private static final String BOARD_DATE = "date";
    private static final String BOARD_TITLE = "title";
    private static final String BOARD_ID = "boardno";
    JSONArray android = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_list);
        instance = this;
        url = "http://140.129.6.189/i4010/board/appboard.php/";
        oslist = new ArrayList<>();
        new JSONParse().execute();
    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            title = (TextView) findViewById(R.id.textView1);
            date = (TextView) findViewById(R.id.textView2);
            id = (TextView) findViewById(R.id.textView3);
            pDialog = new ProgressDialog(BoardList.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            JSONParser jParser = new JSONParser();
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                android = json.getJSONArray(TAG_OS);
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);
                    String title = c.getString(BOARD_TITLE);
                    String date = c.getString(BOARD_DATE);
                    String id = c.getString(BOARD_ID);
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(BOARD_TITLE, title);
                    map.put(BOARD_DATE, date);
                    map.put(BOARD_ID, id);
                    oslist.add(map);
                    list=(ListView)findViewById(R.id.memberList_id);
                    ListAdapter adapter = new SimpleAdapter(BoardList.this, oslist,
                            R.layout.board_detail,
                            new String[] { BOARD_TITLE,BOARD_DATE, BOARD_ID }, new int[] {
                            R.id.textView1,R.id.textView2, R.id.textView3});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            TextView BoardNo = (TextView) view.findViewById(R.id.textView3);
                            String boardno = BoardNo.getText().toString();
                            Intent intent = new Intent();
                            intent.setClass(BoardList.this, BrowserBoard.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("boardno", boardno);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BoardList.this, Menu.class);
        BoardList.this.startActivity(intent);
        BoardList.this.finish();
    }
}
