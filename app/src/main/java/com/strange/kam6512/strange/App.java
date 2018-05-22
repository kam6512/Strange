package com.strange.kam6512.strange;

import android.app.Application;
import android.content.Context;
import com.strange.kam6512.strange.di.component.ApplicationComponent;
import com.strange.kam6512.strange.di.component.DaggerApplicationComponent;
import com.strange.kam6512.strange.di.module.ApplicationModule;

import javax.inject.Inject;

public class App extends Application {

    protected ApplicationComponent appComponent;

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        appComponent.inject(this);
    }

    public ApplicationComponent getAppComponent() {
        return appComponent;
    }

}
