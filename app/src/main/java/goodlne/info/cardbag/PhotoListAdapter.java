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
    private List<Photo> photos;
    //private PhotoListAdapter.onPhotoClickListener clickListener;

    /*public interface onPhotoClickListener{
        void onPhotoClickListener(Card photo);
    }*/

    public PhotoListAdapter(Context context, List<Photo> photos) {
        this.photos = photos;
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
        final Photo photo = photos.get(position);
        viewHolder.ivCardFront.setImageResource(photo.getImageID());
        /*viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                clickListener.onPhotoClickListener(photo);
            }
        });*/
    }

    @Override
    public int getItemCount() {

        return photos.size();
    }
}
