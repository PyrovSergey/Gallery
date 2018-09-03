package ru.pyrovsergey.gallery.fragments.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.pyrovsergey.gallery.DetailActivity;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

public class ListOfSelectedTopicsAdapter extends RecyclerView.Adapter<ListOfSelectedTopicsAdapter.ViewHolder> {
    private List<PhotosItem> photosItems;

    public ListOfSelectedTopicsAdapter(List<PhotosItem> photosItems) {
        this.photosItems = photosItems;
    }

    @NonNull
    @Override
    public ListOfSelectedTopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListOfSelectedTopicsAdapter.ViewHolder holder, int position) {
        final PhotosItem photosItem = photosItems.get(position);

        Picasso.get().load(photosItem.getSrc().getSmall()).placeholder(R.drawable.placeholder).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteWallpaper favoriteWallpaper = new FavoriteWallpaper();
                favoriteWallpaper.setId(photosItem.getId());
                favoriteWallpaper.setUrl(photosItem.getSrc().getPortrait());
                favoriteWallpaper.setAuthor(photosItem.getPhotographer());
                favoriteWallpaper.setSmallUrl(photosItem.getSrc().getSmall());
                DetailActivity.startDetailActivity(favoriteWallpaper);
            }
        });
        setLeftAnimation(holder.cardView);
    }

    private void setLeftAnimation(CardView cardView) {
        Animation animation = AnimationUtils.loadAnimation(App.getInstance().getContext(), android.R.anim.slide_in_left);
        cardView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return photosItems.size();
    }

    public void clear() {
        photosItems.clear();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.list_of_selected_topics_fragment_image);
            cardView = view.findViewById(R.id.list_of_selected_topics_fragment_card_view);
        }
    }

    public void updateDataAdapter(List<PhotosItem> list) {
        int startPosition = photosItems.size();
        int lastPosition = list.size();
        photosItems.addAll(list);
        notifyItemRangeInserted(startPosition, startPosition + lastPosition);
    }
}
