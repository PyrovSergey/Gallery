package ru.pyrovsergey.gallery.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;

public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ViewHolder> {
    private List<ThemeWallpaper> mainListWallpaper;
    private FragmentAdapterListener adapterListener;

    public MainFragmentAdapter(List<ThemeWallpaper> mainListWallpaper) {
        this.mainListWallpaper = mainListWallpaper;
        adapterListener = App.getComponent().getMainFragment();
    }

    @NonNull
    @Override
    public MainFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainFragmentAdapter.ViewHolder holder, int position) {
        final ThemeWallpaper wallpaper = mainListWallpaper.get(position);
        holder.imageView.setImageResource(wallpaper.getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterListener.onClickListener(wallpaper.getTitle());
            }
        });
        //holder.textView.setText(wallpapers.getTitle());
        //Picasso.get().load(wallpapers.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mainListWallpaper.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private CardView cardView;
        private ImageView imageView;
        //private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = view.findViewById(R.id.main_fragment_card_view);
            imageView = view.findViewById(R.id.main_fragment_image);
            //textView = view.findViewById(R.id.main_fragment_text_view);
        }
    }
}
