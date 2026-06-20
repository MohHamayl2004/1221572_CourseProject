package mohammad.example.courseproject_1221572;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReservationActivity extends AppCompatActivity {

    TextView textViewReservationTitle;
    EditText editTextQuantity;
    Spinner spinnerReservationType;
    Button buttonConfirmReservation;

    int eventId;
    String eventTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        textViewReservationTitle = (TextView) findViewById(R.id.textViewReservationTitle);
        editTextQuantity = (EditText) findViewById(R.id.editTextQuantity);
        spinnerReservationType = (Spinner) findViewById(R.id.spinnerReservationType);
        buttonConfirmReservation = (Button) findViewById(R.id.buttonConfirmReservation);

        eventId = getIntent().getIntExtra("eventId", 0);
        eventTitle = getIntent().getStringExtra("eventTitle");

        textViewReservationTitle.setText("Reserve: " + eventTitle);

        String[] types = {"Regular Attendance", "VIP Attendance", "Volunteer Participation"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                ReservationActivity.this,
                android.R.layout.simple_spinner_item,
                types
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReservationType.setAdapter(adapter);

        buttonConfirmReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmReservation();
            }
        });
    }

    private void confirmReservation() {

        String quantityText = editTextQuantity.getText().toString().trim();

        if (quantityText.isEmpty()) {
            Toast.makeText(ReservationActivity.this, "Please enter quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityText);

        if (quantity <= 0) {
            Toast.makeText(ReservationActivity.this, "Quantity must be greater than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String type = spinnerReservationType.getSelectedItem().toString();

        String reservationDate = new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());

        DataBaseHelper dataBaseHelper = new DataBaseHelper(ReservationActivity.this);

        String currentEmail = getSharedPreferences("SmartEventsPrefs", MODE_PRIVATE)
                .getString("current_user_email", "");

        boolean inserted = dataBaseHelper.insertReservation(
                eventId,
                eventTitle,
                reservationDate,
                quantity,
                type,
                "Confirmed",
                currentEmail
        );

        if (inserted) {
            Toast.makeText(ReservationActivity.this, "Reservation confirmed", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ReservationActivity.this, "Reservation failed", Toast.LENGTH_SHORT).show();
        }
    }
}