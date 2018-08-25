package ru.pyrovsergey.gallery.model.db;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import ru.pyrovsergey.gallery.R;
import ru.pyrovsergey.gallery.app.App;
import ru.pyrovsergey.gallery.model.PexelsApi;
import ru.pyrovsergey.gallery.model.SearchPhotosCallback;
import ru.pyrovsergey.gallery.model.ThemeWallpaper;
import ru.pyrovsergey.gallery.model.dto.PhotosItem;
import ru.pyrovsergey.gallery.model.dto.Response;

public class DataStorage implements ContractDataStorage {

    private List<ThemeWallpaper> themeWallpapers;
    private final PexelsApi pexelsApi;
    private List<PhotosItem> photosItems;

    public DataStorage() {
        themeWallpapers = new ArrayList<>();
        initThemeWallpapersList();
        photosItems = new ArrayList<>();
        pexelsApi = App.getApi();
    }

    private void initThemeWallpapersList() {
        Context context = App.getInstance().getContext();
        Resources resources = context.getResources();
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.abstraction), R.drawable.abstraction));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.adventure), R.drawable.adventure));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.africa), R.drawable.africa));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.animals), R.drawable.animals));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.apple), R.drawable.apple));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.architecture), R.drawable.architecture));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.art), R.drawable.art));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.beach), R.drawable.beach));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.beauty), R.drawable.beauty));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.black_and_white), R.drawable.black_and_white));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.blur), R.drawable.blur));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.books), R.drawable.books));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.building), R.drawable.building));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.business), R.drawable.business));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.businessman), R.drawable.businessman));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.camera), R.drawable.camera));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.car), R.drawable.car));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.city), R.drawable.city));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.clothes), R.drawable.clothes));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.clouds), R.drawable.clouds));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.coffee), R.drawable.coffee));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.communication), R.drawable.communication));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.computer), R.drawable.computer));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.construction), R.drawable.construction));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.cooking), R.drawable.cooking));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.couple), R.drawable.couple));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.creative), R.drawable.creative));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.crowd), R.drawable.crowd));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.dance), R.drawable.dance));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.data), R.drawable.data));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.desert), R.drawable.desert));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.design), R.drawable.design));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.desk), R.drawable.desk));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.desktop_wallpaper), R.drawable.desktop_wallpaper));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.earth), R.drawable.earth));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.face), R.drawable.face));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.family), R.drawable.family));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.fashion), R.drawable.fashion));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.finance), R.drawable.finance));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.fire), R.drawable.fire));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.fitness), R.drawable.fitness));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.flowers), R.drawable.flowers));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.food), R.drawable.food));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.forest), R.drawable.forest));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.friends), R.drawable.friends));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.fun), R.drawable.fun));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.garden), R.drawable.garden));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.gift), R.drawable.gift));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.girl), R.drawable.girl));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.grass), R.drawable.grass));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.green), R.drawable.green));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.group), R.drawable.group));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.gym), R.drawable.gym));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.hd_wallpaper), R.drawable.hd_wallpaper));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.hair), R.drawable.hair));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.happy), R.drawable.happy));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.healthy), R.drawable.healthy));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.heart), R.drawable.heart));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.holiday), R.drawable.holiday));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.home), R.drawable.home));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.house), R.drawable.house));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.ice_cream), R.drawable.ice_cream));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.idea), R.drawable.idea));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.industry), R.drawable.industry));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.interior), R.drawable.interior));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.internet), R.drawable.internet));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.iphone), R.drawable.iphone));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.job), R.drawable.job));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.kids), R.drawable.kids));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.kitchen), R.drawable.kitchen));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.landscape), R.drawable.landscape));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.laptop), R.drawable.laptop));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.light), R.drawable.light));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.love), R.drawable.love));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.makeup), R.drawable.makeup));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.man), R.drawable.man));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.map), R.drawable.map));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.marketing), R.drawable.marketing));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.medical), R.drawable.medical));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.meeting), R.drawable.meeting));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.mobile), R.drawable.mobile));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.mockup), R.drawable.mockup));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.model), R.drawable.model));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.money), R.drawable.money));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.mountains), R.drawable.mountains));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.music), R.drawable.music));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.nature), R.drawable.nature));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.nature_wallpaper), R.drawable.nature_wallpaper));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.new_york), R.drawable.new_york));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.new_york_city_wallpaper), R.drawable.new_york_city_wallpaper));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.night), R.drawable.night));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.notebook), R.drawable.notebook));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.office), R.drawable.office));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.old), R.drawable.old));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.paint), R.drawable.paint));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.paper), R.drawable.paper));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.party), R.drawable.party));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.people), R.drawable.people));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.person), R.drawable.person));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.phone), R.drawable.phone));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.photography), R.drawable.photography));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.plane), R.drawable.plane));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.portrait), R.drawable.portrait));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.rain), R.drawable.rain));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.reading), R.drawable.reading));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.relax), R.drawable.relax));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.river), R.drawable.river));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.road), R.drawable.road));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.romantic), R.drawable.romantic));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.room), R.drawable.room));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.running), R.drawable.running));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sad), R.drawable.sad));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.school), R.drawable.school));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sea), R.drawable.sea));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.security), R.drawable.security));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sky), R.drawable.sky));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.smartphone), R.drawable.smartphone));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.smile), R.drawable.smile));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.social_media), R.drawable.social_media));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.space), R.drawable.space));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sport), R.drawable.sport));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.stars), R.drawable.stars));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.street), R.drawable.street));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.student), R.drawable.student));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.study), R.drawable.study));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.success), R.drawable.success));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.summer), R.drawable.summer));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sun), R.drawable.sun));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.sunset), R.drawable.sunset));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.team), R.drawable.team));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.technology), R.drawable.technology));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.texture), R.drawable.texture));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.time), R.drawable.time));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.tools), R.drawable.tools));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.training), R.drawable.training));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.travel), R.drawable.travel));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.trees), R.drawable.trees));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.universe), R.drawable.universe));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.vacation), R.drawable.vacation));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.vintage), R.drawable.vintage));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.wall), R.drawable.wall));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.water), R.drawable.water));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.website), R.drawable.website));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.wedding), R.drawable.wedding));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.winter), R.drawable.winter));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.woman), R.drawable.woman));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.wood), R.drawable.wood));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.work), R.drawable.work));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.working), R.drawable.working));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.writing), R.drawable.writing));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.yoga), R.drawable.yoga));
        themeWallpapers.add(new ThemeWallpaper(resources.getString(R.string.young), R.drawable.young));
    }

    @Override
    public List<ThemeWallpaper> getMainListWallpapers() {
        return themeWallpapers;
    }

    @Override
    public void searchWallpapersOnRequest(String query, final SearchPhotosCallback searchPhotosCallback) {
        pexelsApi.searchPhoto(query, 40, 1)
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                        if (response.body() != null) {
                            photosItems.clear();
                            photosItems = response.body().getPhotos();
                        }
                        searchPhotosCallback.onSuccessLoad();
                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        searchPhotosCallback.onErrorLoad(t);
                    }
                });
    }

    @Override
    public List<PhotosItem> getPhotosItems() {
        return photosItems;
    }
}
