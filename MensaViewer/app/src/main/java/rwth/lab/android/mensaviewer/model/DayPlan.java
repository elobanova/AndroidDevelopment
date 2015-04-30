package rwth.lab.android.mensaviewer.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class DayPlan {

    private String header;
    private List<Menu> menues;
    private List<Extra> extras;

    public List<Menu> getMenues() {
        return this.menues;
    }

    public void setMenues(List<Menu> menues) {
        this.menues = menues;
    }

    public List<Extra> getExtras() {
        return this.extras;
    }

    public void setExtras(List<Extra> extras) {
        this.extras = extras;
    }

    public String getHeader() {
        return this.header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}