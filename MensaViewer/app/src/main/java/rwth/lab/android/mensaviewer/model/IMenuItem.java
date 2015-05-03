package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;

/**
 * Created by ekaterina on 03.05.2015.
 */
public interface IMenuItem extends Serializable {
    String getCategory();

    String getDish();

    String getPrice();
}
