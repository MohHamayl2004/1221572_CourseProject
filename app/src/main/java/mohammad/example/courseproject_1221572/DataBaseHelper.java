package mohammad.example.courseproject_1221572;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartEventsDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_EVENTS = "EVENTS";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "CREATE TABLE EVENTS(" +
                        "ID INTEGER PRIMARY KEY, " +
                        "TITLE TEXT, " +
                        "DESCRIPTION TEXT, " +
                        "CATEGORY TEXT, " +
                        "DATE TEXT, " +
                        "TIME TEXT, " +
                        "LOCATION TEXT, " +
                        "SEATS INTEGER, " +
                        "IMAGE TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EVENTS");
        onCreate(sqLiteDatabase);
    }

    public void insertEvent(Event event) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("ID", event.getId());
        contentValues.put("TITLE", event.getTitle());
        contentValues.put("DESCRIPTION", event.getDescription());
        contentValues.put("CATEGORY", event.getCategory());
        contentValues.put("DATE", event.getDate());
        contentValues.put("TIME", event.getTime());
        contentValues.put("LOCATION", event.getLocation());
        contentValues.put("SEATS", event.getSeats());
        contentValues.put("IMAGE", event.getImage());

        sqLiteDatabase.insert(TABLE_EVENTS, null, contentValues);
    }

    public void insertEvents(List<Event> events) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.delete(TABLE_EVENTS, null, null);

        for (int i = 0; i < events.size(); i++) {

            Event event = events.get(i);

            ContentValues contentValues = new ContentValues();

            contentValues.put("ID", event.getId());
            contentValues.put("TITLE", event.getTitle());
            contentValues.put("DESCRIPTION", event.getDescription());
            contentValues.put("CATEGORY", event.getCategory());
            contentValues.put("DATE", event.getDate());
            contentValues.put("TIME", event.getTime());
            contentValues.put("LOCATION", event.getLocation());
            contentValues.put("SEATS", event.getSeats());
            contentValues.put("IMAGE", event.getImage());

            sqLiteDatabase.insert(TABLE_EVENTS, null, contentValues);
        }
    }

    public Cursor getAllEvents() {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        return sqLiteDatabase.rawQuery("SELECT * FROM EVENTS", null);
    }
}