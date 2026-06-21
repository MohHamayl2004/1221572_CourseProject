package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminReservationsActivity extends AppCompatActivity {

    LinearLayout linearLayoutAdminReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_reservations);

        linearLayoutAdminReservations = (LinearLayout) findViewById(R.id.linearLayoutAdminReservations);

        loadReservations();
    }

    private void loadReservations() {

        linearLayoutAdminReservations.removeAllViews();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AdminReservationsActivity.this);

        Cursor cursor = dataBaseHelper.getAllReservations();

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(AdminReservationsActivity.this);
            textView.setText("No reservations found.");
            textView.setTextSize(18);
            textView.setPadding(10, 20, 10, 20);

            linearLayoutAdminReservations.addView(textView);

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            String eventTitle = cursor.getString(2);
            String reservationDate = cursor.getString(3);
            int quantity = cursor.getInt(4);
            String type = cursor.getString(5);
            String status = cursor.getString(6);
            String userEmail = cursor.getString(7);

            TextView textViewReservation = new TextView(AdminReservationsActivity.this);

            String reservationInfo =
                    "Event: " + eventTitle + "\n" +
                            "User: " + userEmail + "\n" +
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

            linearLayoutAdminReservations.addView(textViewReservation);
        }

        cursor.close();
    }
}
