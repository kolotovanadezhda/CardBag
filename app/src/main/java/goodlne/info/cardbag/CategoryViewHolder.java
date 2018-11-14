package goodlne.info.cardbag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView rvCategory;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.rvCategory = itemView.findViewById(R.id.txtCategoryName);

    }
}
