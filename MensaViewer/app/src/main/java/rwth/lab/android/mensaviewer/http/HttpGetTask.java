package rwth.lab.android.mensaviewer.http;

import android.os.AsyncTask;

import rwth.lab.android.mensaviewer.MensaListItem;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class HttpGetTask extends AsyncTask<Void, Void, String> {
    private final MensaListItem mensa;

    public HttpGetTask(MensaListItem mensa) {
        super();
        this.mensa = mensa;
    }

    /**
     * Our result is of type String for now, but it will be a data about menu
     */
    @Override
    protected String doInBackground(Void... params) {

        //TODO execute Http request
        return "TODO";
    }

    /**
     * Our result is of type String for now, but it will be a data about menu
     */
    @Override
    protected void onPostExecute(String result) {
        //TODO close connection and display result
    }
}
