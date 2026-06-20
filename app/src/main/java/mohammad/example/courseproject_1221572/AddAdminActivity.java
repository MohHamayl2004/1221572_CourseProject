package mohammad.example.courseproject_1221572;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddAdminActivity extends AppCompatActivity {

    EditText editTextAdminEmail;
    EditText editTextAdminPassword;
    Button buttonCreateAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_admin);

        editTextAdminEmail = (EditText) findViewById(R.id.editTextAdminEmail);
        editTextAdminPassword = (EditText) findViewById(R.id.editTextAdminPassword);
        buttonCreateAdmin = (Button) findViewById(R.id.buttonCreateAdmin);

        buttonCreateAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAdmin();
            }
        });
    }

    private void createAdmin() {

        String email = editTextAdminEmail.getText().toString();
        String password = editTextAdminPassword.getText().toString();

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(AddAdminActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(AddAdminActivity.this, "Password must contain letter, number, and at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(AddAdminActivity.this);

        if (dataBaseHelper.checkEmailExists(email)) {
            Toast.makeText(AddAdminActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        String encryptedPassword = PasswordUtils.encryptPassword(password);

        boolean inserted = dataBaseHelper.insertUser(
                email,
                "Admin",
                "Admin",
                encryptedPassword,
                "-",
                "-",
                "-",
                1
        );

        if (inserted) {
            Toast.makeText(AddAdminActivity.this, "Admin created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(AddAdminActivity.this, "Failed to create admin", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidPassword(String password) {

        boolean hasLetter = false;
        boolean hasNumber = false;

        if (password.length() < 6) {
            return false;
        }

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);

            if (Character.isLetter(c)) {
                hasLetter = true;
            }

            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }

        return hasLetter && hasNumber;
    }
}
