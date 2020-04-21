package in.akshit.horizontalcalendarview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import in.akshit.horizontalcalendar.HorizontalCalendarModel;
import in.akshit.horizontalcalendar.HorizontalCalendarView;
import in.akshit.horizontalcalendar.Tools;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final HorizontalCalendarView calendarView = findViewById(R.id.calendar);

        Calendar starttime = Calendar.getInstance();
        starttime.add(Calendar.MONTH,-6);

        Calendar endtime = Calendar.getInstance();
        endtime.add(Calendar.MONTH,6);

        ArrayList datesToBeColored = new ArrayList();
        datesToBeColored.add(Tools.getFormattedDateToday());

        calendarView.setUpCalendar(starttime.getTimeInMillis(), endtime.getTimeInMillis(), datesToBeColored, new HorizontalCalendarView.OnCalendarListener() {
            @Override
            public void onDateSelected(HorizontalCalendarModel date) {
                calendarView.updateAdapter(date);
            }
        });
    }
}
