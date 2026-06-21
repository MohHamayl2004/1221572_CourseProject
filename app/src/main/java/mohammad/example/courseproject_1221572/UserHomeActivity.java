package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

public class UserHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else if (id == R.id.nav_events) {
                    startActivity(new Intent(UserHomeActivity.this, EventsActivity.class));
                } else if (id == R.id.nav_reservations) {
                    startActivity(new Intent(UserHomeActivity.this, MyReservationsActivity.class));
                } else if (id == R.id.nav_favorites) {
                    startActivity(new Intent(UserHomeActivity.this, FavoritesActivity.class));
                } else if (id == R.id.nav_special) {
                    startActivity(new Intent(UserHomeActivity.this, SpecialSectionActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(UserHomeActivity.this, ProfileActivity.class));
                } else if (id == R.id.nav_contact) {
                    startActivity(new Intent(UserHomeActivity.this, ContactUsActivity.class));
                } else if (id == R.id.nav_logout) {
                    getSharedPreferences("SmartEventsPrefs", MODE_PRIVATE)
                            .edit()
                            .remove("current_user_email")
                            .commit();

                    Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}
