package rwth.lab.android.mensaviewer.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import rwth.lab.android.mensaviewer.adapters.MenuListAdapter;
import rwth.lab.android.mensaviewer.model.DayPlan;
import rwth.lab.android.mensaviewer.model.Menu;

/**
 * Created by ekaterina on 01.05.2015.
 */
public class MenuFragment extends ListFragment {
    public static final String MENUES_KEY = "MENUES";

    private List<Menu> menues;
    private MenuListAdapter adapter;

    public static MenuFragment newInstance(DayPlan dayPlan) {
        Bundle args = new Bundle();
        List<Menu> menues = dayPlan.getMenues();
        args.putSerializable(MENUES_KEY, (Serializable) menues);

        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.menues = (ArrayList) getArguments().getSerializable(MENUES_KEY);
        this.adapter = new MenuListAdapter(getActivity().getApplicationContext());
        addMenuItemsToAdapter();
        setListAdapter(this.adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
    }

    private void addMenuItemsToAdapter() {
        if (this.adapter != null && this.menues != null) {
            for (Menu menu : this.menues) {
                this.adapter.add(menu);
            }
        }
    }
}
