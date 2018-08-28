package ru.pyrovsergey.gallery.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import ru.pyrovsergey.gallery.DetailActivity;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;

class ListOfSelectedTopicsAdapter extends RecyclerView.Adapter<ListOfSelectedTopicsAdapter.ViewHolder> {
    private List<PhotosItem> photosItems;
    private Context context;

    public ListOfSelectedTopicsAdapter(List<PhotosItem> photosItems) {
        this.photosItems = photosItems;
        context = App.getInstance().getContext();
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

        Glide.with(context).load(photosItem.getSrc().getPortrait())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.INVISIBLE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailActivity.startDetailActivity(photosItem.getSrc().getPortrait(), photosItem.getPhotographer());
            }
        });
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
        private ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.list_of_selected_topics_fragment_image);
            progressBar = view.findViewById(R.id.progress_bar);
        }
    }

    public void updateDataAdapter(List<PhotosItem> list) {
        photosItems.addAll(list);
        notifyDataSetChanged();
    }
}
