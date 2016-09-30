package com.proschoolonline.utilities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.proschoolonline.adapter.LoginAdapter;
import com.proschoolonline.application.SharedInstance;
import com.proschoolonline.model.NewsData;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.UiThread;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EService
public class BackgroundService extends Service {

    @Bean
    LoginAdapter loginAdapter;

    private boolean isRunning;
    private Context context;
   // private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
       // this.backgroundThread = new Thread(myTask);
    }

    @Background
    void processData(){
        Log.v("Inside Service","processData--");
        List<NewsData> weatherData = loginAdapter.getNewsData();
        publishResult(weatherData);
    }

    @UiThread
    void publishResult(List<NewsData> newsDatas){
        if (newsDatas != null){

            Iterator<NewsData> itr = newsDatas.iterator();
            while (itr.hasNext()) {
                NewsData newsData = itr.next();
                if (!newsData.getAcf().toString().contains("visible_in_app=Yes")){
                    itr.remove();
                }else{
                    if (newsData.getAcf().toString().contains("counter=")){
                        Matcher m = Pattern.compile(
                                Pattern.quote("counter=")
                                        + "(.*?)"
                                        + Pattern.quote("}")
                        ).matcher(newsData.getAcf().toString());
                        while(m.find()){
                            String match = m.group(1);
                            newsData.setCounter(match);
                        }
                    }
                }
                Log.v("Inside Service","publishResult--"+newsData.getTitle().getRendered()+"-----");
                Log.v("Inside Service","acf--"+newsData.getAcf().toString()+"-----");
                Log.v("Inside Service","Counter--"+newsData.getCounter());
            }

            if (SharedInstance.getInstance().getNewsDataList() != null && SharedInstance.getInstance().getNewsDataList().size() > 0){
                for (NewsData newsDataOld : SharedInstance.getInstance().getNewsDataList()){
                    for (NewsData newsDataNew : newsDatas){
                        if (newsDataNew.getId().intValue() == newsDataOld.getId().intValue()){
                            newsDataNew.setBookmarked(newsDataOld.isBookmarked());
                        }
                    }
                }
            }

        }else{
            newsDatas = new ArrayList<>();
        }

        EventBus.getDefault().post(newsDatas);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            Log.v("Inside Service","onStartCommand--");
            processData();
            //this.backgroundThread.start();
        }
        return START_STICKY;
    }

/*
    private Runnable myTask = new Runnable() {
        public void run() {

            //Looper.prepare();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    // process incoming messages here
                    // Do something here


                }
            });

        }
    };
*/

}
