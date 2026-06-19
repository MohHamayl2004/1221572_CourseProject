package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageViewProfile;
    Button buttonAddPicture;
    TextView textViewProfileEmail;
    EditText editTextProfileFirstName;
    EditText editTextProfileLastName;
    EditText editTextProfilePhone;
    EditText editTextProfilePassword;
    Button buttonSaveProfile;

    String currentEmail;
    String currentPassword;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewProfile = (ImageView) findViewById(R.id.imageViewProfile);
        buttonAddPicture = (Button) findViewById(R.id.buttonAddPicture);
        textViewProfileEmail = (TextView) findViewById(R.id.textViewProfileEmail);
        editTextProfileFirstName = (EditText) findViewById(R.id.editTextProfileFirstName);
        editTextProfileLastName = (EditText) findViewById(R.id.editTextProfileLastName);
        editTextProfilePhone = (EditText) findViewById(R.id.editTextProfilePhone);
        editTextProfilePassword = (EditText) findViewById(R.id.editTextProfilePassword);
        buttonSaveProfile = (Button) findViewById(R.id.buttonSaveProfile);

        SharedPreferences sharedPreferences = getSharedPreferences("SmartEventsPrefs", MODE_PRIVATE);
        currentEmail = sharedPreferences.getString("current_user_email", "");

        if (currentEmail.equals("")) {
            Toast.makeText(ProfileActivity.this, "No logged in user", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadUserData();

        buttonAddPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    private void loadUserData() {

        DataBaseHelper dataBaseHelper = new DataBaseHelper(ProfileActivity.this);

        Cursor cursor = dataBaseHelper.getUserByEmail(currentEmail);

        if (cursor.moveToFirst()) {
            textViewProfileEmail.setText(cursor.getString(0));
            editTextProfileFirstName.setText(cursor.getString(1));
            editTextProfileLastName.setText(cursor.getString(2));
            currentPassword = cursor.getString(3);
            editTextProfilePhone.setText(cursor.getString(6));
        }

        cursor.close();
    }

    private void saveProfile() {

        String firstName = editTextProfileFirstName.getText().toString();
        String lastName = editTextProfileLastName.getText().toString();
        String phone = editTextProfilePhone.getText().toString();
        String newPassword = editTextProfilePassword.getText().toString();

        if (firstName.length() < 3) {
            Toast.makeText(ProfileActivity.this, "First name must be at least 3 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastName.length() < 3) {
            Toast.makeText(ProfileActivity.this, "Last name must be at least 3 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(ProfileActivity.this, "Phone number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        String passwordToSave = currentPassword;

        if (!newPassword.isEmpty()) {
            if (!isValidPassword(newPassword)) {
                Toast.makeText(ProfileActivity.this, "Password must contain letter, number, and at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            passwordToSave = PasswordUtils.encryptPassword(newPassword);
        }

        DataBaseHelper dataBaseHelper = new DataBaseHelper(ProfileActivity.this);

        boolean updated = dataBaseHelper.updateUser(
                currentEmail,
                firstName,
                lastName,
                phone,
                passwordToSave
        );

        if (updated) {
            currentPassword = passwordToSave;
            editTextProfilePassword.setText("");
            Toast.makeText(ProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProfileActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            imageViewProfile.setImageURI(imageUri);
        }
    }
}
