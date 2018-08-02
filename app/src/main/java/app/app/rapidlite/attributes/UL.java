package app.app.rapidlite.attributes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by hardip on 12/2/18.
 */

public class UL {

    public static boolean isNull(String str){
        if (str==null || str.trim().length()==0){
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNullE(EditText et){
        if (et==null || et.getText()==null || et.getText().toString().trim().length()==0){
            return true;
        } else {
            return false;
        }
    }

    public static String EtV(EditText et){
       return et.getText().toString().trim();
    }

    public static void showToast(final Context context, final String str){
        Toast.makeText(context,str, Toast.LENGTH_LONG).show();
    }

    public static void setPrefStr(Context mContext, String key,
                                  String value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContext.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        // Commit the edits!
        editor.commit();
    }


    public static String getPrfStr(Context mContext, String key) {
        try {
            SharedPreferences settings = mContext.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_MULTI_PROCESS);
            String value = settings.getString(key, "");
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static boolean isNetAvail(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }

//    public static LinearLayout getLayout(Activity context, String title, String msg){
//        LinearLayout ll=null;
//
//         ll = (LinearLayout) context.getLayoutInflater().inflate(R.layout.custom_dialog_bottom_sheet,null);
//        TextView header = ll.findViewById(R.id.header);
//        TextView tv_msg = ll.findViewById(R.id.msg);
//
//        header.setText(title);
//        tv_msg.setText(msg);
//
//        return ll;
//    }

//    public static void displayExitDialog(final Context context) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
//        dialog.getWindow().setBackgroundDrawable(
//                new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.alert_dialog);
//        dialog.setCancelable(false);
//        TextView btnOkay = (TextView) dialog.findViewById(R.id.btn_dialog_ok);
//        TextView tv_description = (TextView) dialog
//                .findViewById(R.id.tv_alert_dialog_description);
//
//        tv_description.setText("Authentication error!\n\nAnother device has signed in with your current account credentials.");
//
//        UL.setPrefStr(context, Constant.USER_ID, "");
//
//
//        btnOkay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO Auto-generated method stub
//                dialog.dismiss();
//                Intent intent = new Intent(context, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                context.startActivity(intent);
//            }
//        });
//
//        if (!dialog.isShowing()) {
//            dialog.show();
//        }
//    }

    public static boolean isPermissionGranted(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static boolean isPermissionGrantedgLOC(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission("android.permission.ACCESS_FINE_LOCATION") != PackageManager.PERMISSION_GRANTED) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public static String getDateConvert(String str) {
        String DateString = "";
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
            Date date = format1.parse(str);
            DateString = format2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DateString;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]"
                + "+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void openSettings(Activity context, String msg) {
        showToast(context,msg);

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    public static String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        Log.d("lastv", lastVal);
        return lastVal;
    }

    public static boolean isGPSEnabled(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        if (mLocationManager != null) {
            return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } else {
            return false;
        }
    }

    public static Calendar getDateFromMillis(String milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        return calendar;
//        return formatter.format(calendar.getTime());
    }

    public static String getDateFromMillisString(String milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
//        return calendar;
        return formatter.format(calendar.getTime());
    }

    public static void writeSharedPreferencesInt(Context mContext, String key,
                                                 int value) {
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = mContext.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        // Commit the edits!
        editor.commit();
    }

    public static int getAppPrefInt(Context mContext, String key) {
        try {
            SharedPreferences settings = mContext.getSharedPreferences(Constant.PREFS_NAME, Context.MODE_MULTI_PROCESS);
            int value = settings.getInt(key, 0);
            return value;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
