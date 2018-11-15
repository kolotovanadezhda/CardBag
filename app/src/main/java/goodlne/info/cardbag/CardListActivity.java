package goodlne.info.cardbag;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.ArrayList;
import java.util.List;

public class CardListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView cardList;
    private RelativeLayout noCard;
    private RecyclerView recyclerView;
    private List<Card> cards;

    public static final int REQUEST_CODE_ADD_CARD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Мои карты");

        cardList = findViewById(R.id.rvCard);
        cardList.setVisibility(View.GONE);
        noCard = findViewById(R.id.NoCard);

        recyclerView = findViewById(R.id.rvCard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cards = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_list, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_CARD:
                    cardList.setVisibility(View.VISIBLE);
                    noCard.setVisibility(View.GONE);

                    Bundle arg = data.getExtras();
                    if (arg == null) {
                        return;
                    }

                    Card card = (Card) arg.getSerializable(Card.class.getSimpleName());
                    if (card == null) {
                        return;
                    }

                    CardListAdapter adapter = new CardListAdapter(this, cards);
                    recyclerView.setAdapter(adapter);
                    adapter.insertItem(card);
            }
        }

    }

    public void onShow(View view) {
        Intent intent = new Intent(this, AddCard.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CARD);
    }
}
