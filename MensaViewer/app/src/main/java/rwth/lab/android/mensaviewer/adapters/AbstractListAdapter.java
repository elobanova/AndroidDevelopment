package rwth.lab.android.mensaviewer.adapters;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import rwth.lab.android.mensaviewer.model.IListItem;

/**
 * Created by я on 01.05.2015.
 */
public abstract class AbstractListAdapter<T extends IListItem> extends BaseAdapter {
    protected final List<T> items = new ArrayList<T>();
    protected final Context context;

    public AbstractListAdapter(Context context) {
        this.context = context;
    }

    public void add(T item) {
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
        return items.get(pos).getId();
    }
}
