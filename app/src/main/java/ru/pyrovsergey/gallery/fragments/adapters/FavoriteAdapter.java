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

import es.dmoral.toasty.Toasty;
import ru.pyrovsergey.gallery.DetailActivity;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<FavoriteWallpaper> wallpaperList;

    public FavoriteAdapter(List<FavoriteWallpaper> wallpaperList) {
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        final FavoriteWallpaper favoriteWallpaper = wallpaperList.get(position);
        Picasso.get().load(favoriteWallpaper.getSmallUrl()).placeholder(R.drawable.placeholder).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.isInternetAvailable()) {
                    DetailActivity.startDetailActivity(favoriteWallpaper);
                } else {
                    Toasty.error(App.getInstance(), App.getInstance().getString(R.string.no_internet_connection) +
                            "\n" + App.getInstance().getString(R.string.check_connection_settings), 0, true).show();
                }
            }
        });
        setLeftAnimation(holder.cardView);
    }

    private void setLeftAnimation(CardView cardView) {
        Animation animation = AnimationUtils.loadAnimation(cardView.getContext(), android.R.anim.slide_in_left);
        cardView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    public void updateDataAdapter(List<FavoriteWallpaper> list) {
        clear();
        wallpaperList = list;
        notifyDataSetChanged();
    }

    public void clear() {
        wallpaperList.clear();
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
}
