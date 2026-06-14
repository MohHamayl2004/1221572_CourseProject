package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EventsActivity extends AppCompatActivity {

    LinearLayout linearLayoutEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        linearLayoutEvents = (LinearLayout) findViewById(R.id.linearLayoutEvents);

        loadEvents();
    }

    private void loadEvents() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(EventsActivity.this);

        Cursor cursor = dataBaseHelper.getAllEvents();

        linearLayoutEvents.removeAllViews();

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(EventsActivity.this);
            textView.setText("No events found. Please connect to API first.");
            textView.setTextSize(18);
            linearLayoutEvents.addView(textView);
            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String location = cursor.getString(6);
            int seats = cursor.getInt(7);

            TextView textViewEvent = new TextView(EventsActivity.this);

            textViewEvent.setText(
                    "Title: " + title +
                            "\nDescription: " + description +
                            "\nCategory: " + category +
                            "\nDate: " + date +
                            "\nTime: " + time +
                            "\nLocation: " + location +
                            "\nSeats: " + seats +
                            "\n\n"
            );

            textViewEvent.setTextSize(16);
            textViewEvent.setPadding(10, 10, 10, 10);

            linearLayoutEvents.addView(textViewEvent);
        }

        cursor.close();
    }
}