package mohammad.example.courseproject_1221572;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    List<Event> eventsList;
    OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }

    public EventAdapter(List<Event> eventsList, OnEventClickListener listener) {
        this.eventsList = eventsList;
        this.listener = listener;
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        TextView textViewEventTitle;
        TextView textViewEventCategory;
        TextView textViewEventDateTime;
        TextView textViewEventLocation;
        Button buttonViewDetails;

        public EventViewHolder(View itemView) {
            super(itemView);

            textViewEventTitle = (TextView) itemView.findViewById(R.id.textViewEventTitle);
            textViewEventCategory = (TextView) itemView.findViewById(R.id.textViewEventCategory);
            textViewEventDateTime = (TextView) itemView.findViewById(R.id.textViewEventDateTime);
            textViewEventLocation = (TextView) itemView.findViewById(R.id.textViewEventLocation);
            buttonViewDetails = (Button) itemView.findViewById(R.id.buttonViewDetails);
        }
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);

        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {

        final Event event = eventsList.get(position);

        holder.textViewEventTitle.setText(event.getTitle());
        holder.textViewEventCategory.setText("Category: " + event.getCategory());
        holder.textViewEventDateTime.setText("Date: " + event.getDate() + " | Time: " + event.getTime());
        holder.textViewEventLocation.setText("Location: " + event.getLocation());

        holder.buttonViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEventClick(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }
}