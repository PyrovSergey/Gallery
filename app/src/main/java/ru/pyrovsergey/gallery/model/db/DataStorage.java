package ru.pyrovsergey.gallery.model.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.FavoriteWallpaper;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.db.contracts.DataStorageContract;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.presenters.listeners.DetailListener;
import ru.pyrovsergey.gallery.presenters.listeners.FavoriteListener;

public class DataStorage implements DataStorageContract {

    private List<ThemeWallpaper> themeWallpapers;
    private List<PhotosItem> photosItems;
    private List<FavoriteWallpaper> wallpaperList;
    private FavoriteWallpaperDao favoriteWallpaperDao;

    public DataStorage() {
        AppGalleryDatabase database = App.getDatabase();
        favoriteWallpaperDao = database.favoriteWallpaperDao();
        themeWallpapers = new ArrayList<>();
        initThemeWallpapersList();
        photosItems = new ArrayList<>();
        wallpaperList = new ArrayList<>();
    }

    @SuppressLint("CheckResult")
    @Override
    public void isAddedToBookmarks(int id, final DetailListener listener) {
        favoriteWallpaperDao.getById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FavoriteWallpaper>() {
                    @Override
                    public void accept(FavoriteWallpaper favorite) throws Exception {
                        listener.positiveResultCheckIsAddToBookmarks();
                    }
                });
    }

    @Override
    public void deleteBookmark(final FavoriteWallpaper favorite, final DetailListener listener) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                favoriteWallpaperDao.delete(favorite);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onSuccessDeleteBookmark();
                        updateFavoriteList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onErrorDeleteBookmark();
                    }
                });
    }

    @Override
    public void insertBookmark(final FavoriteWallpaper favorite, final DetailListener listener) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                favoriteWallpaperDao.insert(favorite);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        listener.onSuccessInsertBookmark();
                        updateFavoriteList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        listener.onErrorInsertBookmark();
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void requestFavoriteList(final FavoriteListener listener) {
        favoriteWallpaperDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FavoriteWallpaper>>() {
                    @Override
                    public void accept(List<FavoriteWallpaper> employees) throws Exception {
                        wallpaperList = employees;
                        listener.onSuccess();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void updateFavoriteList() {
        favoriteWallpaperDao.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<FavoriteWallpaper>>() {
                    @Override
                    public void accept(List<FavoriteWallpaper> employees) throws Exception {
                        wallpaperList = employees;
                        Log.i("MyTAG", "Success updateFavoriteList()");
                    }
                });
    }

    @Override
    public List<PhotosItem> getPhotosItems() {
        return photosItems;
    }

    @Override
    public void setPhotoItemList(List<PhotosItem> list) {
        photosItems.clear();
        photosItems = list;
    }

    @Override
    public List<FavoriteWallpaper> getFavoriteWallpapersList() {
        return wallpaperList;
    }

    @Override
    public List<ThemeWallpaper> getMainListWallpapers() {
        return themeWallpapers;
    }

    private void initThemeWallpapersList() {
        Context context = App.getInstance().getContext();
        Resources resources = context.getResources();

        ThemeWallpaper wallpaper0 = new ThemeWallpaper();
        wallpaper0.setTitle("abstraction");
        wallpaper0.setLocalTitle(resources.getString(R.string.abstraction));
        wallpaper0.setImage(R.drawable.abstraction);
        themeWallpapers.add(wallpaper0);
        ThemeWallpaper wallpaper1 = new ThemeWallpaper();
        wallpaper1.setTitle("adventure");
        wallpaper1.setLocalTitle(resources.getString(R.string.adventure));
        wallpaper1.setImage(R.drawable.adventure);
        themeWallpapers.add(wallpaper1);
        ThemeWallpaper wallpaper2 = new ThemeWallpaper();
        wallpaper2.setTitle("africa");
        wallpaper2.setLocalTitle(resources.getString(R.string.africa));
        wallpaper2.setImage(R.drawable.africa);
        themeWallpapers.add(wallpaper2);
        ThemeWallpaper wallpaper3 = new ThemeWallpaper();
        wallpaper3.setTitle("animals");
        wallpaper3.setLocalTitle(resources.getString(R.string.animals));
        wallpaper3.setImage(R.drawable.animals);
        themeWallpapers.add(wallpaper3);
        ThemeWallpaper wallpaper4 = new ThemeWallpaper();
        wallpaper4.setTitle("apple");
        wallpaper4.setLocalTitle(resources.getString(R.string.apple));
        wallpaper4.setImage(R.drawable.apple);
        themeWallpapers.add(wallpaper4);
        ThemeWallpaper wallpaper5 = new ThemeWallpaper();
        wallpaper5.setTitle("architecture");
        wallpaper5.setLocalTitle(resources.getString(R.string.architecture));
        wallpaper5.setImage(R.drawable.architecture);
        themeWallpapers.add(wallpaper5);
        ThemeWallpaper wallpaper6 = new ThemeWallpaper();
        wallpaper6.setTitle("art");
        wallpaper6.setLocalTitle(resources.getString(R.string.art));
        wallpaper6.setImage(R.drawable.art);
        themeWallpapers.add(wallpaper6);
        ThemeWallpaper wallpaper7 = new ThemeWallpaper();
        wallpaper7.setTitle("beach");
        wallpaper7.setLocalTitle(resources.getString(R.string.beach));
        wallpaper7.setImage(R.drawable.beach);
        themeWallpapers.add(wallpaper7);
        ThemeWallpaper wallpaper8 = new ThemeWallpaper();
        wallpaper8.setTitle("beauty");
        wallpaper8.setLocalTitle(resources.getString(R.string.beauty));
        wallpaper8.setImage(R.drawable.beauty);
        themeWallpapers.add(wallpaper8);
        ThemeWallpaper wallpaper9 = new ThemeWallpaper();
        wallpaper9.setTitle("black and white");
        wallpaper9.setLocalTitle(resources.getString(R.string.black_and_white));
        wallpaper9.setImage(R.drawable.black_and_white);
        themeWallpapers.add(wallpaper9);
        ThemeWallpaper wallpaper10 = new ThemeWallpaper();
        wallpaper10.setTitle("blur");
        wallpaper10.setLocalTitle(resources.getString(R.string.blur));
        wallpaper10.setImage(R.drawable.blur);
        themeWallpapers.add(wallpaper10);
        ThemeWallpaper wallpaper11 = new ThemeWallpaper();
        wallpaper11.setTitle("blue");
        wallpaper11.setLocalTitle(resources.getString(R.string.blue));
        wallpaper11.setImage(R.drawable.blue);
        themeWallpapers.add(wallpaper11);
        ThemeWallpaper wallpaper12 = new ThemeWallpaper();
        wallpaper12.setTitle("books");
        wallpaper12.setLocalTitle(resources.getString(R.string.books));
        wallpaper12.setImage(R.drawable.books);
        themeWallpapers.add(wallpaper12);
        ThemeWallpaper wallpaper13 = new ThemeWallpaper();
        wallpaper13.setTitle("building");
        wallpaper13.setLocalTitle(resources.getString(R.string.building));
        wallpaper13.setImage(R.drawable.building);
        themeWallpapers.add(wallpaper13);
        ThemeWallpaper wallpaper14 = new ThemeWallpaper();
        wallpaper14.setTitle("business");
        wallpaper14.setLocalTitle(resources.getString(R.string.business));
        wallpaper14.setImage(R.drawable.business);
        themeWallpapers.add(wallpaper14);
        ThemeWallpaper wallpaper15 = new ThemeWallpaper();
        wallpaper15.setTitle("businessman");
        wallpaper15.setLocalTitle(resources.getString(R.string.businessman));
        wallpaper15.setImage(R.drawable.businessman);
        themeWallpapers.add(wallpaper15);
        ThemeWallpaper wallpaper16 = new ThemeWallpaper();
        wallpaper16.setTitle("camera");
        wallpaper16.setLocalTitle(resources.getString(R.string.camera));
        wallpaper16.setImage(R.drawable.camera);
        themeWallpapers.add(wallpaper16);
        ThemeWallpaper wallpaper17 = new ThemeWallpaper();
        wallpaper17.setTitle("car");
        wallpaper17.setLocalTitle(resources.getString(R.string.car));
        wallpaper17.setImage(R.drawable.car);
        themeWallpapers.add(wallpaper17);
        ThemeWallpaper wallpaper18 = new ThemeWallpaper();
        wallpaper18.setTitle("city");
        wallpaper18.setLocalTitle(resources.getString(R.string.city));
        wallpaper18.setImage(R.drawable.city);
        themeWallpapers.add(wallpaper18);
        ThemeWallpaper wallpaper19 = new ThemeWallpaper();
        wallpaper19.setTitle("clothes");
        wallpaper19.setLocalTitle(resources.getString(R.string.clothes));
        wallpaper19.setImage(R.drawable.clothes);
        themeWallpapers.add(wallpaper19);
        ThemeWallpaper wallpaper20 = new ThemeWallpaper();
        wallpaper20.setTitle("clouds");
        wallpaper20.setLocalTitle(resources.getString(R.string.clouds));
        wallpaper20.setImage(R.drawable.clouds);
        themeWallpapers.add(wallpaper20);
        ThemeWallpaper wallpaper21 = new ThemeWallpaper();
        wallpaper21.setTitle("coffee");
        wallpaper21.setLocalTitle(resources.getString(R.string.coffee));
        wallpaper21.setImage(R.drawable.coffee);
        themeWallpapers.add(wallpaper21);
        ThemeWallpaper wallpaper22 = new ThemeWallpaper();
        wallpaper22.setTitle("communication");
        wallpaper22.setLocalTitle(resources.getString(R.string.communication));
        wallpaper22.setImage(R.drawable.communication);
        themeWallpapers.add(wallpaper22);
        ThemeWallpaper wallpaper23 = new ThemeWallpaper();
        wallpaper23.setTitle("computer");
        wallpaper23.setLocalTitle(resources.getString(R.string.computer));
        wallpaper23.setImage(R.drawable.computer);
        themeWallpapers.add(wallpaper23);
        ThemeWallpaper wallpaper24 = new ThemeWallpaper();
        wallpaper24.setTitle("construction");
        wallpaper24.setLocalTitle(resources.getString(R.string.construction));
        wallpaper24.setImage(R.drawable.construction);
        themeWallpapers.add(wallpaper24);
        ThemeWallpaper wallpaper25 = new ThemeWallpaper();
        wallpaper25.setTitle("cooking");
        wallpaper25.setLocalTitle(resources.getString(R.string.cooking));
        wallpaper25.setImage(R.drawable.cooking);
        themeWallpapers.add(wallpaper25);
        ThemeWallpaper wallpaper26 = new ThemeWallpaper();
        wallpaper26.setTitle("couple");
        wallpaper26.setLocalTitle(resources.getString(R.string.couple));
        wallpaper26.setImage(R.drawable.couple);
        themeWallpapers.add(wallpaper26);
        ThemeWallpaper wallpaper27 = new ThemeWallpaper();
        wallpaper27.setTitle("creative");
        wallpaper27.setLocalTitle(resources.getString(R.string.creative));
        wallpaper27.setImage(R.drawable.creative);
        themeWallpapers.add(wallpaper27);
        ThemeWallpaper wallpaper28 = new ThemeWallpaper();
        wallpaper28.setTitle("crowd");
        wallpaper28.setLocalTitle(resources.getString(R.string.crowd));
        wallpaper28.setImage(R.drawable.crowd);
        themeWallpapers.add(wallpaper28);
        ThemeWallpaper wallpaper29 = new ThemeWallpaper();
        wallpaper29.setTitle("dance");
        wallpaper29.setLocalTitle(resources.getString(R.string.dance));
        wallpaper29.setImage(R.drawable.dance);
        themeWallpapers.add(wallpaper29);
        ThemeWallpaper wallpaper30 = new ThemeWallpaper();
        wallpaper30.setTitle("data");
        wallpaper30.setLocalTitle(resources.getString(R.string.data));
        wallpaper30.setImage(R.drawable.data);
        themeWallpapers.add(wallpaper30);
        ThemeWallpaper wallpaper31 = new ThemeWallpaper();
        wallpaper31.setTitle("desert");
        wallpaper31.setLocalTitle(resources.getString(R.string.desert));
        wallpaper31.setImage(R.drawable.desert);
        themeWallpapers.add(wallpaper31);
        ThemeWallpaper wallpaper32 = new ThemeWallpaper();
        wallpaper32.setTitle("design");
        wallpaper32.setLocalTitle(resources.getString(R.string.design));
        wallpaper32.setImage(R.drawable.design);
        themeWallpapers.add(wallpaper32);
        ThemeWallpaper wallpaper33 = new ThemeWallpaper();
        wallpaper33.setTitle("desk");
        wallpaper33.setLocalTitle(resources.getString(R.string.desk));
        wallpaper33.setImage(R.drawable.desk);
        themeWallpapers.add(wallpaper33);
        ThemeWallpaper wallpaper34 = new ThemeWallpaper();
        wallpaper34.setTitle("desktop wallpaper");
        wallpaper34.setLocalTitle(resources.getString(R.string.desktop_wallpaper));
        wallpaper34.setImage(R.drawable.desktop_wallpaper);
        themeWallpapers.add(wallpaper34);
        ThemeWallpaper wallpaper35 = new ThemeWallpaper();
        wallpaper35.setTitle("earth");
        wallpaper35.setLocalTitle(resources.getString(R.string.earth));
        wallpaper35.setImage(R.drawable.earth);
        themeWallpapers.add(wallpaper35);
        ThemeWallpaper wallpaper36 = new ThemeWallpaper();
        wallpaper36.setTitle("face");
        wallpaper36.setLocalTitle(resources.getString(R.string.face));
        wallpaper36.setImage(R.drawable.face);
        themeWallpapers.add(wallpaper36);
        ThemeWallpaper wallpaper37 = new ThemeWallpaper();
        wallpaper37.setTitle("family");
        wallpaper37.setLocalTitle(resources.getString(R.string.family));
        wallpaper37.setImage(R.drawable.family);
        themeWallpapers.add(wallpaper37);
        ThemeWallpaper wallpaper38 = new ThemeWallpaper();
        wallpaper38.setTitle("fashion");
        wallpaper38.setLocalTitle(resources.getString(R.string.fashion));
        wallpaper38.setImage(R.drawable.fashion);
        themeWallpapers.add(wallpaper38);
        ThemeWallpaper wallpaper39 = new ThemeWallpaper();
        wallpaper39.setTitle("finance");
        wallpaper39.setLocalTitle(resources.getString(R.string.finance));
        wallpaper39.setImage(R.drawable.finance);
        themeWallpapers.add(wallpaper39);
        ThemeWallpaper wallpaper40 = new ThemeWallpaper();
        wallpaper40.setTitle("fire");
        wallpaper40.setLocalTitle(resources.getString(R.string.fire));
        wallpaper40.setImage(R.drawable.fire);
        themeWallpapers.add(wallpaper40);
        ThemeWallpaper wallpaper41 = new ThemeWallpaper();
        wallpaper41.setTitle("fitness");
        wallpaper41.setLocalTitle(resources.getString(R.string.fitness));
        wallpaper41.setImage(R.drawable.fitness);
        themeWallpapers.add(wallpaper41);
        ThemeWallpaper wallpaper42 = new ThemeWallpaper();
        wallpaper42.setTitle("flowers");
        wallpaper42.setLocalTitle(resources.getString(R.string.flowers));
        wallpaper42.setImage(R.drawable.flowers);
        themeWallpapers.add(wallpaper42);
        ThemeWallpaper wallpaper43 = new ThemeWallpaper();
        wallpaper43.setTitle("food");
        wallpaper43.setLocalTitle(resources.getString(R.string.food));
        wallpaper43.setImage(R.drawable.food);
        themeWallpapers.add(wallpaper43);
        ThemeWallpaper wallpaper44 = new ThemeWallpaper();
        wallpaper44.setTitle("forest");
        wallpaper44.setLocalTitle(resources.getString(R.string.forest));
        wallpaper44.setImage(R.drawable.forest);
        themeWallpapers.add(wallpaper44);
        ThemeWallpaper wallpaper45 = new ThemeWallpaper();
        wallpaper45.setTitle("friends");
        wallpaper45.setLocalTitle(resources.getString(R.string.friends));
        wallpaper45.setImage(R.drawable.friends);
        themeWallpapers.add(wallpaper45);
        ThemeWallpaper wallpaper46 = new ThemeWallpaper();
        wallpaper46.setTitle("fun");
        wallpaper46.setLocalTitle(resources.getString(R.string.fun));
        wallpaper46.setImage(R.drawable.fun);
        themeWallpapers.add(wallpaper46);
        ThemeWallpaper wallpaper47 = new ThemeWallpaper();
        wallpaper47.setTitle("garden");
        wallpaper47.setLocalTitle(resources.getString(R.string.garden));
        wallpaper47.setImage(R.drawable.garden);
        themeWallpapers.add(wallpaper47);
        ThemeWallpaper wallpaper48 = new ThemeWallpaper();
        wallpaper48.setTitle("gift");
        wallpaper48.setLocalTitle(resources.getString(R.string.gift));
        wallpaper48.setImage(R.drawable.gift);
        themeWallpapers.add(wallpaper48);
        ThemeWallpaper wallpaper49 = new ThemeWallpaper();
        wallpaper49.setTitle("girl");
        wallpaper49.setLocalTitle(resources.getString(R.string.girl));
        wallpaper49.setImage(R.drawable.girl);
        themeWallpapers.add(wallpaper49);
        ThemeWallpaper wallpaper50 = new ThemeWallpaper();
        wallpaper50.setTitle("grass");
        wallpaper50.setLocalTitle(resources.getString(R.string.grass));
        wallpaper50.setImage(R.drawable.grass);
        themeWallpapers.add(wallpaper50);
        ThemeWallpaper wallpaper51 = new ThemeWallpaper();
        wallpaper51.setTitle("green");
        wallpaper51.setLocalTitle(resources.getString(R.string.green));
        wallpaper51.setImage(R.drawable.green);
        themeWallpapers.add(wallpaper51);
        ThemeWallpaper wallpaper52 = new ThemeWallpaper();
        wallpaper52.setTitle("group");
        wallpaper52.setLocalTitle(resources.getString(R.string.group));
        wallpaper52.setImage(R.drawable.group);
        themeWallpapers.add(wallpaper52);
        ThemeWallpaper wallpaper53 = new ThemeWallpaper();
        wallpaper53.setTitle("gym");
        wallpaper53.setLocalTitle(resources.getString(R.string.gym));
        wallpaper53.setImage(R.drawable.gym);
        themeWallpapers.add(wallpaper53);
        ThemeWallpaper wallpaper54 = new ThemeWallpaper();
        wallpaper54.setTitle("hd wallpaper");
        wallpaper54.setLocalTitle(resources.getString(R.string.hd_wallpaper));
        wallpaper54.setImage(R.drawable.hd_wallpaper);
        themeWallpapers.add(wallpaper54);
        ThemeWallpaper wallpaper55 = new ThemeWallpaper();
        wallpaper55.setTitle("hair");
        wallpaper55.setLocalTitle(resources.getString(R.string.hair));
        wallpaper55.setImage(R.drawable.hair);
        themeWallpapers.add(wallpaper55);
        ThemeWallpaper wallpaper56 = new ThemeWallpaper();
        wallpaper56.setTitle("happy");
        wallpaper56.setLocalTitle(resources.getString(R.string.happy));
        wallpaper56.setImage(R.drawable.happy);
        themeWallpapers.add(wallpaper56);
        ThemeWallpaper wallpaper57 = new ThemeWallpaper();
        wallpaper57.setTitle("healthy");
        wallpaper57.setLocalTitle(resources.getString(R.string.healthy));
        wallpaper57.setImage(R.drawable.healthy);
        themeWallpapers.add(wallpaper57);
        ThemeWallpaper wallpaper58 = new ThemeWallpaper();
        wallpaper58.setTitle("heart");
        wallpaper58.setLocalTitle(resources.getString(R.string.heart));
        wallpaper58.setImage(R.drawable.heart);
        themeWallpapers.add(wallpaper58);
        ThemeWallpaper wallpaper59 = new ThemeWallpaper();
        wallpaper59.setTitle("holiday");
        wallpaper59.setLocalTitle(resources.getString(R.string.holiday));
        wallpaper59.setImage(R.drawable.holiday);
        themeWallpapers.add(wallpaper59);
        ThemeWallpaper wallpaper60 = new ThemeWallpaper();
        wallpaper60.setTitle("home");
        wallpaper60.setLocalTitle(resources.getString(R.string.home));
        wallpaper60.setImage(R.drawable.home);
        themeWallpapers.add(wallpaper60);
        ThemeWallpaper wallpaper61 = new ThemeWallpaper();
        wallpaper61.setTitle("house");
        wallpaper61.setLocalTitle(resources.getString(R.string.house));
        wallpaper61.setImage(R.drawable.house);
        themeWallpapers.add(wallpaper61);
        ThemeWallpaper wallpaper62 = new ThemeWallpaper();
        wallpaper62.setTitle("ice cream");
        wallpaper62.setLocalTitle(resources.getString(R.string.ice_cream));
        wallpaper62.setImage(R.drawable.ice_cream);
        themeWallpapers.add(wallpaper62);
        ThemeWallpaper wallpaper63 = new ThemeWallpaper();
        wallpaper63.setTitle("idea");
        wallpaper63.setLocalTitle(resources.getString(R.string.idea));
        wallpaper63.setImage(R.drawable.idea);
        themeWallpapers.add(wallpaper63);
        ThemeWallpaper wallpaper64 = new ThemeWallpaper();
        wallpaper64.setTitle("industry");
        wallpaper64.setLocalTitle(resources.getString(R.string.industry));
        wallpaper64.setImage(R.drawable.industry);
        themeWallpapers.add(wallpaper64);
        ThemeWallpaper wallpaper65 = new ThemeWallpaper();
        wallpaper65.setTitle("interior");
        wallpaper65.setLocalTitle(resources.getString(R.string.interior));
        wallpaper65.setImage(R.drawable.interior);
        themeWallpapers.add(wallpaper65);
        ThemeWallpaper wallpaper66 = new ThemeWallpaper();
        wallpaper66.setTitle("internet");
        wallpaper66.setLocalTitle(resources.getString(R.string.internet));
        wallpaper66.setImage(R.drawable.internet);
        themeWallpapers.add(wallpaper66);
        ThemeWallpaper wallpaper67 = new ThemeWallpaper();
        wallpaper67.setTitle("iphone");
        wallpaper67.setLocalTitle(resources.getString(R.string.iphone));
        wallpaper67.setImage(R.drawable.iphone);
        themeWallpapers.add(wallpaper67);
        ThemeWallpaper wallpaper68 = new ThemeWallpaper();
        wallpaper68.setTitle("job");
        wallpaper68.setLocalTitle(resources.getString(R.string.job));
        wallpaper68.setImage(R.drawable.job);
        themeWallpapers.add(wallpaper68);
        ThemeWallpaper wallpaper69 = new ThemeWallpaper();
        wallpaper69.setTitle("kids");
        wallpaper69.setLocalTitle(resources.getString(R.string.kids));
        wallpaper69.setImage(R.drawable.kids);
        themeWallpapers.add(wallpaper69);
        ThemeWallpaper wallpaper70 = new ThemeWallpaper();
        wallpaper70.setTitle("kitchen");
        wallpaper70.setLocalTitle(resources.getString(R.string.kitchen));
        wallpaper70.setImage(R.drawable.kitchen);
        themeWallpapers.add(wallpaper70);
        ThemeWallpaper wallpaper71 = new ThemeWallpaper();
        wallpaper71.setTitle("landscape");
        wallpaper71.setLocalTitle(resources.getString(R.string.landscape));
        wallpaper71.setImage(R.drawable.landscape_image);
        themeWallpapers.add(wallpaper71);
        ThemeWallpaper wallpaper72 = new ThemeWallpaper();
        wallpaper72.setTitle("laptop");
        wallpaper72.setLocalTitle(resources.getString(R.string.laptop));
        wallpaper72.setImage(R.drawable.laptop);
        themeWallpapers.add(wallpaper72);
        ThemeWallpaper wallpaper73 = new ThemeWallpaper();
        wallpaper73.setTitle("light");
        wallpaper73.setLocalTitle(resources.getString(R.string.light));
        wallpaper73.setImage(R.drawable.light);
        themeWallpapers.add(wallpaper73);
        ThemeWallpaper wallpaper74 = new ThemeWallpaper();
        wallpaper74.setTitle("love");
        wallpaper74.setLocalTitle(resources.getString(R.string.love));
        wallpaper74.setImage(R.drawable.love);
        themeWallpapers.add(wallpaper74);
        ThemeWallpaper wallpaper75 = new ThemeWallpaper();
        wallpaper75.setTitle("makeup");
        wallpaper75.setLocalTitle(resources.getString(R.string.makeup));
        wallpaper75.setImage(R.drawable.makeup);
        themeWallpapers.add(wallpaper75);
        ThemeWallpaper wallpaper76 = new ThemeWallpaper();
        wallpaper76.setTitle("man");
        wallpaper76.setLocalTitle(resources.getString(R.string.man));
        wallpaper76.setImage(R.drawable.man);
        themeWallpapers.add(wallpaper76);
        ThemeWallpaper wallpaper77 = new ThemeWallpaper();
        wallpaper77.setTitle("map");
        wallpaper77.setLocalTitle(resources.getString(R.string.map));
        wallpaper77.setImage(R.drawable.map);
        themeWallpapers.add(wallpaper77);
        ThemeWallpaper wallpaper78 = new ThemeWallpaper();
        wallpaper78.setTitle("marketing");
        wallpaper78.setLocalTitle(resources.getString(R.string.marketing));
        wallpaper78.setImage(R.drawable.marketing);
        themeWallpapers.add(wallpaper78);
        ThemeWallpaper wallpaper79 = new ThemeWallpaper();
        wallpaper79.setTitle("medical");
        wallpaper79.setLocalTitle(resources.getString(R.string.medical));
        wallpaper79.setImage(R.drawable.medical);
        themeWallpapers.add(wallpaper79);
        ThemeWallpaper wallpaper80 = new ThemeWallpaper();
        wallpaper80.setTitle("meeting");
        wallpaper80.setLocalTitle(resources.getString(R.string.meeting));
        wallpaper80.setImage(R.drawable.meeting);
        themeWallpapers.add(wallpaper80);
        ThemeWallpaper wallpaper81 = new ThemeWallpaper();
        wallpaper81.setTitle("mobile");
        wallpaper81.setLocalTitle(resources.getString(R.string.mobile));
        wallpaper81.setImage(R.drawable.mobile);
        themeWallpapers.add(wallpaper81);
        ThemeWallpaper wallpaper82 = new ThemeWallpaper();
        wallpaper82.setTitle("mockup");
        wallpaper82.setLocalTitle(resources.getString(R.string.mockup));
        wallpaper82.setImage(R.drawable.mockup);
        themeWallpapers.add(wallpaper82);
        ThemeWallpaper wallpaper83 = new ThemeWallpaper();
        wallpaper83.setTitle("model");
        wallpaper83.setLocalTitle(resources.getString(R.string.model));
        wallpaper83.setImage(R.drawable.model);
        themeWallpapers.add(wallpaper83);
        ThemeWallpaper wallpaper84 = new ThemeWallpaper();
        wallpaper84.setTitle("money");
        wallpaper84.setLocalTitle(resources.getString(R.string.money));
        wallpaper84.setImage(R.drawable.money);
        themeWallpapers.add(wallpaper84);
        ThemeWallpaper wallpaper85 = new ThemeWallpaper();
        wallpaper85.setTitle("mountains");
        wallpaper85.setLocalTitle(resources.getString(R.string.mountains));
        wallpaper85.setImage(R.drawable.mountains);
        themeWallpapers.add(wallpaper85);
        ThemeWallpaper wallpaper86 = new ThemeWallpaper();
        wallpaper86.setTitle("music");
        wallpaper86.setLocalTitle(resources.getString(R.string.music));
        wallpaper86.setImage(R.drawable.music);
        themeWallpapers.add(wallpaper86);
        ThemeWallpaper wallpaper87 = new ThemeWallpaper();
        wallpaper87.setTitle("nature");
        wallpaper87.setLocalTitle(resources.getString(R.string.nature));
        wallpaper87.setImage(R.drawable.nature);
        themeWallpapers.add(wallpaper87);
        ThemeWallpaper wallpaper88 = new ThemeWallpaper();
        wallpaper88.setTitle("nature wallpaper");
        wallpaper88.setLocalTitle(resources.getString(R.string.nature_wallpaper));
        wallpaper88.setImage(R.drawable.nature_wallpaper);
        themeWallpapers.add(wallpaper88);
        ThemeWallpaper wallpaper89 = new ThemeWallpaper();
        wallpaper89.setTitle("new york");
        wallpaper89.setLocalTitle(resources.getString(R.string.new_york));
        wallpaper89.setImage(R.drawable.new_york);
        themeWallpapers.add(wallpaper89);
        ThemeWallpaper wallpaper90 = new ThemeWallpaper();
        wallpaper90.setTitle("new york city wallpaper");
        wallpaper90.setLocalTitle(resources.getString(R.string.new_york_city_wallpaper));
        wallpaper90.setImage(R.drawable.new_york_city_wallpaper);
        themeWallpapers.add(wallpaper90);
        ThemeWallpaper wallpaper91 = new ThemeWallpaper();
        wallpaper91.setTitle("night");
        wallpaper91.setLocalTitle(resources.getString(R.string.night));
        wallpaper91.setImage(R.drawable.night);
        themeWallpapers.add(wallpaper91);
        ThemeWallpaper wallpaper92 = new ThemeWallpaper();
        wallpaper92.setTitle("notebook");
        wallpaper92.setLocalTitle(resources.getString(R.string.notebook));
        wallpaper92.setImage(R.drawable.notebook);
        themeWallpapers.add(wallpaper92);
        ThemeWallpaper wallpaper93 = new ThemeWallpaper();
        wallpaper93.setTitle("office");
        wallpaper93.setLocalTitle(resources.getString(R.string.office));
        wallpaper93.setImage(R.drawable.office);
        themeWallpapers.add(wallpaper93);
        ThemeWallpaper wallpaper94 = new ThemeWallpaper();
        wallpaper94.setTitle("old");
        wallpaper94.setLocalTitle(resources.getString(R.string.old));
        wallpaper94.setImage(R.drawable.old);
        themeWallpapers.add(wallpaper94);
        ThemeWallpaper wallpaper95 = new ThemeWallpaper();
        wallpaper95.setTitle("orange");
        wallpaper95.setLocalTitle(resources.getString(R.string.orange));
        wallpaper95.setImage(R.drawable.orange);
        themeWallpapers.add(wallpaper95);
        ThemeWallpaper wallpaper96 = new ThemeWallpaper();
        wallpaper96.setTitle("paint");
        wallpaper96.setLocalTitle(resources.getString(R.string.paint));
        wallpaper96.setImage(R.drawable.paint);
        themeWallpapers.add(wallpaper96);
        ThemeWallpaper wallpaper97 = new ThemeWallpaper();
        wallpaper97.setTitle("paper");
        wallpaper97.setLocalTitle(resources.getString(R.string.paper));
        wallpaper97.setImage(R.drawable.paper);
        themeWallpapers.add(wallpaper97);
        ThemeWallpaper wallpaper98 = new ThemeWallpaper();
        wallpaper98.setTitle("party");
        wallpaper98.setLocalTitle(resources.getString(R.string.party));
        wallpaper98.setImage(R.drawable.party);
        themeWallpapers.add(wallpaper98);
        ThemeWallpaper wallpaper99 = new ThemeWallpaper();
        wallpaper99.setTitle("people");
        wallpaper99.setLocalTitle(resources.getString(R.string.people));
        wallpaper99.setImage(R.drawable.people);
        themeWallpapers.add(wallpaper99);
        ThemeWallpaper wallpaper100 = new ThemeWallpaper();
        wallpaper100.setTitle("person");
        wallpaper100.setLocalTitle(resources.getString(R.string.person));
        wallpaper100.setImage(R.drawable.person);
        themeWallpapers.add(wallpaper100);
        ThemeWallpaper wallpaper101 = new ThemeWallpaper();
        wallpaper101.setTitle("phone");
        wallpaper101.setLocalTitle(resources.getString(R.string.phone));
        wallpaper101.setImage(R.drawable.phone);
        themeWallpapers.add(wallpaper101);
        ThemeWallpaper wallpaper102 = new ThemeWallpaper();
        wallpaper102.setTitle("photography");
        wallpaper102.setLocalTitle(resources.getString(R.string.photography));
        wallpaper102.setImage(R.drawable.photography);
        themeWallpapers.add(wallpaper102);
        ThemeWallpaper wallpaper103 = new ThemeWallpaper();
        wallpaper103.setTitle("plane");
        wallpaper103.setLocalTitle(resources.getString(R.string.plane));
        wallpaper103.setImage(R.drawable.plane);
        themeWallpapers.add(wallpaper103);
        ThemeWallpaper wallpaper104 = new ThemeWallpaper();
        wallpaper104.setTitle("portrait");
        wallpaper104.setLocalTitle(resources.getString(R.string.portrait));
        wallpaper104.setImage(R.drawable.portrait_image);
        themeWallpapers.add(wallpaper104);
        ThemeWallpaper wallpaper105 = new ThemeWallpaper();
        wallpaper105.setTitle("purple");
        wallpaper105.setLocalTitle(resources.getString(R.string.purple));
        wallpaper105.setImage(R.drawable.purple);
        themeWallpapers.add(wallpaper105);
        ThemeWallpaper wallpaper106 = new ThemeWallpaper();
        wallpaper106.setTitle("rain");
        wallpaper106.setLocalTitle(resources.getString(R.string.rain));
        wallpaper106.setImage(R.drawable.rain);
        themeWallpapers.add(wallpaper106);
        ThemeWallpaper wallpaper107 = new ThemeWallpaper();
        wallpaper107.setTitle("reading");
        wallpaper107.setLocalTitle(resources.getString(R.string.reading));
        wallpaper107.setImage(R.drawable.reading);
        themeWallpapers.add(wallpaper107);
        ThemeWallpaper wallpaper108 = new ThemeWallpaper();
        wallpaper108.setTitle("red");
        wallpaper108.setLocalTitle(resources.getString(R.string.red));
        wallpaper108.setImage(R.drawable.red);
        themeWallpapers.add(wallpaper108);
        ThemeWallpaper wallpaper109 = new ThemeWallpaper();
        wallpaper109.setTitle("relax");
        wallpaper109.setLocalTitle(resources.getString(R.string.relax));
        wallpaper109.setImage(R.drawable.relax);
        themeWallpapers.add(wallpaper109);
        ThemeWallpaper wallpaper110 = new ThemeWallpaper();
        wallpaper110.setTitle("river");
        wallpaper110.setLocalTitle(resources.getString(R.string.river));
        wallpaper110.setImage(R.drawable.river);
        themeWallpapers.add(wallpaper110);
        ThemeWallpaper wallpaper111 = new ThemeWallpaper();
        wallpaper111.setTitle("road");
        wallpaper111.setLocalTitle(resources.getString(R.string.road));
        wallpaper111.setImage(R.drawable.road);
        themeWallpapers.add(wallpaper111);
        ThemeWallpaper wallpaper112 = new ThemeWallpaper();
        wallpaper112.setTitle("romantic");
        wallpaper112.setLocalTitle(resources.getString(R.string.romantic));
        wallpaper112.setImage(R.drawable.romantic);
        themeWallpapers.add(wallpaper112);
        ThemeWallpaper wallpaper113 = new ThemeWallpaper();
        wallpaper113.setTitle("room");
        wallpaper113.setLocalTitle(resources.getString(R.string.room));
        wallpaper113.setImage(R.drawable.room);
        themeWallpapers.add(wallpaper113);
        ThemeWallpaper wallpaper114 = new ThemeWallpaper();
        wallpaper114.setTitle("running");
        wallpaper114.setLocalTitle(resources.getString(R.string.running));
        wallpaper114.setImage(R.drawable.running);
        themeWallpapers.add(wallpaper114);
        ThemeWallpaper wallpaper115 = new ThemeWallpaper();
        wallpaper115.setTitle("sad");
        wallpaper115.setLocalTitle(resources.getString(R.string.sad));
        wallpaper115.setImage(R.drawable.sad);
        themeWallpapers.add(wallpaper115);
        ThemeWallpaper wallpaper116 = new ThemeWallpaper();
        wallpaper116.setTitle("school");
        wallpaper116.setLocalTitle(resources.getString(R.string.school));
        wallpaper116.setImage(R.drawable.school);
        themeWallpapers.add(wallpaper116);
        ThemeWallpaper wallpaper117 = new ThemeWallpaper();
        wallpaper117.setTitle("sea");
        wallpaper117.setLocalTitle(resources.getString(R.string.sea));
        wallpaper117.setImage(R.drawable.sea);
        themeWallpapers.add(wallpaper117);
        ThemeWallpaper wallpaper118 = new ThemeWallpaper();
        wallpaper118.setTitle("security");
        wallpaper118.setLocalTitle(resources.getString(R.string.security));
        wallpaper118.setImage(R.drawable.security);
        themeWallpapers.add(wallpaper118);
        ThemeWallpaper wallpaper119 = new ThemeWallpaper();
        wallpaper119.setTitle("sky");
        wallpaper119.setLocalTitle(resources.getString(R.string.sky));
        wallpaper119.setImage(R.drawable.sky);
        themeWallpapers.add(wallpaper119);
        ThemeWallpaper wallpaper120 = new ThemeWallpaper();
        wallpaper120.setTitle("smartphone");
        wallpaper120.setLocalTitle(resources.getString(R.string.smartphone));
        wallpaper120.setImage(R.drawable.smartphone);
        themeWallpapers.add(wallpaper120);
        ThemeWallpaper wallpaper121 = new ThemeWallpaper();
        wallpaper121.setTitle("smile");
        wallpaper121.setLocalTitle(resources.getString(R.string.smile));
        wallpaper121.setImage(R.drawable.smile);
        themeWallpapers.add(wallpaper121);
        ThemeWallpaper wallpaper122 = new ThemeWallpaper();
        wallpaper122.setTitle("social media");
        wallpaper122.setLocalTitle(resources.getString(R.string.social_media));
        wallpaper122.setImage(R.drawable.social_media);
        themeWallpapers.add(wallpaper122);
        ThemeWallpaper wallpaper123 = new ThemeWallpaper();
        wallpaper123.setTitle("space");
        wallpaper123.setLocalTitle(resources.getString(R.string.space));
        wallpaper123.setImage(R.drawable.space);
        themeWallpapers.add(wallpaper123);
        ThemeWallpaper wallpaper124 = new ThemeWallpaper();
        wallpaper124.setTitle("sport");
        wallpaper124.setLocalTitle(resources.getString(R.string.sport));
        wallpaper124.setImage(R.drawable.sport);
        themeWallpapers.add(wallpaper124);
        ThemeWallpaper wallpaper125 = new ThemeWallpaper();
        wallpaper125.setTitle("stars");
        wallpaper125.setLocalTitle(resources.getString(R.string.stars));
        wallpaper125.setImage(R.drawable.stars);
        themeWallpapers.add(wallpaper125);
        ThemeWallpaper wallpaper126 = new ThemeWallpaper();
        wallpaper126.setTitle("street");
        wallpaper126.setLocalTitle(resources.getString(R.string.street));
        wallpaper126.setImage(R.drawable.street);
        themeWallpapers.add(wallpaper126);
        ThemeWallpaper wallpaper127 = new ThemeWallpaper();
        wallpaper127.setTitle("student");
        wallpaper127.setLocalTitle(resources.getString(R.string.student));
        wallpaper127.setImage(R.drawable.student);
        themeWallpapers.add(wallpaper127);
        ThemeWallpaper wallpaper128 = new ThemeWallpaper();
        wallpaper128.setTitle("study");
        wallpaper128.setLocalTitle(resources.getString(R.string.study));
        wallpaper128.setImage(R.drawable.study);
        themeWallpapers.add(wallpaper128);
        ThemeWallpaper wallpaper129 = new ThemeWallpaper();
        wallpaper129.setTitle("success");
        wallpaper129.setLocalTitle(resources.getString(R.string.success));
        wallpaper129.setImage(R.drawable.success);
        themeWallpapers.add(wallpaper129);
        ThemeWallpaper wallpaper130 = new ThemeWallpaper();
        wallpaper130.setTitle("summer");
        wallpaper130.setLocalTitle(resources.getString(R.string.summer));
        wallpaper130.setImage(R.drawable.summer);
        themeWallpapers.add(wallpaper130);
        ThemeWallpaper wallpaper131 = new ThemeWallpaper();
        wallpaper131.setTitle("sun");
        wallpaper131.setLocalTitle(resources.getString(R.string.sun));
        wallpaper131.setImage(R.drawable.sun);
        themeWallpapers.add(wallpaper131);
        ThemeWallpaper wallpaper132 = new ThemeWallpaper();
        wallpaper132.setTitle("sunset");
        wallpaper132.setLocalTitle(resources.getString(R.string.sunset));
        wallpaper132.setImage(R.drawable.sunset);
        themeWallpapers.add(wallpaper132);
        ThemeWallpaper wallpaper133 = new ThemeWallpaper();
        wallpaper133.setTitle("team");
        wallpaper133.setLocalTitle(resources.getString(R.string.team));
        wallpaper133.setImage(R.drawable.team);
        themeWallpapers.add(wallpaper133);
        ThemeWallpaper wallpaper134 = new ThemeWallpaper();
        wallpaper134.setTitle("technology");
        wallpaper134.setLocalTitle(resources.getString(R.string.technology));
        wallpaper134.setImage(R.drawable.technology);
        themeWallpapers.add(wallpaper134);
        ThemeWallpaper wallpaper135 = new ThemeWallpaper();
        wallpaper135.setTitle("texture");
        wallpaper135.setLocalTitle(resources.getString(R.string.texture));
        wallpaper135.setImage(R.drawable.texture);
        themeWallpapers.add(wallpaper135);
        ThemeWallpaper wallpaper136 = new ThemeWallpaper();
        wallpaper136.setTitle("time");
        wallpaper136.setLocalTitle(resources.getString(R.string.time));
        wallpaper136.setImage(R.drawable.time);
        themeWallpapers.add(wallpaper136);
        ThemeWallpaper wallpaper137 = new ThemeWallpaper();
        wallpaper137.setTitle("tools");
        wallpaper137.setLocalTitle(resources.getString(R.string.tools));
        wallpaper137.setImage(R.drawable.tools);
        themeWallpapers.add(wallpaper137);
        ThemeWallpaper wallpaper138 = new ThemeWallpaper();
        wallpaper138.setTitle("training");
        wallpaper138.setLocalTitle(resources.getString(R.string.training));
        wallpaper138.setImage(R.drawable.training);
        themeWallpapers.add(wallpaper138);
        ThemeWallpaper wallpaper139 = new ThemeWallpaper();
        wallpaper139.setTitle("travel");
        wallpaper139.setLocalTitle(resources.getString(R.string.travel));
        wallpaper139.setImage(R.drawable.travel);
        themeWallpapers.add(wallpaper139);
        ThemeWallpaper wallpaper140 = new ThemeWallpaper();
        wallpaper140.setTitle("trees");
        wallpaper140.setLocalTitle(resources.getString(R.string.trees));
        wallpaper140.setImage(R.drawable.trees);
        themeWallpapers.add(wallpaper140);
        ThemeWallpaper wallpaper141 = new ThemeWallpaper();
        wallpaper141.setTitle("universe");
        wallpaper141.setLocalTitle(resources.getString(R.string.universe));
        wallpaper141.setImage(R.drawable.universe);
        themeWallpapers.add(wallpaper141);
        ThemeWallpaper wallpaper142 = new ThemeWallpaper();
        wallpaper142.setTitle("vacation");
        wallpaper142.setLocalTitle(resources.getString(R.string.vacation));
        wallpaper142.setImage(R.drawable.vacation);
        themeWallpapers.add(wallpaper142);
        ThemeWallpaper wallpaper143 = new ThemeWallpaper();
        wallpaper143.setTitle("vintage");
        wallpaper143.setLocalTitle(resources.getString(R.string.vintage));
        wallpaper143.setImage(R.drawable.vintage);
        themeWallpapers.add(wallpaper143);
        ThemeWallpaper wallpaper144 = new ThemeWallpaper();
        wallpaper144.setTitle("wall");
        wallpaper144.setLocalTitle(resources.getString(R.string.wall));
        wallpaper144.setImage(R.drawable.wall);
        themeWallpapers.add(wallpaper144);
        ThemeWallpaper wallpaper145 = new ThemeWallpaper();
        wallpaper145.setTitle("water");
        wallpaper145.setLocalTitle(resources.getString(R.string.water));
        wallpaper145.setImage(R.drawable.water);
        themeWallpapers.add(wallpaper145);
        ThemeWallpaper wallpaper146 = new ThemeWallpaper();
        wallpaper146.setTitle("website");
        wallpaper146.setLocalTitle(resources.getString(R.string.website));
        wallpaper146.setImage(R.drawable.website);
        themeWallpapers.add(wallpaper146);
        ThemeWallpaper wallpaper147 = new ThemeWallpaper();
        wallpaper147.setTitle("wedding");
        wallpaper147.setLocalTitle(resources.getString(R.string.wedding));
        wallpaper147.setImage(R.drawable.wedding);
        themeWallpapers.add(wallpaper147);
        ThemeWallpaper wallpaper148 = new ThemeWallpaper();
        wallpaper148.setTitle("winter");
        wallpaper148.setLocalTitle(resources.getString(R.string.winter));
        wallpaper148.setImage(R.drawable.winter);
        themeWallpapers.add(wallpaper148);
        ThemeWallpaper wallpaper149 = new ThemeWallpaper();
        wallpaper149.setTitle("woman");
        wallpaper149.setLocalTitle(resources.getString(R.string.woman));
        wallpaper149.setImage(R.drawable.woman);
        themeWallpapers.add(wallpaper149);
        ThemeWallpaper wallpaper150 = new ThemeWallpaper();
        wallpaper150.setTitle("wood");
        wallpaper150.setLocalTitle(resources.getString(R.string.wood));
        wallpaper150.setImage(R.drawable.wood);
        themeWallpapers.add(wallpaper150);
        ThemeWallpaper wallpaper151 = new ThemeWallpaper();
        wallpaper151.setTitle("work");
        wallpaper151.setLocalTitle(resources.getString(R.string.work));
        wallpaper151.setImage(R.drawable.work);
        themeWallpapers.add(wallpaper151);
        ThemeWallpaper wallpaper152 = new ThemeWallpaper();
        wallpaper152.setTitle("working");
        wallpaper152.setLocalTitle(resources.getString(R.string.working));
        wallpaper152.setImage(R.drawable.working);
        themeWallpapers.add(wallpaper152);
        ThemeWallpaper wallpaper153 = new ThemeWallpaper();
        wallpaper153.setTitle("writing");
        wallpaper153.setLocalTitle(resources.getString(R.string.writing));
        wallpaper153.setImage(R.drawable.writing);
        themeWallpapers.add(wallpaper153);
        ThemeWallpaper wallpaper154 = new ThemeWallpaper();
        wallpaper154.setTitle("yellow");
        wallpaper154.setLocalTitle(resources.getString(R.string.yellow));
        wallpaper154.setImage(R.drawable.yellow);
        themeWallpapers.add(wallpaper154);
        ThemeWallpaper wallpaper155 = new ThemeWallpaper();
        wallpaper155.setTitle("yoga");
        wallpaper155.setLocalTitle(resources.getString(R.string.yoga));
        wallpaper155.setImage(R.drawable.yoga);
        themeWallpapers.add(wallpaper155);
        ThemeWallpaper wallpaper156 = new ThemeWallpaper();
        wallpaper156.setTitle("young");
        wallpaper156.setLocalTitle(resources.getString(R.string.young));
        wallpaper156.setImage(R.drawable.young);
        themeWallpapers.add(wallpaper156);
    }
}
