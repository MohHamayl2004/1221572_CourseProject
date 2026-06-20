package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyReservationsActivity extends AppCompatActivity {

    LinearLayout linearLayoutReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservations);

        linearLayoutReservations = (LinearLayout) findViewById(R.id.linearLayoutReservations);

        loadReservations();
    }

    private void loadReservations() {

        linearLayoutReservations.removeAllViews();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(MyReservationsActivity.this);

        String currentEmail = getSharedPreferences("SmartEventsPrefs", MODE_PRIVATE)
                .getString("current_user_email", "");

        Cursor cursor = dataBaseHelper.getReservationsByUser(currentEmail);

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(MyReservationsActivity.this);
            textView.setText("No reservations yet.");
            textView.setTextSize(18);
            textView.setPadding(10, 20, 10, 20);

            linearLayoutReservations.addView(textView);

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            String eventTitle = cursor.getString(2);
            String reservationDate = cursor.getString(3);
            int quantity = cursor.getInt(4);
            String type = cursor.getString(5);
            String status = cursor.getString(6);

            TextView textViewReservation = new TextView(MyReservationsActivity.this);

            String reservationInfo =
                    "Event: " + eventTitle + "\n" +
                            "Reservation Date: " + reservationDate + "\n" +
                            "Quantity: " + quantity + "\n" +
                            "Type: " + type + "\n" +
                            "Status: " + status;

            textViewReservation.setText(reservationInfo);
            textViewReservation.setTextSize(16);
            textViewReservation.setPadding(15, 15, 15, 15);
            textViewReservation.setBackgroundColor(0xFFEFEFEF);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 15);
            textViewReservation.setLayoutParams(params);

            linearLayoutReservations.addView(textViewReservation);
        }

        cursor.close();
    }
}
