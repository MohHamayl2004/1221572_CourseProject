package mohammad.example.courseproject_1221572;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextRegisterEmail;
    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextRegisterPassword;
    EditText editTextConfirmPassword;
    EditText editTextPhone;

    Spinner spinnerGender;
    Spinner spinnerMajor;

    Button buttonRegister;
    Button buttonBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextRegisterEmail = (EditText) findViewById(R.id.editTextRegisterEmail);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextRegisterPassword = (EditText) findViewById(R.id.editTextRegisterPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
        spinnerMajor = (Spinner) findViewById(R.id.spinnerMajor);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonBackToLogin = (Button) findViewById(R.id.buttonBackToLogin);

        String[] genderOptions = {"Male", "Female"};
        ArrayAdapter<String> genderAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderOptions);
        spinnerGender.setAdapter(genderAdapter);

        String[] majorOptions = {"Computer Engineering", "Electrical Engineering", "Computer Science", "Other"};
        ArrayAdapter<String> majorAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, majorOptions);
        spinnerMajor.setAdapter(majorAdapter);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        buttonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {

        String email = editTextRegisterEmail.getText().toString();
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String password = editTextRegisterPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String phone = editTextPhone.getText().toString();

        if (email.isEmpty() || !email.contains("@")) {
            Toast.makeText(RegisterActivity.this, "Enter valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (firstName.length() < 3) {
            Toast.makeText(RegisterActivity.this, "First name must be at least 3 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastName.length() < 3) {
            Toast.makeText(RegisterActivity.this, "Last name must be at least 3 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(RegisterActivity.this, "Password must contain letter, number, and at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(RegisterActivity.this, "Registration database will be added next", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
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