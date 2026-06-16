package mohammad.example.courseproject_1221572;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FavoritesActivity extends AppCompatActivity {

    LinearLayout linearLayoutFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        linearLayoutFavorites = (LinearLayout) findViewById(R.id.linearLayoutFavorites);

        loadFavorites();
    }

    private void loadFavorites() {

        linearLayoutFavorites.removeAllViews();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(FavoritesActivity.this);

        Cursor cursor = dataBaseHelper.getAllFavorites();

        if (cursor.getCount() == 0) {
            TextView textView = new TextView(FavoritesActivity.this);
            textView.setText("No favorite events yet.");
            textView.setTextSize(18);
            textView.setPadding(10, 20, 10, 20);

            linearLayoutFavorites.addView(textView);

            cursor.close();
            return;
        }

        while (cursor.moveToNext()) {

            final int eventId = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String category = cursor.getString(3);
            String date = cursor.getString(4);
            String time = cursor.getString(5);
            String location = cursor.getString(6);
            int seats = cursor.getInt(7);

            TextView textViewEvent = new TextView(FavoritesActivity.this);

            String eventInfo =
                    "Title: " + title + "\n" +
                            "Category: " + category + "\n" +
                            "Description: " + description + "\n" +
                            "Date: " + date + "\n" +
                            "Time: " + time + "\n" +
                            "Location: " + location + "\n" +
                            "Seats: " + seats;

            textViewEvent.setText(eventInfo);
            textViewEvent.setTextSize(16);
            textViewEvent.setPadding(15, 15, 15, 15);
            textViewEvent.setBackgroundColor(0xFFEFEFEF);

            Button buttonRemove = new Button(FavoritesActivity.this);
            buttonRemove.setText("Remove From Favorites");

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DataBaseHelper helper = new DataBaseHelper(FavoritesActivity.this);
                    helper.deleteFavorite(eventId);

                    Toast.makeText(
                            FavoritesActivity.this,
                            "Removed from favorites",
                            Toast.LENGTH_SHORT
                    ).show();

                    loadFavorites();
                }
            });

            linearLayoutFavorites.addView(textViewEvent);
            linearLayoutFavorites.addView(buttonRemove);
        }

        cursor.close();
    }
}