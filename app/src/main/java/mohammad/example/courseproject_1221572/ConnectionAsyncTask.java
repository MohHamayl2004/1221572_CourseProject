package mohammad.example.courseproject_1221572;

import android.app.Activity;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    Activity activity;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        ((IntroActivity) activity).setButtonText("Connecting...");
        ((IntroActivity) activity).setProgress(true);
    }

    @Override
    protected String doInBackground(String... params) {
        String data = HttpManager.getData(params[0]);
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        ((IntroActivity) activity).setProgress(false);

        if (s == null) {
            ((IntroActivity) activity).connectionFailed();
            return;
        }

        List<Event> events = EventJsonParser.getObjectFromJson(s);

        if (events == null || events.size() < 10) {
            ((IntroActivity) activity).connectionFailed();
            return;
        }

        ((IntroActivity) activity).connectionSuccess(events);
    }
}