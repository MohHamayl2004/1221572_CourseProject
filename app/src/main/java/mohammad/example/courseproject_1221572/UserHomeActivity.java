package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserHomeActivity extends AppCompatActivity {

    Button buttonHome;
    Button buttonEvents;
    Button buttonReservations;
    Button buttonFavorites;
    Button buttonSpecial;
    Button buttonProfile;
    Button buttonContact;
    Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        buttonHome = (Button) findViewById(R.id.buttonHome);
        buttonEvents = (Button) findViewById(R.id.buttonEvents);
        buttonReservations = (Button) findViewById(R.id.buttonReservations);
        buttonFavorites = (Button) findViewById(R.id.buttonFavorites);
        buttonSpecial = (Button) findViewById(R.id.buttonSpecial);
        buttonProfile = (Button) findViewById(R.id.buttonProfile);
        buttonContact = (Button) findViewById(R.id.buttonContact);
        buttonLogout = (Button) findViewById(R.id.buttonLogout);

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserHomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
            }
        });

        buttonEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        buttonReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, MyReservationsActivity.class);
                startActivity(intent);
            }
        });

        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        buttonSpecial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserHomeActivity.this, "Special Section will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserHomeActivity.this, "Profile screen will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        buttonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserHomeActivity.this, "Contact Us screen will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}