package goodlne.info.cardbag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

public class CategoryListActivity extends AppCompatActivity implements CategoryListAdapter.onCategoryClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Выбрать категорию");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_search_black_24dp);

       RecyclerView recyclerView = findViewById(R.id.rvCategory);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));

       CategoryListAdapter adapter = new CategoryListAdapter(this, DataBaseHelper.categories, this);
       recyclerView.setAdapter(adapter);
    }

    @Override
    public void onCategoryClick(Category category){
        Intent intent = new Intent(this, AddCard.class);
        intent.putExtra("category_name", category.getName().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
