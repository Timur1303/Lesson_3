package kg.geektech.anroid3.lesson_3;

import android.app.Application;

import kg.geektech.anroid3.lesson_3.data.preferences.PreferenceUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        PreferenceUtils.init(this);
    }
}
