package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    RecyclerView recyclerViewEvents;
    ArrayList<Event> eventsList;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerViewEvents = (RecyclerView) findViewById(R.id.recyclerViewEvents);

        eventsList = new ArrayList<Event>();

        loadEventsFromDatabase();

        eventAdapter = new EventAdapter(eventsList, new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(Event event) {
                showEventDetails(event);
            }
        });

        recyclerViewEvents.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewEvents.setAdapter(eventAdapter);
    }

    private void loadEventsFromDatabase() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(EventsActivity.this);

        Cursor cursor = dataBaseHelper.getAllEvents();

        eventsList.clear();

        if (cursor.getCount() == 0) {
            Toast.makeText(
                    EventsActivity.this,
                    "No events found. Please connect to API first.",
                    Toast.LENGTH_SHORT
            ).show();

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            Event event = new Event();

            event.setId(cursor.getInt(0));
            event.setTitle(cursor.getString(1));
            event.setDescription(cursor.getString(2));
            event.setCategory(cursor.getString(3));
            event.setDate(cursor.getString(4));
            event.setTime(cursor.getString(5));
            event.setLocation(cursor.getString(6));
            event.setSeats(cursor.getInt(7));
            event.setImage(cursor.getString(8));

            eventsList.add(event);
        }

        cursor.close();
    }

    private void showEventDetails(Event event) {

        EventDetailsFragment eventDetailsFragment = new EventDetailsFragment();

        Bundle bundle = new Bundle();

        bundle.putInt("id", event.getId());
        bundle.putString("title", event.getTitle());
        bundle.putString("description", event.getDescription());
        bundle.putString("category", event.getCategory());
        bundle.putString("date", event.getDate());
        bundle.putString("time", event.getTime());
        bundle.putString("location", event.getLocation());
        bundle.putInt("seats", event.getSeats());
        bundle.putString("image", event.getImage());

        eventDetailsFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragmentContainerDetails, eventDetailsFragment);
        fragmentTransaction.commit();
    }
}