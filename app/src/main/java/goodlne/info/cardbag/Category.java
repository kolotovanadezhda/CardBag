package goodlne.info.cardbag;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    private String name;

    public Category(int id, String name)
    {
        this.id=id;
        this.name=name;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public int getId() {

        return id;
    }

    public String getName() {

        return name;
    }

}
