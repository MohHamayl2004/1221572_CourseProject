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

public class SpecialSectionActivity extends AppCompatActivity {

    RecyclerView recyclerViewSpecial;
    ArrayList<Event> specialList;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_section);

        recyclerViewSpecial = (RecyclerView) findViewById(R.id.recyclerViewSpecial);

        specialList = new ArrayList<Event>();

        loadSpecialEvents();

        eventAdapter = new EventAdapter(specialList, new EventAdapter.OnEventClickListener() {
            @Override
            public void onEventClick(Event event) {
                showEventDetails(event);
            }
        });

        recyclerViewSpecial.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewSpecial.setAdapter(eventAdapter);
    }

    private void loadSpecialEvents() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(SpecialSectionActivity.this);

        Cursor cursor = dataBaseHelper.getSpecialEvents();

        specialList.clear();

        if (cursor.getCount() == 0) {
            Toast.makeText(
                    SpecialSectionActivity.this,
                    "No special events found. Please connect to API first.",
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

            specialList.add(event);
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

        fragmentTransaction.replace(R.id.fragmentContainerSpecial, eventDetailsFragment);
        fragmentTransaction.commit();
    }
}
