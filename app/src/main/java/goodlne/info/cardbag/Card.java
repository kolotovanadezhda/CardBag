package goodlne.info.cardbag;


import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Card implements Parcelable {
    private String nameCard;
    private String category;
    private String discount;
    private List<Photo> photos;

    protected Card(Parcel in) {
        nameCard = in.readString();
        category = in.readString();
        discount = in.readString();
        photos = in.createTypedArrayList(Photo.CREATOR);
    }
    public Card()
    {
        System.out.println("Constuctor Card\n");
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {

        this.photos = photos;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nameCard);
        dest.writeString(category);
        dest.writeString(discount);
        dest.writeList(photos);

    }
}
