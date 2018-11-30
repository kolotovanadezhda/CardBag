package goodlne.info.cardbag;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
    private int id;
    private String nameCard;
    private Category category;
    private String discount;
    private ArrayList<Photo> photos;

    public Card()
    {

        System.out.println("Constuctor Card\n");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }
}
