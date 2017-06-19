package com.ttu.ttu.TeleMail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ttu.ttu.JSONParser;
import com.ttu.ttu.R;
import com.ttu.ttu.login.Helper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class TeleMail2 extends Activity {
    ListView list;

    TextView Line1;
    TextView Line2;
    private String url;
    String title;
    String name;
    String tel;
    String email;
    ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();

    private static final String TAG_OS = "android";
    private static final String TITLE = "title";
    private static final String NAME = "name";
    private static final String TEL = "tel";
    private static final String EMAIL = "email";
    private Activity instance;
    JSONArray android = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tele_mail2);
        instance = this;
        oslist = new ArrayList<>();
        Bundle bundle =this.getIntent().getExtras();
        String loadurl = bundle.getString("code");
        url = Helper.getURL(instance) + "getTelEmailByUnit.php?unit="+loadurl;
        new JSONParse().execute();

    }
    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Line1 = (TextView)findViewById(R.id.text1);
            Line2 = (TextView)findViewById(R.id.text2);

            pDialog = new ProgressDialog(TeleMail2.this);
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

                    name = c.getString(NAME);
                    title = c.getString(TITLE);
                    tel = c.getString(TEL);
                    email = c.getString(EMAIL);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("LINE1", name+" "+title);
                    map.put("LINE2", "分機:"+tel+"       Email:"+email);

                    oslist.add(map);
                    list=(ListView)findViewById(R.id.telmail_list2);
                    ListAdapter adapter = new SimpleAdapter(TeleMail2.this, oslist,
                            R.layout.telemail2_detail,
                            new String[] { "LINE1","LINE2"}, new int[] {
                            R.id.text1,R.id.text2});
                    list.setAdapter(adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {

                            TextView LINE2 = (TextView) view.findViewById(R.id.text2);
                            String Line2 = LINE2.getText().toString();
                            Log.e("Telemail2", Line2);
                            String[] line2 = Line2.split("       ");
                            String[] phone_array = line2[0].split(":");
                            String[] email_array = line2[1].split(":");
                            final String phone_now = phone_array[1];
                            Log.e("Telemail2", phone_now);
                            final String email_now = email_array[1];
                            Log.e("Telemail2", email_now);

                            new AlertDialog.Builder(TeleMail2.this)
                                    .setTitle("提示")
                                    .setMessage("點選下列功能")
                                    .setPositiveButton("撥打電話", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intentDial = new Intent("android.intent.action.DIAL",Uri.parse("tel:0221822928,"+phone_now));
                                            startActivity(intentDial);                                        }
                                    })
                                    .setNegativeButton("發送郵件", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Uri uri = Uri.parse("mailto:" + email_now);
                                            Intent it = new Intent("android.intent.action.SENDTO", uri);
                                            startActivity(it);
                                        }


                                    })
                                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .show();
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
        Intent intent = new Intent(TeleMail2.this, TeleMail1.class);
        TeleMail2.this.startActivity(intent);
        TeleMail2.this.finish();

    }

}