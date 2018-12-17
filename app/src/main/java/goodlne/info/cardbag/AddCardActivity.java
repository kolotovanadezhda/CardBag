package goodlne.info.cardbag;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    private static final int REQUEST_CODE_FRONT_PHOTO = 1;
    private static final int REQUEST_CODE_BACK_PHOTO = 2;

    ImageView ivPhotoFront, ivPhotoBack;



    private static final int REQUEST_CODE_ADD_CATEGORY = 0;

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


        // Вешаем на кнопку обработчик нажатий
        findViewById(R.id.flFrontPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChooseImageFromGallery(REQUEST_CODE_FRONT_PHOTO);
            }
        });

        findViewById(R.id.flBackPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onChooseImageFromGallery(REQUEST_CODE_BACK_PHOTO);
            }
        });

        ivPhotoFront = findViewById(R.id.ivPhotoFront);
        ivPhotoBack = findViewById(R.id.ivPhotoBack);

        long currentTime = System.currentTimeMillis();
        Photo front = new Photo(currentTime+1);
        Photo back = new Photo(currentTime+2);
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(R.drawable.card_1_front));
        photos.add(new Photo(R.drawable.card_1_front));
        card.setPhotos(photos);

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
            switch (requestCode) {
                case REQUEST_CODE_ADD_CATEGORY:
                    if (resultCode == RESULT_OK) {
                        Category category = data.getParcelableExtra(Category.class.getSimpleName());
                        card.setCategory(category);
                        etCategory.setText(category.getName());
                    }
                    break;
                case REQUEST_CODE_FRONT_PHOTO:
                case REQUEST_CODE_BACK_PHOTO:
                    showImage(requestCode, data);
                    break;
            }
        }

    private void showImage(int requestCode, Intent data) {
        try {
            //Получаем URI изображения, преобразуем его в Bitmap
            //объект и отображаем в элементе ImageView нашего интерфейса:
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

            switch (requestCode){
                case REQUEST_CODE_FRONT_PHOTO:
                    ivPhotoFront.setImageBitmap(selectedImage);
                    break;

                case REQUEST_CODE_BACK_PHOTO:
                    ivPhotoBack.setImageBitmap(selectedImage);
                    break;
            }

        } catch (FileNotFoundException e) {
            // Эта ошибка отобразится в случае если не удалось найти изображение
            e.printStackTrace();
        }
    }

    public void onAddCard(View view) {

        Random random = new Random();
        int id = random.nextInt(200000);
        card.setId(id);

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

    public void onChooseImageFromGallery(int requestCode){

        // Интент для получения всех приложений которые могут отображать изображения
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        // Вызываем системное диалоговое окно для выбора приложения, которое умеет отображать изображения
        // и возвращать выбранную фотографию
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Выберите изображение");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        // Запускаем приложения и ожидаем результат
        startActivityForResult(chooserIntent, requestCode);
    }

    private File createImageFile(long imageID){

    }

}
