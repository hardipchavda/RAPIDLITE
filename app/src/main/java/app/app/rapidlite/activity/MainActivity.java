package app.app.rapidlite.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.TextView;

import app.app.rapidlite.R;
import app.app.rapidlite.attributes.Constant;
import app.app.rapidlite.attributes.UL;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private SwitchCompat switchC;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

           init();

    }

    private void init(){
      mContext = MainActivity.this;

        switchC = findViewById(R.id.switchC);
        tvTitle = findViewById(R.id.tvTitle);

        tvTitle.setText("Hello, "+UL.getPrfStr(mContext, Constant.USER_NAME));

        if (UL.getPrfStr(mContext, Constant.SERVICE_STATUS).equalsIgnoreCase("true")){
            switchC.setChecked(true);
        } else {
            switchC.setChecked(false);
        }

        switchC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    UL.setPrefStr(mContext, Constant.SERVICE_STATUS,"true");
                } else {
                    UL.setPrefStr(mContext, Constant.SERVICE_STATUS,"false");
                }

            }
        });

    }

}
