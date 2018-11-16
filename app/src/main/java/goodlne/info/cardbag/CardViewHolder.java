package goodlne.info.cardbag;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public TextView txtNameCard;
    public TextView txtCategoryCard;
    public TextView txtDiscountCard;

    CardViewHolder (@NonNull View itemView) {
        super(itemView);
        txtNameCard = itemView.findViewById(R.id.txtNameCard);
        txtCategoryCard = itemView.findViewById(R.id.txtCategory);
        txtDiscountCard = itemView.findViewById(R.id.txtDiscount);
    }

}
