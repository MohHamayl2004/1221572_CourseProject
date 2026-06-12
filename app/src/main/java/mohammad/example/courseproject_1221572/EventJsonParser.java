package mohammad.example.courseproject_1221572;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventJsonParser {

    public static List<Event> getObjectFromJson(String json) {

        List<Event> events;

        try {
            JSONArray jsonArray = new JSONArray(json);
            events = new ArrayList<Event>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                Event event = new Event();

                event.setId(jsonObject.getInt("id"));
                event.setTitle(jsonObject.getString("title"));
                event.setDescription(jsonObject.getString("description"));
                event.setCategory(jsonObject.getString("category"));
                event.setDate(jsonObject.getString("date"));
                event.setTime(jsonObject.getString("time"));
                event.setLocation(jsonObject.getString("location"));
                event.setSeats(jsonObject.getInt("seats"));
                event.setImage(jsonObject.getString("image"));

                events.add(event);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return events;
    }
}