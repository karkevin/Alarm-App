package com.example.alarm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public final static String EXTRA_MESSAGE = "com.example.alarm.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void buttonClicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }
    public void Null(View view) {}

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,i);
        cal.set(Calendar.MINUTE, i1);
        cal.set(Calendar.SECOND, 0);

        updateTime(cal);
        startAlarm(cal);
    }

    private void updateTime(Calendar c) {
        String text = "Alarm set for: "; //+ DateFormat.getTimeInstance(DateFormat.SHORT).format(c);
        if(DateFormat.is24HourFormat(this)){
            text += new SimpleDateFormat("k:m", Locale.ENGLISH).format(c.getTime());
        } else {
            text += new SimpleDateFormat("K:m a", Locale.ENGLISH).format(c.getTime());
        }
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(text);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);


    }
}
