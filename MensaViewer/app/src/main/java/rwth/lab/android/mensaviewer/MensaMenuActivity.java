package rwth.lab.android.mensaviewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

import rwth.lab.android.mensaviewer.http.OnResponseListener;
import rwth.lab.android.mensaviewer.http.WeekPlanGetRequest;
import rwth.lab.android.mensaviewer.model.MensaListItem;
import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by я on 30.04.2015.
 */
public class MensaMenuActivity extends FragmentActivity {
    private MensaListItem mensa;
    private WeekPlanGetRequest getRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_menu_activity);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new MenuFragmentPagerAdapter(getSupportFragmentManager(),
                MensaMenuActivity.this));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mensa = (MensaListItem) extras.getSerializable(ViewerInitialActivity.MENSA_ITEM);

            //TODO continue
            getRequest = new WeekPlanGetRequest(mensa);
            getRequest.setOnResponseListener(new OnResponseListener() {
                @Override
                public void onResponse(WeekPlan weekPlan) {

                }

                @Override
                public void onError(String errorMessage) {

                }
            });
            getRequest.send();
        }
    }
}
