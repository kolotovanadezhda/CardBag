package goodlne.info.cardbag;

import java.io.Serializable;

public class Photo implements Serializable {

    private int imageID;

    public Photo(int imageID) {

        this.imageID = imageID;
    }


    public int getImageID() {

        return imageID;
    }
}
