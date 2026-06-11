package mohammad.example.courseproject_1221572;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    Button buttonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        buttonConnect = (Button) findViewById(R.id.buttonConnect);

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(IntroActivity.this, "Connection will be added in next step", Toast.LENGTH_SHORT).show();
            }
        });
    }
}