package rwth.lab.android.mensaviewer.http;

import rwth.lab.android.mensaviewer.model.WeekPlan;

/**
 * Created by ekaterina on 30.04.2015.
 */
public interface OnResponseListener {
    void onPreExecute();

    void onResponse(WeekPlan weekPlan);

    void onError(String errorMessage);
}