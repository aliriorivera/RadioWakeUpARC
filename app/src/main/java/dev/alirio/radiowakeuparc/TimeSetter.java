package dev.alirio.radiowakeuparc;


/**
 * modified from:
 * https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
 * I modified this code because it contained all the features I required so it made not sense to
 * develop my own implementation.
 */


import android.app.TimePickerDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimeSetter implements View.OnFocusChangeListener, TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    private EditText timeTextToSet;
    private Calendar calendarToGetDate;
    private SimpleDateFormat TimeFormatter;

    public TimeSetter(EditText editText){
        this.timeTextToSet = editText;
        timeTextToSet.setOnFocusChangeListener(this);
        timeTextToSet.setOnClickListener(this);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            showPicker(view);
        }
    }

    @Override
    public void onClick(View view) {
        showPicker(view);
    }

    private void showPicker(View view) {
        if (calendarToGetDate == null)
            calendarToGetDate = Calendar.getInstance();

        int hour = calendarToGetDate.get(Calendar.HOUR_OF_DAY);
        int minute = calendarToGetDate.get(Calendar.MINUTE);

        new TimePickerDialog(view.getContext(), this, hour, minute, true).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendarToGetDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarToGetDate.set(Calendar.MINUTE, minute);

        if (TimeFormatter == null)
            TimeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

        this.timeTextToSet.setText(TimeFormatter.format(calendarToGetDate.getTime()));
    }

}