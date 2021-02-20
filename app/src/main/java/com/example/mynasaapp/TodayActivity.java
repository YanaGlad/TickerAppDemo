package com.example.mynasaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.mynasaapp.models.Date;
import com.example.mynasaapp.models.Photo;
import com.google.android.material.snackbar.Snackbar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.schedulers.Schedulers;

public class TodayActivity extends AppCompatActivity {
    CompositeDisposable disposable = new CompositeDisposable();
    ImageView imageView;
    private Bitmap photo;
    private Spinner spinner;
    String[] actions = {" ", "Установить как обои"};
    private AssetManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, actions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        imageView = findViewById(R.id.image);
        final NasaLoader nasaLoader = new NasaLoader();
        nasaLoader.create(this);

        am = getAssets();

        final Date[] date = new Date[1];
        disposable.add(nasaLoader.getNasaService().getApi().getDatesWithPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BiConsumer<List<Date>, Throwable>() {
                    @Override
                    public void accept(List<Date> dates, Throwable throwable) throws Exception {
                        if (throwable != null) {
                            Toast.makeText(TodayActivity.this, "error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TodayActivity.this, dates.get(0).getDate(), Toast.LENGTH_SHORT).show();
                        }
                        date[0] = dates.get(0);
                        System.out.println("Date is " + date[0]);
                        disposable.add(nasaLoader.getNasaService().getApi().getPhotosForDate(date[0].getDate())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io())
                                .subscribe(new BiConsumer<List<Photo>, Throwable>() {
                                    @Override
                                    public void accept(List<Photo> photos, Throwable throwable) throws Exception {
                                        System.out.println(photos.get(0).getImageUrl());
                                        if (throwable != null) {
                                            Toast.makeText(TodayActivity.this, "error", Toast.LENGTH_SHORT).show();
                                        } else {
                                            System.out.println("LOOK " + photos.get(0).getImageUrl());
                                            ImageLoader.getInstance().displayImage(photos.get(0).getImageUrl(), imageView);
                                            findViewById(R.id.progress).setVisibility(View.GONE);

//                                            ImageLoader.getInstance().loadImage(getIntent().getStringExtra(photos.get(0).getImageUrl()), new SimpleImageLoadingListener() {
//                                                @Override
//                                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                                                    if (!isFinishing()) {
//                                                        System.out.println("Loaded image: " + loadedImage);
//                                                        System.out.println("Uri is " + imageUri);
//                                                        photo = loadedImage;
//                                                        imageView.setImage(ImageSource.cachedBitmap(loadedImage));
//                                                         findViewById(R.id.progress).setVisibility(View.GONE);
//                                                    }
//                                                }
//                                            });
                                        }
                                    }
                                }));
                    }
                }));


        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) setWallpaper();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

    }

    public Bitmap createNewGraphicsBitmap(String name) { //Загрузка изображения из assets
        InputStream inputStream;
        Bitmap bitmap;
        try {
            inputStream = am.open(name);
            bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null) {
                throw new RuntimeException("Unable to load file " + name);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to load file " + name);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void setWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            if (photo == null) {
                photo = createNewGraphicsBitmap("aaa.png");
            }
            wallpaperManager.setBitmap(photo);
            Snackbar.make(imageView, "Обои установлены", Snackbar.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}