package goodlne.info.cardbag;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoViewHolder>{
    private LayoutInflater inflater;
    private List<Photo> photos;
    Context context;

    public PhotoListAdapter(Context context, List<Photo> photos) {
        this.photos = photos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
        File imgFile = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" +
                        photo.getImageID() + ".jpg"
        );

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewHolder.ivCardFront.setImageBitmap(myBitmap);
        }

    }


    @Override
    public int getItemCount() {

        return photos.size();
    }
}
