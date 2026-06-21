package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminEventsActivity extends AppCompatActivity {

    EditText editTextEventId;
    EditText editTextEventTitle;
    EditText editTextEventDescription;
    EditText editTextEventCategory;
    EditText editTextEventDate;
    EditText editTextEventTime;
    EditText editTextEventLocation;
    EditText editTextEventSeats;
    EditText editTextEventImage;
    Button buttonSaveEvent;
    LinearLayout linearLayoutAdminEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_events);

        editTextEventId = (EditText) findViewById(R.id.editTextEventId);
        editTextEventTitle = (EditText) findViewById(R.id.editTextEventTitle);
        editTextEventDescription = (EditText) findViewById(R.id.editTextEventDescription);
        editTextEventCategory = (EditText) findViewById(R.id.editTextEventCategory);
        editTextEventDate = (EditText) findViewById(R.id.editTextEventDate);
        editTextEventTime = (EditText) findViewById(R.id.editTextEventTime);
        editTextEventLocation = (EditText) findViewById(R.id.editTextEventLocation);
        editTextEventSeats = (EditText) findViewById(R.id.editTextEventSeats);
        editTextEventImage = (EditText) findViewById(R.id.editTextEventImage);
        buttonSaveEvent = (Button) findViewById(R.id.buttonSaveEvent);
        linearLayoutAdminEvents = (LinearLayout) findViewById(R.id.linearLayoutAdminEvents);

        buttonSaveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEvent();
            }
        });

        loadEvents();
    }

    private void saveEvent() {

        String idText = editTextEventId.getText().toString();
        String title = editTextEventTitle.getText().toString();
        String seatsText = editTextEventSeats.getText().toString();

        if (idText.isEmpty() || title.isEmpty() || seatsText.isEmpty()) {
            Toast.makeText(AdminEventsActivity.this, "ID, title and seats are required", Toast.LENGTH_SHORT).show();
            return;
        }

        Event event = new Event();

        event.setId(Integer.parseInt(idText));
        event.setTitle(title);
        event.setDescription(editTextEventDescription.getText().toString());
        event.setCategory(editTextEventCategory.getText().toString());
        event.setDate(editTextEventDate.getText().toString());
        event.setTime(editTextEventTime.getText().toString());
        event.setLocation(editTextEventLocation.getText().toString());
        event.setSeats(Integer.parseInt(seatsText));
        event.setImage(editTextEventImage.getText().toString());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AdminEventsActivity.this);

        boolean updated = dataBaseHelper.updateEvent(event);

        if (updated) {
            Toast.makeText(AdminEventsActivity.this, "Event updated", Toast.LENGTH_SHORT).show();
        } else {
            dataBaseHelper.insertSingleEvent(event);
            Toast.makeText(AdminEventsActivity.this, "Event added", Toast.LENGTH_SHORT).show();
        }

        clearForm();
        loadEvents();
    }

    private void clearForm() {
        editTextEventId.setText("");
        editTextEventTitle.setText("");
        editTextEventDescription.setText("");
        editTextEventCategory.setText("");
        editTextEventDate.setText("");
        editTextEventTime.setText("");
        editTextEventLocation.setText("");
        editTextEventSeats.setText("");
        editTextEventImage.setText("");
    }

    private void loadEvents() {

        linearLayoutAdminEvents.removeAllViews();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AdminEventsActivity.this);

        Cursor cursor = dataBaseHelper.getAllEvents();

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(AdminEventsActivity.this);
            textView.setText("No events found.");
            textView.setTextSize(18);
            textView.setPadding(10, 20, 10, 20);

            linearLayoutAdminEvents.addView(textView);

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            final int id = cursor.getInt(0);
            final String title = cursor.getString(1);
            final String description = cursor.getString(2);
            final String category = cursor.getString(3);
            final String date = cursor.getString(4);
            final String time = cursor.getString(5);
            final String location = cursor.getString(6);
            final int seats = cursor.getInt(7);
            final String image = cursor.getString(8);

            TextView textViewEvent = new TextView(AdminEventsActivity.this);

            String eventInfo =
                    "ID: " + id + "\n" +
                            "Title: " + title + "\n" +
                            "Category: " + category + "\n" +
                            "Date: " + date + " | Time: " + time + "\n" +
                            "Location: " + location + "\n" +
                            "Seats: " + seats;

            textViewEvent.setText(eventInfo);
            textViewEvent.setTextSize(16);
            textViewEvent.setPadding(15, 15, 15, 15);
            textViewEvent.setBackgroundColor(0xFFEFEFEF);

            Button buttonEdit = new Button(AdminEventsActivity.this);
            buttonEdit.setText("Edit");

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editTextEventId.setText(String.valueOf(id));
                    editTextEventTitle.setText(title);
                    editTextEventDescription.setText(description);
                    editTextEventCategory.setText(category);
                    editTextEventDate.setText(date);
                    editTextEventTime.setText(time);
                    editTextEventLocation.setText(location);
                    editTextEventSeats.setText(String.valueOf(seats));
                    editTextEventImage.setText(image);
                }
            });

            Button buttonDelete = new Button(AdminEventsActivity.this);
            buttonDelete.setText("Delete");

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBaseHelper helper = new DataBaseHelper(AdminEventsActivity.this);
                    helper.deleteEvent(id);

                    Toast.makeText(AdminEventsActivity.this, "Event deleted", Toast.LENGTH_SHORT).show();

                    loadEvents();
                }
            });

            linearLayoutAdminEvents.addView(textViewEvent);
            linearLayoutAdminEvents.addView(buttonEdit);
            linearLayoutAdminEvents.addView(buttonDelete);
        }

        cursor.close();
    }
}
