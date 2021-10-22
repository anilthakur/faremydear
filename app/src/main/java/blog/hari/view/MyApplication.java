package blog.hari.view;

import android.app.Application;
import android.content.res.Configuration;

import blog.hari.commonutils.DataProcessor;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        new DataProcessor(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
