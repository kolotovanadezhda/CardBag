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
    public static CardRealm cardMap2Realm(Card card){
        CardRealm cardRealm = new CardRealm();
        cardRealm.setId(card.getId());
        cardRealm.setNameCard(card.getNameCard());
        cardRealm.setDiscount(card.getDiscount());
        cardRealm.setCategory(CategoryMapper.categoryMap2Realm(card.getCategory()));
        cardRealm.setPhotos(PhotoMapper.photoMap2Realm(card.getPhotos()));
        return cardRealm;
    }
}
