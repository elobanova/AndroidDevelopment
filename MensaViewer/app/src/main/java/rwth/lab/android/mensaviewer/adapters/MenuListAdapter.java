package rwth.lab.android.mensaviewer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.model.Menu;

/**
 * Created by я on 01.05.2015.
 */
public class MenuListAdapter extends AbstractListAdapter<Menu> {

    public MenuListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TextView text;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            view = inflater.inflate(R.layout.menu_list_item, parent, false);
        } else {
            view = convertView;
        }

        if (view instanceof TextView) {
            text = (TextView) view;
            Menu item = (Menu) getItem(position);
            text.setText(item.getMenu());
        }
        return view;
    }
}
