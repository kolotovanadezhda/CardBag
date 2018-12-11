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
    private List<Photo> photos;

    public Card(int id, String nameCard, Category category, String discount, List<Photo> photos) {
        this.id = id;
        this.nameCard = nameCard;
        this.category = category;
        this.discount = discount;
        this.photos = photos;
    }

    public Card() {
        id = 0;
        nameCard = null;
        category = null;
        discount = null;
        photos = null;
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public static Creator<Card> getCREATOR() {
        return CREATOR;
    }

    protected Card(Parcel in) {
        nameCard = in.readString();
        category = (Category) in.readParcelable(Category.class.getClassLoader());
        discount = in.readString();
        photos = new ArrayList<>();
        in.readList(photos, getClass().getClassLoader());
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
