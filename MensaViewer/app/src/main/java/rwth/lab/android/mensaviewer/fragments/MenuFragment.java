package rwth.lab.android.mensaviewer.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rwth.lab.android.mensaviewer.R;
import rwth.lab.android.mensaviewer.adapters.MenuListAdapter;
import rwth.lab.android.mensaviewer.model.DayPlan;
import rwth.lab.android.mensaviewer.model.Menu;

/**
 * Created by ekaterina on 01.05.2015.
 */
public class MenuFragment extends ListFragment {
    public static final String MENUES_COUNT = "MENUES_COUNT";
    public static final String MENU_PREFIX = "MENU";

    private List<Menu> menues;
    private int menuesCount;
    private MenuListAdapter adapter;

    public static MenuFragment newInstance(DayPlan dayPlan) {
        Bundle args = new Bundle();
        List<Menu> menues = dayPlan.getMenues();
        int menuesSize = menues.size();
        args.putInt(MENUES_COUNT, menuesSize);
        for (int i = 0; i < menuesSize; i++) {
            args.putSerializable(MENU_PREFIX + i, menues.get(i));
        }

        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.menuesCount = getArguments().getInt(MENUES_COUNT);
        this.menues = new ArrayList<Menu>();
        for (int i = 0; i < this.menuesCount; i++) {
            menues.add(i, (Menu) getArguments().getSerializable(MENU_PREFIX + i));
        }
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.layout.simple_list_item_activated_1
                : android.R.layout.simple_list_item_1;
        String[] menuItems = new String[this.menuesCount];
        for (int i = 0; i < menuesCount; i++) {
            menuItems[i] = menues.get(i).getMenu();
        }
        //TODO make it through adapter!!
        //  new MenuListAdapter(getActivity().getApplicationContext());

        // Set the list adapter for this ListFragment
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, menuItems));
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_page, container, false);
//        this.adapter = new MenuListAdapter(getActivity().getApplicationContext());
//        addMenuItemsToAdapter();
//        setListAdapter(this.adapter);
//        return view;
//    }

    private void addMenuItemsToAdapter() {
        if (this.adapter != null && this.menues != null) {
            for (Menu menu : this.menues) {
                this.adapter.add(menu);
            }
        }
    }
}
