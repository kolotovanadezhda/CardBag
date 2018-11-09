package goodlne.info.cardbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import goodlne.info.cardbag.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter {

    private LayoutInflater inflater;
    private List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> chats) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override

    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_view_chat, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.txtCategoryName.setName(Category.getName());
    }

    public void setAdapter(CategoryListAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    public int getCategories() {

        return categories.size();
    }
    CategoryListAdapter adapter = new CategoryListAdapter(this, DataBaseHelper.getCategories());
    // устанавливаем для списка адаптер
        recyclerView.setAdapter(adapter);
}
