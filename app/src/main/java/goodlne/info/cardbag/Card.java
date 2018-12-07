package goodlne.info.cardbag;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class Card implements Parcelable {
    private int id;
    private String nameCard;
    private Category category;
    private String discount;
    private ArrayList photos;

    public Card(int id, String nameCard, Category category, String discount, ArrayList photos) {
        this.id = id;
        this.nameCard = nameCard;
        this.category = category;
        this.discount = discount;
        this.photos = photos;
    }

    public Card() {

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
    protected Card(Parcel in) {
        nameCard = in.readString();
        category = (Category) in.readParcelable(Category.class.getClassLoader());
        discount = in.readString();
        photos = in.readArrayList(Photo.class.getClassLoader());
    }
    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {

            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {

            return new Card[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameCard);
        dest.writeParcelable((Parcelable) category, flags);
        dest.writeString(discount);
        dest.writeList(photos);
    }
}
