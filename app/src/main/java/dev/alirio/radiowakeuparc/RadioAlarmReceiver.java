package dev.alirio.radiowakeuparc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * author: Alirio Rivera
 * RadioAlarmReceiver establishes the time when the radio service will start
 */
public class RadioAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent radioPlayerIntent= new Intent(context, RadioPlayerService.class);
        context.startService(radioPlayerIntent);
    }

    /**
     * Method used to set the trigger alarm time at a specific time
     * @param context app context
     * @param addedTime number of milliseconds from now in which the alart will be triggered.
     * @param timeToStart string with the date and time formatted to show to the user
     */
    public void SetAlarm(Context context, long addedTime, String timeToStart){

        Toast.makeText(context, "Radio Station will start playing at: " + timeToStart, Toast.LENGTH_LONG).show();

        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, RadioAlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + addedTime, pi);
    }
}
