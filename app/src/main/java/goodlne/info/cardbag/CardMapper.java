package goodlne.info.cardbag;

import java.util.ArrayList;
import java.util.List;

public final class CardMapper {
    public static List<Card> map2DataList(List<CardRealm> realmList) {
        List<Card> cards = new ArrayList<>();
        for (CardRealm cardRealm : realmList) {
            Card card = new Card (
                    cardRealm.getId(),
                    cardRealm.getNameCard(),
                    CategoryMapper.categoryMap2Data(cardRealm.getCategory()),
                    cardRealm.getDiscount(),
                    (ArrayList) PhotoMapper.photoMap2Data(cardRealm.getPhotos())
            );
            cards.add(card);
        }
        return cards;
    }
}
