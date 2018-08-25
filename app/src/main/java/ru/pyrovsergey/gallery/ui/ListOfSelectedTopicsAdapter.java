package ru.pyrovsergey.gallery.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

class ListOfSelectedTopicsAdapter extends RecyclerView.Adapter<ListOfSelectedTopicsAdapter.ViewHolder> {
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
    public void onBindViewHolder(@NonNull ListOfSelectedTopicsAdapter.ViewHolder holder, int position) {
        final PhotosItem photosItem = photosItems.get(position);
        Picasso.get().load(photosItem.getSrc().getPortrait()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photosItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.list_of_selected_topics_fragment_image);
        }
    }
}
