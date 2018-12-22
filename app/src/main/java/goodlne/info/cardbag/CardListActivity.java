package goodlne.info.cardbag;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class CardListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvCardList;
    private RelativeLayout noCard;
    private List<Card> cards;
    private CardListAdapter adapter;

    public static final int REQUEST_CODE_ADD_CARD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Мои карты");

        noCard = findViewById(R.id.NoCard);

        rvCardList = findViewById(R.id.rvCard);
        rvCardList.setLayoutManager(new LinearLayoutManager(this));

        cards = map2DataList(getCards());

        if (cards == null || cards.isEmpty()){
            rvCardList.setVisibility(View.GONE);
            noCard.setVisibility(View.VISIBLE);
        }
        else {
            rvCardList.setVisibility(View.VISIBLE);
            noCard.setVisibility(View.GONE);
        }

        adapter = new CardListAdapter(this, cards);
        rvCardList.setAdapter(adapter);
    }

    private RealmResults<CardRealm> getCards() {
        return Realm.getDefaultInstance().where(CardRealm.class).findAll();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_CARD:
                    rvCardList.setVisibility(View.VISIBLE);
                    noCard.setVisibility(View.GONE);
                    Bundle arg = data.getExtras();
                    if (arg == null) {
                        return;
                    }
                    Card card = (Card) arg.getParcelable(Card.class.getSimpleName());
                    if (card == null) {
                        return;
                    }
                    adapter.insertItem(card);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_list, menu);
        return true;
    }
    public void onShow(View view) {
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CARD);

    }


    private List<Card> map2DataList(List<CardRealm> realmList) {
        List<Card> cards = new ArrayList<>();
        for (CardRealm cardRealm : realmList) {
            Card card = new Card (
                    cardRealm.getId(),
                    cardRealm.getNameCard(),
                    categoryMap2Data(cardRealm.getCategory()),
                    cardRealm.getDiscount(),
                    (ArrayList) photoMap2Data(cardRealm.getPhotos())
            );
            cards.add(card);
        }
        return cards;
    }

    private List<Photo> photoMap2Data(List<PhotoRealm> realmList) {
        List<Photo> photos = new ArrayList<>();
        for (PhotoRealm photoRealm : realmList) {
            Photo photo = new Photo(
                    photoRealm.getImageID()
            );
            photos.add(photo);
        }
        return photos;
    }

    private Category categoryMap2Data(CategoryRealm categoryRealm) {
        Category category = new Category();
        category.setId(categoryRealm.getId());
        category.setName(categoryRealm.getName());
        return category;
    }
}
