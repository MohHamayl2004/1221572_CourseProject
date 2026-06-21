package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {

    Button buttonManageUsers;
    Button buttonAddAdmin;
    Button buttonManageEvents;
    Button buttonViewReservations;
    Button buttonAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        buttonManageUsers = (Button) findViewById(R.id.buttonManageUsers);
        buttonAddAdmin = (Button) findViewById(R.id.buttonAddAdmin);
        buttonManageEvents = (Button) findViewById(R.id.buttonManageEvents);
        buttonViewReservations = (Button) findViewById(R.id.buttonViewReservations);
        buttonAdminLogout = (Button) findViewById(R.id.buttonAdminLogout);

        buttonManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminUsersActivity.class);
                startActivity(intent);
            }
        });

        buttonAddAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AddAdminActivity.class);
                startActivity(intent);
            }
        });

        buttonManageEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminEventsActivity.class);
                startActivity(intent);
            }
        });

        buttonViewReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, AdminReservationsActivity.class);
                startActivity(intent);
            }
        });

        buttonAdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
