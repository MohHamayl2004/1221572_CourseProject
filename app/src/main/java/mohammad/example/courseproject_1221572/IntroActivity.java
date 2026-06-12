package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class IntroActivity extends AppCompatActivity {

    Button buttonConnect;
    ProgressBar progressBarConnect;

    private static final String EVENTS_URL =
            "https://example.com/events.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        progressBarConnect = (ProgressBar) findViewById(R.id.progressBarConnect);

        setProgress(false);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionAsyncTask connectionAsyncTask =
                        new ConnectionAsyncTask(IntroActivity.this);

                connectionAsyncTask.execute(EVENTS_URL);
            }
        });
    }

    public void setButtonText(String text) {
        buttonConnect.setText(text);
    }

    public void setProgress(boolean progress) {
        if (progress) {
            progressBarConnect.setVisibility(View.VISIBLE);
        } else {
            progressBarConnect.setVisibility(View.GONE);
        }
    }

    public void connectionFailed() {
        setButtonText("Connect");
        Toast.makeText(
                IntroActivity.this,
                "Connection failed. Please try again.",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void connectionSuccess(List<Event> events) {
        setButtonText("Connected");

        DataBaseHelper dataBaseHelper = new DataBaseHelper(IntroActivity.this);
        dataBaseHelper.insertEvents(events);

        Toast.makeText(
                IntroActivity.this,
                "Connected successfully. Events saved: " + events.size(),
                Toast.LENGTH_SHORT
        ).show();

        Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}