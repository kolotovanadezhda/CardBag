package goodlne.info.cardbag;

import java.util.ArrayList;
import java.util.List;

public final class PhotoMapper {
    public static List<Photo> photoMap2Data(List<PhotoRealm> realmList) {
        List<Photo> photos = new ArrayList<>();
        for (PhotoRealm photoRealm : realmList) {
            Photo photo = new Photo(
                    photoRealm.getImageID()
            );
            photos.add(photo);
        }
        return photos;
    }
}
