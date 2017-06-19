package com.ttu.ttu.TeleMail;

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
import com.ttu.ttu.login.Helper;
import com.ttu.ttu.menu.Menu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TeleMail1 extends Activity {
    ListView list;

    TextView Line1;
    TextView Line2;
    private String url;
    private Activity instance;
    String code;
    String name;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    private static final String TAG_OS = "android";
    private static final String CODE = "code";
    private static final String NAME = "name";
    JSONArray android = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tele_mail);
        instance = this;
        oslist = new ArrayList<>();
        url = Helper.getURL(instance) + "getDepList.php";
        new JSONParse().execute();
    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Line1 = (TextView)findViewById(R.id.telemailtext1);
            Line2 = (TextView)findViewById(R.id.telemailtext2);

            pDialog = new ProgressDialog(TeleMail1.this);
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
                    code = c.getString(CODE);
                    name = c.getString(NAME);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("LINE1", name);
                    map.put("LINE2", code);

                    oslist.add(map);
                    list=(ListView)findViewById(R.id.telmail_list);
                    ListAdapter adapter = new SimpleAdapter(TeleMail1.this, oslist,
                            R.layout.telemail_detail,
                            new String[] { "LINE1","LINE2"}, new int[] {
                            R.id.telemailtext1,R.id.telemailtext2});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            TextView LINE2 = (TextView) view.findViewById(R.id.telemailtext2);
                            String Line2 = LINE2.getText().toString();

                            Intent intent = new Intent();
                            intent.setClass(TeleMail1.this, TeleMail2.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("code", Line2);
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
        Intent intent = new Intent(TeleMail1.this, Menu.class);
        TeleMail1.this.startActivity(intent);
        TeleMail1.this.finish();

    }
}