package dev.alirio.radiowakeuparc;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: Alirio Rivera
 * RadioWakeupActivity implement methods to communicate bewtween the UI and the services for the
 * Radio wakeup
 */
public class RadioWakeupActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private AlarmManager manager;
    private Long futureTime = null;
    private RadioAlarmReceiver radioAlarmReceiver = null;

    private final int ACTIVATE_BUTTON = 1;
    private final int DEACTIVATE_BUTTON = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_wakeup);

        // getting the date Text to set the datePicker abilities
        EditText dateEditText = (EditText) findViewById(R.id.alarmDateText);
        new DateSetter(dateEditText);

        // getting the time Text to set the timePicker abilities
        EditText timeEditText = (EditText) findViewById(R.id.alarmTimeText);
        new TimeSetter(timeEditText);

        // I had to put the focus on the welcometext because when the application starts the focus was
        // on one of the textviews (date or time) therefore the app showed immediately the specific
        // picker for the textview
        TextView welcometext = (TextView) findViewById(R.id.top_welcome_text1);
        welcometext.requestFocusFromTouch();

        //Activate the start radio button while deactivating the stop radio button
        executeButtonAction(ACTIVATE_BUTTON, R.id.setAlarmButton);
        executeButtonAction(DEACTIVATE_BUTTON, R.id.stopAlarmButton);

        radioAlarmReceiver = new RadioAlarmReceiver();
    }

    /**
     * Method used to activate the alarm and the service at the specific time selected by the user.
     * @param view
     */
    public void setRadioAlarmStart(View view) {

        if(isDateAndTimeFormatParseable()){
            Context context = this.getApplicationContext();
            EditText dateSelected = (EditText) findViewById(R.id.alarmDateText);
            EditText timeSelected = (EditText) findViewById(R.id.alarmTimeText);
            executeButtonAction(DEACTIVATE_BUTTON, R.id.setAlarmButton);
            executeButtonAction(ACTIVATE_BUTTON, R.id.stopAlarmButton);
            stopRadioPlayerService();
            radioAlarmReceiver.SetAlarm(context, futureTime, dateSelected.getText().toString() + " " +timeSelected.getText().toString());

        }else{
            executeButtonAction(ACTIVATE_BUTTON, R.id.setAlarmButton);
            executeButtonAction(DEACTIVATE_BUTTON, R.id.stopAlarmButton);
        }
    }

    /**
     * method used to stop the radio stream and it is called from the stop button
     * @param view
     */
    public void setRadioAlarmStop(View view){
        stopRadioPlayerService();
        executeButtonAction(ACTIVATE_BUTTON, R.id.setAlarmButton);
        executeButtonAction(DEACTIVATE_BUTTON, R.id.stopAlarmButton);
    }

    /**
     * Method used to stop the current radio service streaming
     */
    private void stopRadioPlayerService(){
        Intent intent = new Intent(this, RadioPlayerService.class);
        stopService(intent);
    }

    /**
     * Method used to check if the input date and time selected by the user are valids
     * @return true if both inputs are valid
     */
    private boolean isDateAndTimeFormatParseable(){

        EditText dateSelected = (EditText) findViewById(R.id.alarmDateText);
        EditText timeSelected = (EditText) findViewById(R.id.alarmTimeText);

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        formatter.setLenient(false);
        try {
            Date selectedDateTime= formatter.parse(dateSelected.getText().toString() + " " +timeSelected.getText().toString());
            Date currentTime = new Date();

            if(selectedDateTime.before(currentTime)){
                Toast.makeText(this, "input date is before or equals to today's Date!!", Toast.LENGTH_LONG).show();
                return false;
            }else {
                futureTime = selectedDateTime.getTime() - currentTime.getTime();
                return true;
            }

        } catch (ParseException e) {
            Toast.makeText(this, "Date or Time format are not valid!!", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    /**
     * Method used to activate or deactivate a button widget
     * @param action ACTIVATE or DEACTIVATE
     * @param widget id of the button widget
     */
    private void executeButtonAction(int action, int widget){
        Button setAlarmBut = (Button) findViewById(widget);
        switch (action) {
            case ACTIVATE_BUTTON:
                setAlarmBut.setEnabled(true);
                break;
            case DEACTIVATE_BUTTON:
                setAlarmBut.setEnabled(false);
                break;
        }
    }
}
