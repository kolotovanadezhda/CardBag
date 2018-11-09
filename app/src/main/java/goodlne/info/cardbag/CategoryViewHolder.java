package goodlne.info.cardbag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    public TextView txtCategoryName;

    public CategoryViewHolder(@NonNull View itemView, TextView txtCategoryName) {
        super(itemView);
        this.txtCategoryName = txtCategoryName;

    }
}
