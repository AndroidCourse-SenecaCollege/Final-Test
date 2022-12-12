package com.example.marsphotos_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {
    NetworkingService networkingService;
    JsonService jsonService;
    NumberPicker itemPicker;
    DatePicker datePicker;
    ImageView img;
    TextView detailsText;
    String date = "2022-12-09";
    String rover = "Curiosity";
    ArrayList<NasaPhoto> allPhotos = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingService = new NetworkingService();
        networkingService.listener = this;
        jsonService = new JsonService();
        detailsText = findViewById(R.id.details);
        itemPicker = findViewById(R.id.items);
        datePicker = findViewById(R.id.earthdatePicker);
        img = findViewById(R.id.image);
        itemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                networkingService.getImageData(allPhotos.get(i1).img_url);
            }
        });

        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                int month =  datePicker.getMonth() + 1; // 0 and 11
                date = datePicker.getYear() +"-"+ month +"-" + datePicker.getDayOfMonth();
                findViewById(R.id.mActivityIndicator).setVisibility(View.VISIBLE);
                getData(rover,date);
            }
        });

        startAnimation();
        findViewById(R.id.mActivityIndicator).setVisibility(View.VISIBLE);

        getData(rover,date);

    }

    public void getData(String rover, String date){
        networkingService.getNasaImages(rover,date);

    }
    @Override
    public void dataListener(String josnString) {
        allPhotos = jsonService.getNasaData(josnString);
        if (allPhotos.size() > 0) {
            networkingService.getImageData(allPhotos.get(0).img_url);
        }
        else {
            img.setImageResource(R.drawable.img);
        }
    }
    public void startAnimation() {
        // Create an animation
        RotateAnimation rotation = new RotateAnimation(
                0f,
                360f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        rotation.setDuration(1200);
        rotation.setInterpolator(new LinearInterpolator());
        rotation.setRepeatMode(Animation.RESTART);
        rotation.setRepeatCount(Animation.INFINITE);

        // and apply it to your imageview
        findViewById(R.id.mActivityIndicator).startAnimation(rotation);
    }
    @Override
    public void imageListener(Bitmap image) {
        findViewById(R.id.mActivityIndicator).setVisibility(View.INVISIBLE);
        detailsText.setText("Curiosity Rover captured " + allPhotos.size() +" photos of Mars on "+ date);
        img.setImageBitmap(image);
        if (allPhotos.size() > 0) {
            String[] displayedValues = new String[allPhotos.size()];
            for (int i = 0; i < allPhotos.size(); i++) {
                displayedValues[i] = allPhotos.get(i).id + "";
            }
            itemPicker.setMinValue(0);
            itemPicker.setMaxValue(displayedValues.length - 1);
            itemPicker.setDisplayedValues(displayedValues);
        }else {
            String[] displayedValues = new String[0];
            itemPicker.setDisplayedValues(displayedValues);
        }
    }
}