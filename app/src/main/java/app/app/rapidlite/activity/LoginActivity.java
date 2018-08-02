package app.app.rapidlite.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import app.app.rapidlite.R;
import app.app.rapidlite.api.APIClient;
import app.app.rapidlite.api.APIInterface;
import app.app.rapidlite.api.LoginResponse;
import app.app.rapidlite.attributes.Constant;
import app.app.rapidlite.attributes.UL;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static app.app.rapidlite.attributes.Constant.USER_ID;
import static app.app.rapidlite.attributes.Constant.USER_NAME;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private EditText et_username,et_password;
    private TextView btn_login;
    private ProgressDialog progressDialog;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UL.getPrfStr(LoginActivity.this, Constant.USER_NAME).trim().length()>0){

            Intent in = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(in);
            finish();

        } else {
            setContentView(R.layout.activity_login);
            init();
            requestSmsPermission();
        }

    }

    private void init(){

        mContext = LoginActivity.this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        progressDialog = new ProgressDialog(mContext,ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");

        btn_login = findViewById(R.id.btn_login);
        et_password = findViewById(R.id.et_password);
        et_username = findViewById(R.id.et_username);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

//                UL.setPrefStr(mContext, USER_ID, "1");
//                UL.setPrefStr(mContext, ACCESS_TOKEN, "Bearer "+"VPpI7aZ-6JT4oRsaOIGqjfwU5aodla1iI5M-bzQQK-11tL5FHi7AG616QnVPbVhslBPl9FKGOFlHi5l1S_vYwHLYEDQaApzLIf7QHVXW8qjmecvcZCLeOw6HjZmmt6IbDm0PAWeypYHKB1kOmP2DBCWf0VeAvp0ZqcwwqXRzj2kDaXvQYOKYb7iR-Je7Q69eQnJ5coGPCXQRrADNV3m6-3e__X4duMQ8eRd_5AlaNeHxswZP-RtKW47JCIbKKk3QsBv1PETAgVb5kPgSXt2uLEFyWclh4AXqeyd1biajX8Vw5V76VBaLhnSHzpNtm0K4KA1UQa57Agor_61UnTzRfKcmF_YDIh_CnOcZ6fvV39KIQAoeRrG2O_cv7fqEcvHL0Q7q4yvWOaHFzPUBaKJ89hsh9SvSYKIERPQlxN2iIYDsczOks_z6Ygm_xfDlBEtzDkzOkydItgUGTs59GW2KubLFmJrIPP-KmWuhGMFzThMQUi0cvHWfFd5iYCLuGJJD");
//
//                Intent in = new Intent(mContext, MainActivity.class);
//                startActivity(in);
//                finish();
            }
        });

    }

    private void login() {

        if (UL.isNullE(et_username)) {
            et_username.setError("Username is required!");
        } else if (UL.isNullE(et_password)) {
            et_password.setError("Password is required!");
        } else {

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }


            apiInterface.login(UL.EtV(et_username), UL.EtV(et_password)).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    if (response.isSuccessful() && response.body() != null) {

                        try {

                            if (response.body().getLoginStatus().getStatus().equalsIgnoreCase("true")){
                                Toast.makeText(mContext,"Login Successful!",Toast.LENGTH_LONG).show();
                                UL.setPrefStr(mContext, USER_ID, response.body().getLoginStatus().getSaasid());
                                UL.setPrefStr(mContext, USER_NAME, response.body().getLoginStatus().getUsername());

                                Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(in);
                                finish();

                            } else {
                                Toast.makeText(mContext,"Login Failed!",Toast.LENGTH_LONG).show();

                            }

                        } catch (Exception e) {
                        }

                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Log.e("onFailure", t.getMessage());
                }
            });


        }
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

}
