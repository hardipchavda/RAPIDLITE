package app.app.rapidlite.receiver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import app.app.rapidlite.R;
import app.app.rapidlite.activity.MainActivity;

public class BgService extends IntentService {

    public BgService() {
        super("BgService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.e("onHandleIntent","TRUE");

        if (intent!=null && intent.getStringExtra("from")!=null){
            sendNotification("Alert",intent.getStringExtra("body"));
        }

    }

    private void sendNotification(String Title, String message) {

        Intent intent = null;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), new Random().nextInt() /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            final int NOTIFY_ID = 1002;

            // There are hardcoding only for show it's just strings
            String name = "RAPIDLITE";
            String id = "RAPIDLITE"; // The user-visible name of the channel.
            String description = "Info"; // The user-visible description of the channel.

            android.support.v4.app.NotificationCompat.Builder builder;


            NotificationManager notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new android.support.v4.app.NotificationCompat.Builder(getApplicationContext(), id);

            builder.setContentTitle(Title)  // required
                    .setSmallIcon(R.mipmap.ic_launcher_round) // required
                    .setContentText(message)
                    .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(message))// required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("RAPIDLITE")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            Notification notification = builder.build();
            notifManager.notify(new Random().nextInt(), notification);

//            startForeground(cnt, notification);
        } else {


            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            android.support.v4.app.NotificationCompat.Builder notificationBuilder = (android.support.v4.app.NotificationCompat.Builder) new android.support.v4.app.NotificationCompat.Builder(getApplicationContext())
                    .setSmallIcon(R.mipmap.ic_launcher_round)
                    .setLargeIcon((BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round)))
                    .setContentTitle(Title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setStyle(new android.support.v4.app.NotificationCompat.BigTextStyle().bigText(message))
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            final int cnt = new Random().nextInt(100);
//            Log.e("cnt",""+cnt);
//            startForeground(cnt, notificationBuilder.build());
            notificationManager.notify(new Random().nextInt() /* ID of notification */, notificationBuilder.build());
        }
    }


}
