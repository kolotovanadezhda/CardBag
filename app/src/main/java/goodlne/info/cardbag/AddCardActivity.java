package goodlne.info.cardbag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmList;


public class AddCardActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Card card;

    private EditText nameCard;
    private EditText etCategory;
    private EditText procDiscount;

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
        etCategory = findViewById(R.id.etCategory);
        procDiscount = findViewById(R.id.procDiscount);

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
                        Category category = data.getParcelableExtra(Category.class.getSimpleName());
                        card.setCategory(category);
                        etCategory.setText(category.getName());

            }
        }
    }
    public void onAddCard(View view) {

        Random random = new Random();
        int id = random.nextInt(200000);
        card.setId(id);

        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.card_1_front));
        photos.add(new Photo(R.drawable.card_1_front));
        card.setPhotos(photos);

        card.setNameCard(nameCard.getText().toString());
        card.setDiscount(procDiscount.getText().toString());

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){

            public void execute(Realm realm){
                    realm.copyToRealmOrUpdate(cardMap2Realm(card));
            }
        });
        realm.close();

        Intent intent = new Intent(this, CardListActivity.class);
        startActivity(intent);
        finish();
    }
    private CardRealm cardMap2Realm(Card card){
        CardRealm cardRealm = new CardRealm();
        cardRealm.setId(card.getId());
        cardRealm.setNameCard(card.getNameCard());
        cardRealm.setDiscount(card.getDiscount());
        cardRealm.setCategory(categoryMap2Realm(card.getCategory()));
        cardRealm.setPhotos(photoMap2Realm(card.getPhotos()));
        return cardRealm;
    }

    private CategoryRealm categoryMap2Realm(Category category) {
        CategoryRealm categoryRealm = new CategoryRealm();
        categoryRealm.setId(category.getId());
        categoryRealm.setName(category.getName());
        return categoryRealm;
    }
    private RealmList<PhotoRealm> photoMap2Realm(List <Photo> photo)
    {
        RealmList <PhotoRealm> photoRealm = new RealmList<>();
        for(Photo photos: photo) {
            PhotoRealm photoRealm1 = new PhotoRealm();
            photoRealm1.setImageID(photos.getImageID());
            photoRealm.add(photoRealm1);
        }
        return photoRealm;
    }

    public void onCategoryClick(View view) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_CATEGORY);
    }
}
