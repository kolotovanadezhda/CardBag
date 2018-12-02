package goodlne.info.cardbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import goodlne.info.cardbag.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private LayoutInflater inflater;
    private List<Category> categories;
    private onCategoryClickListener clickListener;

    public interface onCategoryClickListener{
        void onCategoryClick (Category category);
    }

    public CategoryListAdapter(Context context, List<Category> categories, onCategoryClickListener clickListener) {
        this.categories = categories;
        this.inflater = LayoutInflater.from(context);
        this.clickListener = clickListener;
}

    @NonNull
    @Override

    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_view_category, viewGroup, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder viewHolder, int position) {
        final Category category = categories.get(position);
        viewHolder.rvCategory.setText(category.getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                clickListener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {

        return categories.size();
    }
}
