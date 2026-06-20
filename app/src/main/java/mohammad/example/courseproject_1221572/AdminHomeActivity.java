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
                Toast.makeText(AdminHomeActivity.this, "Add Admin will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        buttonManageEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminHomeActivity.this, "Manage Events will be added later", Toast.LENGTH_SHORT).show();
            }
        });

        buttonViewReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AdminHomeActivity.this, "View Reservations will be added later", Toast.LENGTH_SHORT).show();
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
