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

import static goodlne.info.cardbag.CardMapper.map2DataList;
import static io.realm.Realm.getDefaultInstance;

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


        adapter = new CardListAdapter(this, cards);
        rvCardList.setAdapter(adapter);

        loadCardList();
    }
    public void showCardList(boolean enableList ) {
        // Если enableList равно true, то отобразить список карточек (View.VISIBLE)
        rvCardList.setVisibility(enableList ? View.VISIBLE : View.GONE);
    }
    private void loadCardList() {
        RealmResults<CardRealm> result = getDefaultInstance().where(CardRealm.class).findAll();
        if (result.isEmpty()) {
            // Карточек нет
            showCardList(false);
            return;
        }

        List<Card> cards = CardMapper.map2DataList(result);
        adapter.setData(cards);
        showCardList(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
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

}
