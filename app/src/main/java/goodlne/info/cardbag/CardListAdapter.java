package goodlne.info.cardbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class CardListAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private LayoutInflater inflater;
    private List<Card> cards;

    public CardListAdapter(Context context, List<Card> cards) {
        this.inflater = LayoutInflater.from(context);
        this.cards = cards;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_view_card, viewGroup, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardVH, int position) {
        final Card cardItem = cards.get(position);
        cardVH.txtNameCard.setText(cardItem.getNameCard());
        cardVH.txtCategoryCard.setText(cardItem.getCategory());
        cardVH.txtDiscountCard.setText("Скидка " + cardItem.getDiscount() + "%");
    }
    public void insertItem(Card item) {
        cards.add(item);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return cards.size();
    }
}
