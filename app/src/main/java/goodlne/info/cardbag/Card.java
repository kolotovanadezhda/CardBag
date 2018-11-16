package goodlne.info.cardbag;


import android.provider.ContactsContract;

import java.io.Serializable;
import java.util.List;

public class Card implements Serializable {
    private String nameCard;
    private String category;
    private String discount;
    private int imageID;

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getNameCard() {

        return nameCard;

    }

    public void setNameCard(String nameCard) {

        this.nameCard = nameCard;
    }

    public String getCategory() {

        return category;
    }

    public void setCategory(String category) {

        this.category = category;
    }

    public String getDiscount() {

        return discount;
    }

    public void setDiscount(String discount) {

        this.discount = discount;
    }

}
