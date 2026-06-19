package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    Button buttonCallUs;
    Button buttonEmailUs;
    Button buttonLocateUs;

    private static final String CONTACT_PHONE = "+970599123456";
    private static final String CONTACT_EMAIL = "smartuniversityevents@gmail.com";
    private static final String CONTACT_LOCATION = "geo:0,0?q=Birzeit University";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        buttonCallUs = (Button) findViewById(R.id.buttonCallUs);
        buttonEmailUs = (Button) findViewById(R.id.buttonEmailUs);
        buttonLocateUs = (Button) findViewById(R.id.buttonLocateUs);

        buttonCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + CONTACT_PHONE));
                startIntentSafely(intent, "No phone app found");
            }
        });

        buttonEmailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + CONTACT_EMAIL));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Smart University Events Inquiry");
                startIntentSafely(intent, "No email app found");
            }
        });

        buttonLocateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(CONTACT_LOCATION));
                startIntentSafely(intent, "No maps app found");
            }
        });
    }

    private void startIntentSafely(Intent intent, String errorMessage) {
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(ContactUsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
