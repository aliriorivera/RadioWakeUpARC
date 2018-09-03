package dev.alirio.radiowakeuparc;


/**
 * modified from:
 * https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext
 * I modified this code because it contained all the features I required so it made not sense to
 * develop my own implementation.
 */

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateSetter implements View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener, View.OnClickListener{


    private EditText dateTextToSet;
    private Calendar calendarToGetDate;
    private SimpleDateFormat TimeFormatter;

    public DateSetter(EditText editText){
        this.dateTextToSet = editText;
        dateTextToSet.setOnFocusChangeListener(this);
        dateTextToSet.setOnClickListener(this);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        calendarToGetDate.set(Calendar.YEAR, year);
        calendarToGetDate.set(Calendar.MONTH, month);
        calendarToGetDate.set(Calendar.DAY_OF_MONTH, day);

        if (TimeFormatter == null)
            TimeFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        this.dateTextToSet.setText(TimeFormatter.format(calendarToGetDate.getTime()));
    }

    @Override
    public void onClick(View view) {
        showPicker(view);
    }

    private void showPicker(View view) {
        if (calendarToGetDate == null)
            calendarToGetDate = Calendar.getInstance();

        int year = calendarToGetDate.get(Calendar.YEAR);
        int month = calendarToGetDate.get(Calendar.MONTH);
        int day = calendarToGetDate.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(view.getContext(), this, year, month, day).show();
    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus){
            showPicker(view);
        }
    }
}
