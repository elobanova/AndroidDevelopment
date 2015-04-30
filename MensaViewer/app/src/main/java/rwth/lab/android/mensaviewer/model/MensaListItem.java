package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaListItem implements Serializable {
    private final String mensaName;
    private final String mensaURL;
    private final int mensaId;

    public MensaListItem(String mensaName, String mensaURL, int mensaId) {
        this.mensaName = mensaName;
        this.mensaURL = mensaURL;
        this.mensaId = mensaId;
    }

    public String getMensaName() {
        return this.mensaName;
    }

    public String getMensaURL() {
        return this.mensaURL;
    }

    public int getMensaId() {
        return this.mensaId;
    }
}
