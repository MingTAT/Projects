package com.ttu.ttu.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ttu.ttu.Aboutttu;
import com.ttu.ttu.R;
import com.ttu.ttu.TeleMail.TeleMail1;
import com.ttu.ttu.Traffic;
import com.ttu.ttu.board.BoardList;
import com.ttu.ttu.login.Helper;
import com.ttu.ttu.login.Installation;
import com.ttu.ttu.login.MainActivity;
import com.ttu.ttu.schedule.ScheduleList;
import com.ttu.ttu.sign.SignActivity;

public class Menu  extends Activity {
    private Activity instance;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        instance = this;
        deviceId = Installation.id(instance);
        Helper.setSettingDeviceid(instance, deviceId);


        ImageButton board_button = (ImageButton)findViewById(R.id.board_button);
        board_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, BoardList.class);
                startActivity(intent);
                Menu.this.finish();
            }
        });

        ImageButton traffic = (ImageButton)findViewById(R.id.traffic);
        traffic.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, Traffic.class);

                startActivity(intent);
                Menu.this.finish();
            }
        });

        ImageButton aboutttu = (ImageButton)findViewById(R.id.aboutttu);
        aboutttu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, Aboutttu.class);

                startActivity(intent);
                Menu.this.finish();
            }
        });


        ImageButton cis = (ImageButton)findViewById(R.id.cis);
        cis.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View V){

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, MainActivity.class);
                startActivity(intent);
                Menu.this.finish();

            }

        });

        ImageButton btntelemail = (ImageButton)findViewById(R.id.telemail);
        btntelemail.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, TeleMail1.class);
                startActivity(intent);
                Menu.this.finish();
            }
        });

        ImageButton btnsafe = (ImageButton)findViewById(R.id.safe);
        btnsafe.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Helper.isPad(instance)) {
                    Toast.makeText(instance, "此裝置為平板，無電話功能，請撥打(02)2585-0164", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intentDial = new Intent("android.intent.action.DIAL", Uri.parse("tel:0225850164"));
                startActivity(intentDial);

            }
        });

        ImageButton btnschedule = (ImageButton)findViewById(R.id.schedule);
        btnschedule.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Helper.haveInternet(instance)) {
                    Toast.makeText(instance, "目前沒有網路連接，請開啟行動數據或WiFi。", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.setClass(Menu.this, ScheduleList.class);
                startActivity(intent);
                Menu.this.finish();
            }
        });

        //add by ming
        ImageButton signButton = (ImageButton) findViewById(R.id.signButton);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        ConfirmExit();
    }


    public void ConfirmExit(){

        AlertDialog.Builder ad=new AlertDialog.Builder(Menu.this);

        ad.setTitle("離開");

        ad.setMessage("確定要離開?");

        ad.setPositiveButton("是", new DialogInterface.OnClickListener() { //按"是",則退出應用程式

            public void onClick(DialogInterface dialog, int i) {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }

        });

        ad.setNegativeButton("否", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int i) {

            }

        });

        ad.show();

    }
}


