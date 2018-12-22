package goodlne.info.cardbag;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    private File currentImageFile;

    private static final int REQUEST_CODE_FRONT_PHOTO = 2;
    private static final int REQUEST_CODE_BACK_PHOTO = 3;
    private static final int REQUEST_CAMERA_PERMISSION=4;
    private static final int REQUEST_ITEM_ATACHMENT_GALLERY=0;
    private static final int REQUEST_ITEM_ATACHMENT_CAMERA=1;

    ImageView ivPhotoFront, ivPhotoBack;

    private static final int REQUEST_CODE_ADD_CATEGORY = 5;

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

        initDefaultCard();

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnSaveCardClicked(v);
            }
        });

        // Вешаем на кнопку обработчик нажатий
        findViewById(R.id.flFrontPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageSelectionDialog(REQUEST_CODE_FRONT_PHOTO);
            }
        });

        findViewById(R.id.flBackPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageSelectionDialog(REQUEST_CODE_BACK_PHOTO);
            }
        });

        ivPhotoFront = findViewById(R.id.ivPhotoFront);
        ivPhotoBack = findViewById(R.id.ivPhotoBack);


    }

    private void initDefaultCard() {
        card = new Card();
        long currentTime =  System.currentTimeMillis();
        Photo front = new Photo(currentTime+1);
        Photo back = new Photo(currentTime+2);
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(front);
        photos.add(back);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case REQUEST_CODE_FRONT_PHOTO:
                    showImage(requestCode, data);
                    break;

                case REQUEST_CODE_BACK_PHOTO:
                    showImage(requestCode, data);
                    break;

                    case REQUEST_CODE_ADD_CATEGORY:
                        if (resultCode == RESULT_OK) {
                        Category category = data.getParcelableExtra(Category.class.getSimpleName());
                        card.setCategory(category);
                        etCategory.setText(category.getName());
                        break;
                }
        }
    }

    public void onBtnSaveCardClicked(View view) {

        Random random = new Random();
        int id = random.nextInt(200000);
        card.setId(id);

        card.setNameCard(nameCard.getText().toString());
        card.setDiscount(procDiscount.getText().toString());

        String folder = getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/";

        copyPhoto(

                 folder + card.getPhotos().get(0).getImageID()+ ".jpg",
                getBitmap(ivPhotoFront)
        );
        copyPhoto(
                folder + card.getPhotos().get(1).getImageID()+".jpg",
                getBitmap(ivPhotoBack)
        );
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction(){

            public void execute(Realm realm){
                    realm.copyToRealmOrUpdate(cardMap2Realm(card));
            }
        });
        realm.close();

      Intent intent = new Intent();
      intent.putExtra(Card.class.getSimpleName(), card);
      setResult(RESULT_OK, intent);
      finish();
    }

    private Bitmap getBitmap(ImageView iv) {
        iv.invalidate();
        BitmapDrawable btmpDrawable = (BitmapDrawable) iv.getDrawable();
        return btmpDrawable.getBitmap();
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    private Bitmap getBitmap() {
        Bitmap bitmap = BitmapFactory.decodeFile(currentImageFile.getAbsolutePath());
        try {
            ExifInterface ei = new ExifInterface(currentImageFile.getAbsolutePath());

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(bitmap, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(bitmap, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(bitmap, 270);
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
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

    private void showImage(int requestCode, Intent data) {

        if(data==null || data.getData() == null){
            switch (requestCode) {
                case REQUEST_CODE_FRONT_PHOTO:
                    ivPhotoFront.setImageBitmap(getBitmap());

                    break;
                case REQUEST_CODE_BACK_PHOTO:
                    ivPhotoBack.setImageBitmap(getBitmap());
                    break;

            }
        }
        else{
            try {
                //Получаем URI изображения, преобразуем его в Bitmap
                //объект и отображаем в элементе ImageView нашего интерфейса:
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                switch (requestCode) {
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
    }

    private void showImageSelectionDialog(final int requestCode) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setItems(R.array.attachment_variants, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case REQUEST_ITEM_ATACHMENT_GALLERY:
                                onChooseImageFromGallery(requestCode);
                                break;

                            case REQUEST_ITEM_ATACHMENT_CAMERA:
                                takePhoto(requestCode);
                        }
                    }
                })
                .create();

        if (!isFinishing()) {
            alertDialog.show();
        }
    }

    private void takePhoto(int requestCode) {
        long currentTime = System.currentTimeMillis();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this,
                    "На вашем устройстве недоступна камера",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }


        // Создаём файл для изображения
        currentImageFile = createImageFile(currentTime);

        if (currentImageFile != null) {
            // Если файл создался — получаем его URI
            Uri imageUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    currentImageFile
            );

            // Передаём URI в камеру
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, requestCode);
        }
    }

    private File createImageFile(long time){
        // Генерируем имя файла
        String filename = time + ".jpg";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = new File(storageDir, filename);
        try {
            if (image.createNewFile()) {
                return image;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private boolean checkCameraGrantedPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean doRequestPermission(int requestCode) {
        // Запрашиваем права доступа только на Android 6 и выше
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.CAMERA},
                    requestCode
            );
            return true;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Здесь обрабатывается результат выдачи прав доступа, которые мы запросили
        switch (requestCode) {
            case REQUEST_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePhoto(requestCode);
                }
                break;
        }
    }

    private void copyPhoto(String fileName, Bitmap img) {
        try (FileOutputStream out = new FileOutputStream(fileName)) {
            img.compress(Bitmap.CompressFormat.JPEG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
