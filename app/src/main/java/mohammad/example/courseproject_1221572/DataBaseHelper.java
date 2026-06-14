package mohammad.example.courseproject_1221572;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartEventsDB";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_EVENTS = "EVENTS";
    private static final String TABLE_USERS = "USERS";

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

        sqLiteDatabase.execSQL(
                "CREATE TABLE USERS(" +
                        "EMAIL TEXT PRIMARY KEY, " +
                        "FIRST_NAME TEXT, " +
                        "LAST_NAME TEXT, " +
                        "PASSWORD TEXT, " +
                        "GENDER TEXT, " +
                        "MAJOR TEXT, " +
                        "PHONE TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS EVENTS");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USERS");
        onCreate(sqLiteDatabase);
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

    public boolean insertUser(String email, String firstName, String lastName,
                              String password, String gender, String major, String phone) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("EMAIL", email);
        contentValues.put("FIRST_NAME", firstName);
        contentValues.put("LAST_NAME", lastName);
        contentValues.put("PASSWORD", password);
        contentValues.put("GENDER", gender);
        contentValues.put("MAJOR", major);
        contentValues.put("PHONE", phone);

        long result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues);

        return result != -1;
    }

    public boolean checkUserLogin(String email, String password) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM USERS WHERE EMAIL = ? AND PASSWORD = ?",
                new String[]{email, password}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }

    public boolean checkEmailExists(String email) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT * FROM USERS WHERE EMAIL = ?",
                new String[]{email}
        );

        boolean exists = cursor.getCount() > 0;

        cursor.close();

        return exists;
    }
}