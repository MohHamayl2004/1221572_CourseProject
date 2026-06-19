package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventsActivity extends AppCompatActivity {

    RecyclerView recyclerViewEvents;
    EditText editTextSearch;
    Spinner spinnerCategoryFilter;

    ArrayList<Event> allEventsList;
    ArrayList<Event> eventsList;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        recyclerViewEvents = (RecyclerView) findViewById(R.id.recyclerViewEvents);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        spinnerCategoryFilter = (Spinner) findViewById(R.id.spinnerCategoryFilter);

        allEventsList = new ArrayList<Event>();
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

        setupCategoryFilter();
        setupSearch();

        applyFilters();
    }

    private void loadEventsFromDatabase() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(EventsActivity.this);

        Cursor cursor = dataBaseHelper.getAllEvents();

        allEventsList.clear();

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

            allEventsList.add(event);
        }

        cursor.close();
    }

    private void setupCategoryFilter() {

        ArrayList<String> categories = new ArrayList<String>();
        categories.add("All Categories");

        for (int i = 0; i < allEventsList.size(); i++) {
            String category = allEventsList.get(i).getCategory();
            if (category != null && !categories.contains(category)) {
                categories.add(category);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                EventsActivity.this,
                android.R.layout.simple_spinner_item,
                categories
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoryFilter.setAdapter(adapter);

        spinnerCategoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupSearch() {

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void applyFilters() {

        String query = editTextSearch.getText().toString().toLowerCase().trim();

        String selectedCategory = "All Categories";
        if (spinnerCategoryFilter.getSelectedItem() != null) {
            selectedCategory = spinnerCategoryFilter.getSelectedItem().toString();
        }

        eventsList.clear();

        for (int i = 0; i < allEventsList.size(); i++) {

            Event event = allEventsList.get(i);

            String title = event.getTitle() != null ? event.getTitle().toLowerCase() : "";
            String category = event.getCategory() != null ? event.getCategory() : "";
            String location = event.getLocation() != null ? event.getLocation().toLowerCase() : "";

            boolean matchesQuery =
                    query.isEmpty()
                            || title.contains(query)
                            || category.toLowerCase().contains(query)
                            || location.contains(query);

            boolean matchesCategory =
                    selectedCategory.equals("All Categories")
                            || category.equals(selectedCategory);

            if (matchesQuery && matchesCategory) {
                eventsList.add(event);
            }
        }

        eventAdapter.notifyDataSetChanged();
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
