package rwth.lab.android.mensaviewer.model;

import java.io.Serializable;

/**
 * Created by evgenijavstein on 29/04/15.
 */
public class Menu implements Serializable, IListItem {
    private String category;
    private String menu;
    private String price;

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMenu() {
        return this.menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int getId() {
        //TODO
        return 0;
    }
}
