package extensao.ufc.br.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import extensao.ufc.br.model.Event;
import extensao.ufc.br.providers.Formatters;
import extensao.ufc.br.sice.R;

/**
 * Created by alan on 11/22/15.
 */
public class EventListAdapter extends BaseAdapter{

    Context context;
    List<Event> events;
    public EventListAdapter(Context context, List<Event> events){
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return events.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = null;
        view = inflater.inflate(R.layout.event_list_item, null);

        TextView title, subtitle, description, begin, end;
        title = (TextView) view.findViewById(R.id.event_list_item_title);
        subtitle = (TextView) view.findViewById(R.id.event_list_item_subtitle);
        description = (TextView) view.findViewById(R.id.event_list_item_description);
        begin = (TextView) view.findViewById(R.id.event_list_item_begin);
        end = (TextView) view.findViewById(R.id.event_list_item_end);

        Event event = events.get(position);

        title.setText(event.getTitle());
        subtitle.setText(event.getSubtitle());
        description.setText(event.getDescription());
        begin.setText(Formatters.getFormattedString(event.getBegin()));
        end.setText(Formatters.getFormattedString(event.getEnd()));

        return view;
    }
}
