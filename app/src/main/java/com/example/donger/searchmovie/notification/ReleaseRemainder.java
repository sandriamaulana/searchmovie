package com.example.donger.searchmovie.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.donger.searchmovie.MainActivity;
import com.example.donger.searchmovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static android.support.constraint.Constraints.TAG;

public class ReleaseRemainder extends BroadcastReceiver  {
    private static final String API_KEY = "a61b992f22c37cd1bb7a24928a04bdb1";
    private final static int ID_REPEATING = 102;

    @Override
    public void onReceive(Context context, Intent intent) {
        getReleaseToday(context);
    }

    private void getReleaseToday(final Context context){
        Log.d(TAG, "Running");
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String today = simpleDateFormat.format(date);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String message = context.getString(R.string.no_release_title);
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray jsonArray = responseObject.getJSONArray("results");
                    for (int i=0; i<jsonArray.length(); i++){
                        if  (jsonArray.getJSONObject(i).getString("release_date").equalsIgnoreCase(today)) {
                            String title = jsonArray.getJSONObject(i).getString("title");
                            message= title;
                        }
                    }
                    String title1Notification = context.getString(R.string.release_now_notif);
                    showReleaseNotification(context, title1Notification, message, ID_REPEATING);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

    }

    private void showReleaseNotification(Context context, String title, String message, int notifId) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(context, 0, intent, 0);

        String CHANNEL_ID = "Channel_2";
        String CHANNEL_NAME = "releaseManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent2)
                .setSmallIcon(R.drawable.ic_notif)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    public void setRepeatingAlarm(Context context, String time) {

        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseRemainder.class);


        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        Calendar timeNow = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
            if (alarmManager != null) {
                long daily = 0;
                if(calendar.getTimeInMillis() <= timeNow.getTimeInMillis())
                    daily = calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY+1);
                else
                    daily = calendar.getTimeInMillis();

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, daily, AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        Toast.makeText(context, context.getString(R.string.release_notif_on), Toast.LENGTH_SHORT).show();

    }
    private final static String TIME_FORMAT = "HH:mm";

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseRemainder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        Toast.makeText(context, context.getString(R.string.release_notif_off), Toast.LENGTH_SHORT).show();
    }
}
