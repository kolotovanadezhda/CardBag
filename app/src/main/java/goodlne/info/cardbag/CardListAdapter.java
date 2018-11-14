package goodlne.info.cardbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CardListAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private LayoutInflater inflater;
    private Card card;

    public CardListAdapter(Context context, Card card) {
        this.inflater = LayoutInflater.from(context);
        this.card = card;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_view_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardVH, int position) {
        position = 0;
        cardVH.txtNameCard.setText(card.getNameCard());
        cardVH.txtCategoryCard.setText(card.getCategory());
        cardVH.txtDiscountCard.setText("Скидка " + card.getDiscount() + "%");
    }
    @Override
    public int getItemCount() {
        return 1;
    }
}
