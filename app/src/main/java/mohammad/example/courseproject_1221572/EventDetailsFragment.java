package mohammad.example.courseproject_1221572;

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
            textViewDetailsTitle.setText(bundle.getString("title"));
            textViewDetailsCategory.setText("Category: " + bundle.getString("category"));
            textViewDetailsDescription.setText(bundle.getString("description"));
            textViewDetailsDateTime.setText("Date: " + bundle.getString("date") + " | Time: " + bundle.getString("time"));
            textViewDetailsLocation.setText("Location: " + bundle.getString("location"));
            textViewDetailsSeats.setText("Available Seats: " + bundle.getInt("seats"));
        }

        buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Favorite will be added next", Toast.LENGTH_SHORT).show();
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Reservation form will be added next", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}