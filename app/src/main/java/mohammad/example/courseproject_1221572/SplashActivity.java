package mohammad.example.courseproject_1221572;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 3000;

    ImageView imageViewLogo;
    TextView textViewSplashTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageViewLogo = (ImageView) findViewById(R.id.imageViewLogo);
        textViewSplashTitle = (TextView) findViewById(R.id.textViewSplashTitle);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.splash_fade);

        imageViewLogo.startAnimation(animation);
        textViewSplashTitle.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }
}