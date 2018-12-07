package goodlne.info.cardbag;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CardRealm extends RealmObject {

    @PrimaryKey
    private int id;
    private String nameCard;
    private CategoryRealm category;
    private String discount;
    private RealmList<PhotoRealm> photos;

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

    public CategoryRealm getCategory() {

        return category;
    }

    public void setCategory(CategoryRealm category) {

        this.category = category;
    }

    public String getDiscount() {

        return discount;
    }

    public void setDiscount(String discount) {

        this.discount = discount;
    }

    public RealmList<PhotoRealm> getPhotos() {

        return photos;
    }

    public void setPhotos(RealmList<PhotoRealm> photos) {
        this.photos = photos;
    }
}
