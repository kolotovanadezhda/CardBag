package goodlne.info.cardbag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder>{
    private LayoutInflater inflater;
    private List<Card> cards;
    //private PhotoListAdapter.onPhotoClickListener clickListener;

    /*public interface onPhotoClickListener{
        void onPhotoClickListener(Card photo);
    }*/

    public PhotoListAdapter(Context context, List<Card> cards) {
        this.cards = cards;
        this.inflater = LayoutInflater.from(context);
        //this.clickListener = clickListener;
    }

    @NonNull
    @Override

    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_view_photo_card, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder viewHolder, int position) {
        final Card cardItem = cards.get(position);
        viewHolder.ivCardFront.setImageResource(cardItem.getImageID());
        viewHolder.ivCardBack.setImageResource(cardItem.getImageID());
        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                clickListener.onPhotoClickListener(photo);
            }
        });*/
    }

    @Override
    public int getItemCount() {

        return cards.size();
    }
}
