package app.app.rapidlite.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import app.app.rapidlite.R;
import app.app.rapidlite.attributes.Constant;
import app.app.rapidlite.attributes.UL;
import app.app.rapidlite.receiver.BgService;

import static app.app.rapidlite.attributes.Constant.SERVICE_STATUS;
import static app.app.rapidlite.attributes.Constant.USER_NAME;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private SwitchCompat switchC;
    private TextView tvTitle;
    private ImageView img_logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        mContext = MainActivity.this;

        switchC = findViewById(R.id.switchC);
        tvTitle = findViewById(R.id.tvTitle);
        img_logout = findViewById(R.id.img_logout);

        tvTitle.setText("Hello, " + UL.getPrfStr(mContext, USER_NAME));

        if (UL.getPrfStr(mContext, Constant.SERVICE_STATUS).equalsIgnoreCase("true")) {
            switchC.setChecked(true);
        } else {
            switchC.setChecked(false);
        }

        switchC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    UL.setPrefStr(mContext, Constant.SERVICE_STATUS, "true");
                } else {
                    UL.setPrefStr(mContext, Constant.SERVICE_STATUS, "false");
                }

            }
        });

        img_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginConfirmation();
//                Intent newIntent = new Intent(MainActivity.this, BgService.class);
//                newIntent.putExtra("from", "Karmdip");
//                newIntent.putExtra("body", "http://maps.google.com/maps?q=N10.472028,E77.467410");
//                startService(newIntent);
            }
        });

    }

    private void loginConfirmation() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                UL.setPrefStr(mContext, USER_NAME, "");
                                UL.setPrefStr(mContext, SERVICE_STATUS, "");

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                                // TODO Auto-generated method stub

                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        }).show();
    }

}
