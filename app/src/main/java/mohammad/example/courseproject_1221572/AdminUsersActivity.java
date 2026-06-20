package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminUsersActivity extends AppCompatActivity {

    LinearLayout linearLayoutUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        linearLayoutUsers = (LinearLayout) findViewById(R.id.linearLayoutUsers);

        loadUsers();
    }

    private void loadUsers() {

        linearLayoutUsers.removeAllViews();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AdminUsersActivity.this);

        Cursor cursor = dataBaseHelper.getAllUsers();

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(AdminUsersActivity.this);
            textView.setText("No users found.");
            textView.setTextSize(18);
            textView.setPadding(10, 20, 10, 20);

            linearLayoutUsers.addView(textView);

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            final String email = cursor.getString(0);
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String major = cursor.getString(5);
            String phone = cursor.getString(6);

            TextView textViewUser = new TextView(AdminUsersActivity.this);

            String userInfo =
                    "Email: " + email + "\n" +
                            "Name: " + firstName + " " + lastName + "\n" +
                            "Major: " + major + "\n" +
                            "Phone: " + phone;

            textViewUser.setText(userInfo);
            textViewUser.setTextSize(16);
            textViewUser.setPadding(15, 15, 15, 15);
            textViewUser.setBackgroundColor(0xFFEFEFEF);

            Button buttonDelete = new Button(AdminUsersActivity.this);
            buttonDelete.setText("Delete User");

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBaseHelper helper = new DataBaseHelper(AdminUsersActivity.this);
                    helper.deleteUser(email);

                    Toast.makeText(
                            AdminUsersActivity.this,
                            "User deleted",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadUsers();
                }
            });

            linearLayoutUsers.addView(textViewUser);
            linearLayoutUsers.addView(buttonDelete);
        }

        cursor.close();
    }
}
