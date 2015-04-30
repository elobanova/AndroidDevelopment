package rwth.lab.android.mensaviewer;

/**
 * Created by ekaterina on 30.04.2015.
 */
public class MensaListItem {
    private final String mensaName;
    private final int mensaId;

    public MensaListItem(String mensaName, int mensaId) {
        this.mensaName = mensaName;
        this.mensaId = mensaId;
    }

    String getMensaName() {
        return this.mensaName;
    }

    int getMensaId() {
        return this.mensaId;
    }
}
