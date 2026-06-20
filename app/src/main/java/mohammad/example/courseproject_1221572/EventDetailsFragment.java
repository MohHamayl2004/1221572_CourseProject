package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class EventDetailsFragment extends Fragment {

    TextView textViewDetailsTitle;
    TextView textViewDetailsCategory;
    TextView textViewDetailsDescription;
    TextView textViewDetailsDateTime;
    TextView textViewDetailsLocation;
    TextView textViewDetailsSeats;

    Button buttonAddFavorite;
    Button buttonReserve;

    int eventId;
    String title;
    String description;
    String category;
    String date;
    String time;
    String location;
    int seats;
    String image;
    public EventDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        textViewDetailsTitle = (TextView) view.findViewById(R.id.textViewDetailsTitle);
        textViewDetailsCategory = (TextView) view.findViewById(R.id.textViewDetailsCategory);
        textViewDetailsDescription = (TextView) view.findViewById(R.id.textViewDetailsDescription);
        textViewDetailsDateTime = (TextView) view.findViewById(R.id.textViewDetailsDateTime);
        textViewDetailsLocation = (TextView) view.findViewById(R.id.textViewDetailsLocation);
        textViewDetailsSeats = (TextView) view.findViewById(R.id.textViewDetailsSeats);

        buttonAddFavorite = (Button) view.findViewById(R.id.buttonAddFavorite);
        buttonReserve = (Button) view.findViewById(R.id.buttonReserve);

        Bundle bundle = getArguments();

        if (bundle != null) {
            eventId = bundle.getInt("id");
            title = bundle.getString("title");
            description = bundle.getString("description");
            category = bundle.getString("category");
            date = bundle.getString("date");
            time = bundle.getString("time");
            location = bundle.getString("location");
            seats = bundle.getInt("seats");
            image = bundle.getString("image");

            textViewDetailsTitle.setText(title);
            textViewDetailsCategory.setText("Category: " + category);
            textViewDetailsDescription.setText(description);
            textViewDetailsDateTime.setText("Date: " + date + " | Time: " + time);
            textViewDetailsLocation.setText("Location: " + location);
            textViewDetailsSeats.setText("Available Seats: " + seats);
        }

        buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity());

                String currentEmail = getActivity()
                        .getSharedPreferences("SmartEventsPrefs", getActivity().MODE_PRIVATE)
                        .getString("current_user_email", "");

                if (dataBaseHelper.checkFavoriteExists(eventId, currentEmail)) {
                    Toast.makeText(getActivity(), "Event already in favorites", Toast.LENGTH_SHORT).show();
                    return;
                }

                Event event = new Event();

                event.setId(eventId);
                event.setTitle(title);
                event.setDescription(description);
                event.setCategory(category);
                event.setDate(date);
                event.setTime(time);
                event.setLocation(location);
                event.setSeats(seats);
                event.setImage(image);

                boolean inserted = dataBaseHelper.insertFavorite(event, currentEmail);

                if (inserted) {
                    Toast.makeText(getActivity(), "Added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to add favorite", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ReservationActivity.class);

                intent.putExtra("eventId", eventId);
                intent.putExtra("eventTitle", title);

                startActivity(intent);
            }
        });

        return view;
    }
}