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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ViewerInitialActivity extends ListActivity {

    private static final String[] LIST_OF_MENSAS = {"Mensa Ahornstraße", "Mensa Vita"};
    private static final int MENU_INFORMATION = Menu.FIRST;
    private static final String TAG = "Mensa-Viewer";
    private static final String URL = "http://www.studentenwerk-aachen.de/de/gastronomie/speiseplaene.html";

    private DialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new Adapter containing a list of mensas
        // Set the adapter on this ListActivity's built-in ListView
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item,
                LIST_OF_MENSAS));

        ListView lv = getListView();

        // Enable filtering when the user types in the virtual keyboard
        lv.setTextFilterEnabled(true);

        // Set an setOnItemClickListener on the ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // TODO: Call HTTP GET
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
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
        mDialog = InformationDialogFragment.newInstance();
        mDialog.show(getFragmentManager(), "Alert");
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
