package rwth.lab.android.mensaviewer;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import rwth.lab.android.mensaviewer.http.OnResponseListener;
import rwth.lab.android.mensaviewer.http.WeekPlanGetRequest;
import rwth.lab.android.mensaviewer.model.MensaListItem;
import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class ViewerInitialActivity extends ListActivity {

    private static final int MENU_INFORMATION = Menu.FIRST;
    private static final String TAG = "Mensa-Viewer";
    private static final String URL = "https://laboratory.comsys.rwth-aachen.de/groups/evgenijandkate";

    private DialogFragment dialog;
    private MensaListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a new Adapter containing a list of mensas
        // Set the adapter on this ListActivity's built-in ListView
        this.adapter = new MensaListAdapter(getApplicationContext());
        addMensaItemsToAdapter();
        setListAdapter(this.adapter);

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object item = adapter.getItem(position);
                if (item instanceof MensaListItem) {
                    MensaListItem mensaListItem = (MensaListItem) item;
//                    WeekPlanGetRequest httpGet = new WeekPlanGetRequest(mensaListItem);
//                    httpGet.setOnResponseListener(new OnResponseListener() {
//                        @Override
//                        public void onResponse(WeekPlan weekPlan) {
//
//                        }
//
//                        @Override
//                        public void onError(String errorMessage) {
//
//                        }
//                    });
//                    httpGet.send();
                }
            }
        });
    }

    private void addMensaItemsToAdapter() {
        if (this.adapter != null) {
            //TODO: retrieve a list of mensas from website, can be left as it is now
            this.adapter.add(new MensaListItem("Mensa Ahornstraße", "http://www.studentenwerk-aachen.de/speiseplaene/ahornstrasse-w.html", 0));
            this.adapter.add(new MensaListItem("Mensa Vita", "http://www.studentenwerk-aachen.de/speiseplaene/vita-w.html", 1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        menu.add(Menu.NONE, MENU_INFORMATION, Menu.NONE, R.string.information);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_INFORMATION:
                showDialogFragment();
                return true;
            default:
                return false;
        }
    }

    private void showDialogFragment() {
        this.dialog = InformationDialogFragment.newInstance();
        this.dialog.show(getFragmentManager(), "Alert");
    }

    /**
     * Start a Browser Activity to view a web page or its URL.
     */
    public void startImplicitActivation() {

        Log.i(TAG, "Entered startImplicitActivation()");

        // Create a base intent for viewing a URL
        Uri webpage = Uri.parse(URL);
        Intent baseIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(baseIntent);
    }
}
