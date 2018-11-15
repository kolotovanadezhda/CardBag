package goodlne.info.cardbag;

import java.io.Serializable;

public class Card implements Serializable {
    private String nameCard;
    private String category;
    private String discount;

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
