package goodlne.info.cardbag;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;


public class AddCardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Card card;

    private EditText nameCard;
    private EditText procDiscount;
    private Button category;


    private static final int REQUEST_CODE_ADD_CATEGORY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Добавить карту");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nameCard = findViewById(R.id.nameCard);
        procDiscount = findViewById(R.id.procDiscount);
        category = findViewById(R.id.btnCategory);

        card = new Card();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADD_CATEGORY:
                    String name = data.getStringExtra("name");
                    category.setText(name);

            }
        }
    }
    public void onAddCard(View view) {

        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.card_1_front));
        photos.add(new Photo(R.drawable.card_1_front));
        card.setPhotos(photos);

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {
            }

            public void execut(Realm realm){
                realm.copyToRealmOrUpdate(map2Realm(card));
                }
        });

        //card.setNameCard(nameCard.getText().toString());
        //card.setCategory(category.getText().toString());
        //card.setDiscount(procDiscount.getText().toString());

        Random random = new Random();
        int id = random.nextInt(200000);
        category.setId(id);

        Intent intent = new Intent(this, CardListActivity.class);
        intent.putExtra(Card.class.getSimpleName(), card);
        setResult(RESULT_OK, intent);
        finish();
    }
    private CardRealm map2Realm(Card card){
        CardRealm cardRealm = new CardRealm();
        cardRealm.setNameCard(card.getNameCard());
        cardRealm.setDiscount(card.getDiscount());
        cardRealm.setCategory(categoryMap2Ream(card.getCategory()));
        return cardRealm;
    }

    private CategoryRealm categoryMap2Ream(Category category) {
        CategoryRealm categoryRealm = new CategoryRealm();
        categoryRealm.setId(category.getId());
        categoryRealm.setName(category.getName());
        return categoryRealm;
    }

    public void onCategoryClick(View view) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CATEGORY);
    }
}
