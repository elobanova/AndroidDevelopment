package rwth.lab.android.mensaviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaListAdapter extends BaseAdapter {
    private final List<MensaListItem> items = new ArrayList<MensaListItem>();
    private final Context context;

    public MensaListAdapter(Context context) {
        this.context = context;
    }

    public void add(MensaListItem item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int pos) {
        return items.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return items.get(pos).getMensaId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof TextView) {
            text = (TextView) view;
            MensaListItem item = (MensaListItem) getItem(position);
            text.setText(item.getMensaName());
        }
        return view;
    }
}
