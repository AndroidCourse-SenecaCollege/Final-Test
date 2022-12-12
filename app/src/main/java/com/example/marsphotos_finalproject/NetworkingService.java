package com.example.marsphotos_finalproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkingService {
    private String apikey = "wArFt3VHK2bJ853DS1uac88LIsGSaFyF4lfgIrkB" ;
    private String  rover = "";
    private String earthdate = "";
    private String nasaURL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"+rover+"/photos?earth_date="+earthdate+"&api_key="+apikey;

    interface NetworkingListener{
        void dataListener(String josnString);
        void imageListener(Bitmap image);
    }

    public NetworkingListener listener;

    //Task 1

    public void getNasaImages(String rover, String date){

        String url =  "https://api.nasa.gov/mars-photos/api/v1/rovers/"+rover+"/photos?earth_date="+date+"&api_key="+apikey;
        connect(url);
    }


    public void getImageData(String imgurl){
        //Task 2

    }


    public void connect(String url){
        //Task 3
    }

}
