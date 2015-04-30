package rwth.lab.android.mensaviewer;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import rwth.lab.android.mensaviewer.http.WeekPlanGetRequest;
import rwth.lab.android.mensaviewer.model.MensaListItem;

/**
 * Created by я on 30.04.2015.
 */
public class MensaMenuActivity extends Activity {
    private MensaListItem mensa;
    private WeekPlanGetRequest getRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_menu_activity);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mensa = (MensaListItem) extras.getSerializable(ViewerInitialActivity.MENSA_ITEM);
            TextView textView = (TextView) findViewById(R.id.mensaName);
            textView.setText(mensa.getMensaName());

            //TODO continue
            getRequest = new WeekPlanGetRequest(mensa);
        }
    }
}
