package goodlne.info.cardbag;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class AddCard extends AppCompatActivity {

    private Toolbar toolbar;
    private Card card;
    private EditText nameCard;
    private Button category;
    private EditText procDiscount;

    private static final int CHOOSE_CATEGORY = 2;

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

    public boolean onCreateOptionsMenu(Menu  menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_list, menu);
        return true;
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
                    case CHOOSE_CATEGORY:
                        String name = data.getStringExtra("category_name");
                        category.setText(name);

                }
            }
        }

    public void onCategoryClick(View view) {
        card.setNameCard(nameCard.getText().toString());
        card.setCategory(category.getText().toString());
        card.setDiscount(procDiscount.getText().toString());
    }

    public void onAddCard(View view) {
        Intent intent = new Intent(this, CategoryListActivity.class);
        startActivityForResult(intent, CHOOSE_CATEGORY);
    }
}
