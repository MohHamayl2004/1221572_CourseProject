package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    CheckBox checkBoxRememberMe;
    Button buttonLogin;
    Button buttonSignUp;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "SmartEventsPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_CURRENT_USER = "current_user_email";

    private static final String ADMIN_EMAIL = "admin@admin.com";
    private static final String ADMIN_PASSWORD = "Admin123!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        checkBoxRememberMe = (CheckBox) findViewById(R.id.checkBoxRememberMe);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonSignUp = (Button) findViewById(R.id.buttonSignUp);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");

        if (!savedEmail.equals("")) {
            editTextEmail.setText(savedEmail);
            checkBoxRememberMe.setChecked(true);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginUser() {

        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.contains("@")) {
            Toast.makeText(LoginActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkBoxRememberMe.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_EMAIL, email);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(KEY_EMAIL);
            editor.commit();
        }

        if (email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD)) {
            Toast.makeText(LoginActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        String encryptedPassword = PasswordUtils.encryptPassword(password);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(LoginActivity.this);

        boolean loginSuccess = dataBaseHelper.checkUserLogin(email, encryptedPassword);

        if (loginSuccess) {
            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

            SharedPreferences.Editor sessionEditor = sharedPreferences.edit();
            sessionEditor.putString(KEY_CURRENT_USER, email);
            sessionEditor.commit();

            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }
}